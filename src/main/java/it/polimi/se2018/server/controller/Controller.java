package it.polimi.se2018.server.controller;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Game;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.server.network.VirtualView;
import it.polimi.se2018.shared.Logger;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;


/**
 * class Controller
 * contains all method for the manage of the game
 *
 * @author Samuele Guida
 */
public class Controller implements Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private static final boolean A = true;
    private Boolean b;
    private Game game;
    private int move = 0;
    private VirtualView view;
    private List<Player> players;
    private boolean setDice;
    private boolean useTools;
    private int turn;
    private ArrayList<Player> playersInRound;
    private int mappe = 0;
    private boolean disconnect = false;
    private static final String ERR = "Hai già piazzato il massimo numero di dadi per questo turn [1]";
    private static final String REMOTEERROR = "Errore di connessione: {0} !";


    /**
     * class constructor
     *
     * @param view an instance of virtual vView
     */
    public Controller(VirtualView view) {
        this.game = new Game();
        playersInRound = new ArrayList<>();
        this.view = view;

    }

    /**
     * method that set the players in the controller
     *
     * @param players the players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * method that returns the players in the game, in an entire round order
     *
     * @return an array list of player
     */
    private ArrayList<Player> getPlayersInRound() {
        return playersInRound;
    }

    /**
     * method that returns the turn of the game in a round
     *
     * @return an integer between 1 and #players*2
     */
    private int getTurn() {
        return turn;
    }


    /**
     * method that sets the map for a player
     *
     * @param username the player's username
     * @param map      player's passed map
     */
    public void map(String username, Map map) {
        int index = searchUser(username);
        if (index >= 0) {
            this.players.get(index).setMap(map);
            this.players.get(index).setFavorSig();
            mappe++;
            if (mappe == players.size()) {
                synchronized (this){
                    notifyAll();
                }
            }

        } else
            LOGGER.log(Level.WARNING, "E' stato passato un giocatore errato");
    }

    /**
     * method that searches a player throw his username
     *
     * @param user username of the player
     * @return the number in the array list of players where the username is being searched
     */

    private int searchUser(String user) {
        for (int i = 0; i < this.players.size(); i++) {
            if (players.get(i).getName().equalsIgnoreCase(user))
                return i;
        }
        return -1;
    }

    /**
     * method that setup the game
     */
    public void startGame() {
        game.setPrivateObjectiveCard(players); // chiama il metodo per settare le carte obiettivo privato
        view.startGame();
        view.publicInformation(game.getPublicObjCard());
        game();
    }

    /**
     * method that returns the object with the proprieties of the game
     *
     * @return an instance of the class Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * method that manage the disconnection of a player during the game
     *
     * @param player player disconnected
     */
    public void updatePlayers(Player player) {
        playersInRound.remove(player);
        playersInRound.remove(player);
        turn--;
        if (players.size() == 1){
            view.manageVictoryAbbandon();
            disconnect = true;
        }
    }

    /**
     * method that manages the whole game
     */
    public void game() {

        int round;
        waitw();
        setPlayersInRound();

        // CICLO CHE GESTISCE I ROUND
        for (round = 0; round < game.getMaxRound(); round++) {
            resetRunning();
            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1
            game.setStock(game.getDiceBag().extractDice(playersInRound.size() + 1));

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for (turn = 0; turn < playersInRound.size(); turn++) {
                disconnect = false;
                setDice = false;
                useTools = false;
                try {
                    view.setCurrentPlayer(playersInRound.get(turn));
                    // CICLO CHE GESTISCE LE DUE MOSSE DEL GIOCATORE DENTRO IL SINGOLO TURNO
                    if (!playersInRound.get(turn).getRunningPliers()) {
                        for (move = 0; move < 2; move++) {
                            if (view.isTermi()) {
                                // Invia a tutti i giocatori le informazioni generali del turn
                                view.sendMessageUpdate(getGame(), playersInRound.get(turn).getName());

                                // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI PER IL PROPRIO TURNO DI GIOCO
                                view.sendMessageTurn(playersInRound, turn, setDice, useTools);
                                b = false;
                                try {
                                    if (view.searchPlayer(playersInRound.get(turn).getName()).isConnection())
                                        waitMove();
                                } catch (RemoteException | IndexOutOfBoundsException e) {
                                    // ciao
                                }
                                if (view.isTermi() && !disconnect) {
                                    LOGGER.log(Level.INFO, "Termine mossa {0}", String.valueOf(move) + " del giocatore "
                                            + playersInRound.get(turn).getName());
                                }
                            } else {// se la partita è terminata aumenta tutti i contatori per uscire dai cicli for.
                                move = 2;
                                turn = playersInRound.size();
                                round = game.getMaxRound();
                            }
                        }
                    }
                }catch (IndexOutOfBoundsException e){
                    //non fare nulla
                }
            }

            if (view.isTermi()) {
                // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
                game.getRoundSchemeMap()[round].setDices(game.getStock());

                // aggiorna l'arraylist per i turni dentro al round. quello che era primo diventa ultimo
                updatePlayersInRound(playersInRound);
            }

        }

        if (view.isTermi()) {
            // FINE PARTITA terminata normalmente

            // CALCOLA PUNTEGGI
            calcScore();
            List<Player> finalPlayers = vsScore(playersInRound);

            // INVIA AI GIOCATORI I PUNTEGGI FINALI + MAPPE FINALI + OBIETTIVI PRIVATI DI TUTTI I GIOCATORI

            view.sendScorePlayers(finalPlayers);
        } else {
            LOGGER.log(Level.INFO, "Partita terminata per abbandoni");
        }

    }

    /**
     * method that waits the map of a player
     */
    private void waitw() {
        boolean condition = true;
        try {
            while (condition) {
                if (mappe != players.size())
                    this.wait();
                else
                    condition = false;
            }
        } catch (InterruptedException e1) {
            LOGGER.log(Level.SEVERE, e1.toString(), e1);
            Thread.currentThread().interrupt();
        }
    }


    /**
     * method that waits the move of a player
     */
    private void waitMove() {
        try {
            LOGGER.log(Level.INFO, "Attendo che il giocatore " + this.playersInRound.get(turn).getName() + " effettui la sua mossa");

            while (!b) {
                this.wait();
                b = true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * method that assigns a value to setTool and notify the waiting methods
     */

    private void setTools() {
        this.setDice = A;
        notifyAll();
    }


    /**
     * method that assigns a value to setPos and unlock the waiter
     *
     * @param dice   a chosen dice to be positioned on the map
     * @param row    row's coordinate on the map where the dice will be positioned
     * @param column column's coordinate on the map where the dice will be positioned
     */
    public void setPos(Dice dice, int row, int column) {
        if (!setDice) {
            if (!this.playersInRound.get(turn).posDice(dice, row, column)) {
                manageError(Map.getErrorBool().getErrorMessage());
            } else {
                game.removeDiceStock(dice);
                setDice = A;
                synchronized (this){
                    notifyAll();
                }
            }
        } else {
            manageError(ERR);
        }

    }

    /**
     * method that builds the array of players in order for an entire round
     */
    private void setPlayersInRound() {
        // inizializza la prima metà dell'array
        playersInRound.addAll(players);
        // inizializza la seconda metà dell'array
        for (int i = this.players.size(); i > 0; i--)
            playersInRound.add(players.get(i - 1));
    }

    /**
     * method that moves the first player in the last place to play
     *
     * @param players the list of players that play in a round (x2)
     */

    private void updatePlayersInRound(List<Player> players) {
        try {
            Player exFirst = players.get(0); // salvo il giocatore da spostare
            players.remove(players.size() - 1); // elimino il giocatore che si trova in fondo
            players.remove(0); // elimino il giocatore che si trova in prima posizione
            players.add(this.players.size() - 1, exFirst); // aggiungi il primo turn del giocatore
            players.add(this.players.size(), exFirst); // aggiungi il secondo turn del giocatore
            checkPlayerRound();// controlla che non si siano riconnessi giocatori. Se sì, li aggiunge come ultimi giocatori.
        }catch (IndexOutOfBoundsException e){
            //non fare nulla
        }
    }

    /**
     * method that verifies if there's a player that is not present in the array list playersInRound
     */
    private void checkPlayerRound() {
        for (Player player : players) {
            if (!playersInRound.contains(player)) {
                playersInRound.add(this.playersInRound.size() - 1, player); // aggiungi il primo turn del giocatore che non era presente
                playersInRound.add(this.playersInRound.size(), player); // aggiungi il secondo turn del giocatore che non era presente
            }
        }
    }

    /**
     * method for the calculus of score for every players
     */

    private void calcScore() {
        // cicla i giocatori
        for (Player player : this.players) {
            // cicla le carte obiettivo pubbliche
            for (int j = 0; j < game.getPublicObjCard().size(); j++) {
                // calcola il punteggio ottenuto tramite la carta obiettivo pubblica
                player.setScore(player.getScore() + game.getPublicObjCard().get(j).search(player.getMap()));
            }
            // calcola il punteggio ottenuto tramite la carta obiettivo privata
            player.setScore(player.getScore() + player.getCardPrivateObj().search(player.getMap()));
            // calcola il punteggio ottenuto tramite i segnalini favore rimasti
            player.setScore(player.getScore() + player.getFavSig());
            // calcola il punteggio ottenuto sottraendo gli spazi liberi nella mappa
            player.setScore(player.getScore() - player.getMap().emptyCells());
        }
    }

    /**
     * method that compares the score of the players
     *
     * @param playersInLastRound list of players in order that play the last game
     * @return a list of player ordered by score
     */
    private List<Player> vsScore(ArrayList<Player> playersInLastRound) {
        boolean set;
        int j;
        ArrayList<Player> playersFinal = new ArrayList<>();
        // metti il primo giocatore nell'array.
        try {
            playersFinal.add(players.get(0));

            // cicla i giocatori

            for (int i = 1; i < players.size(); i++) {
                // cicla i giocatori già calcolati
                set = false;
                j = 0;
                while (!set && j < playersFinal.size()) {
                    // confronta i punteggi
                    if (players.get(i).getScore() > playersFinal.get(j).getScore()) {
                        // se il punteggio del giocatore preso in considerazione è più alto, aggiungilo prima di quello confrontato
                        playersFinal.add(j, players.get(i));
                        set = true;
                    } else { // altrimenti
                        if (players.get(i).getScore() == playersFinal.get(j).getScore()) {
                            // se il punteggio del giocatore preso in considerazione è uguale a quello confrontato
                            // confronta i punteggi ottenuti dalle carte obiettivo privato
                            if (players.get(i).getCardPrivateObj().search(players.get(i).getMap()) > playersFinal.get(j).getCardPrivateObj().search(playersFinal.get(j).getMap())) {
                                // se il punteggio ottenuto dall'obiettivo privato del giocatore è più alto aggiungilo prima di quello confrontato
                                playersFinal.add(j, players.get(i));
                                set = true;
                            } else { // altrimenti
                                // se anche i punteggi privati sono uguali
                                if (players.get(i).getCardPrivateObj().search(players.get(i).getMap()) == playersFinal.get(j).getCardPrivateObj().search(playersFinal.get(j).getMap())) {
                                    // confronta il numero di segnalini favore rimasti
                                    if (players.get(i).getFavSig() > playersFinal.get(j).getFavSig()) {
                                        // se al giocatore sono rimasti più segnalini favore rispetto a quello confrontato allora aggiungilo prima di quello confrontato
                                        playersFinal.add(j, players.get(i));
                                        set = true;
                                    } else {
                                        // se anche i segnalini favore rimasti sono uguali
                                        if (players.get(i).getFavSig() == playersFinal.get(j).getFavSig() && (playersInLastRound.indexOf(players.get(i)) < playersInLastRound.indexOf(playersFinal.get(j)))) {
                                            // se il giocatore preso in considerazione ha giocato prima del giocatore preso in considerazione nell'ultimo round
                                            playersFinal.add(j, players.get(i));
                                            set = true;
                                        }
                                    }

                                }


                            }

                        }
                    }
                    // se sei arrivato alla fine dei giocatori da confrontare
                    if (j + 1 == playersFinal.size()) {
                        // piazza il giocatore in fondo all'array.
                        playersFinal.add(players.get(i));
                        set = true;
                    } else
                        // altrimenti aumenta j per proseguire il confronto
                        j++;
                }

            }
        }catch (IndexOutOfBoundsException e){
            //non fare nulla
        }

        return playersFinal;
    }

    /**
     * method that call the method for use the card that players have requested
     *
     * @param titleCard Title of tool card that player wants to use
     */
    public void useTools(String titleCard) {
        game.searchToolCard(titleCard).getStrategy().requestMessage(view, titleCard, players.indexOf(playersInRound.get(turn)));
    }

    /**
     * method that assign a value to setDice
     *
     * @param setDice a boolean value
     */
    public void setSetDice(boolean setDice) {
        this.setDice = setDice;
    }

    /**
     * method that simulates a fake move from the player that have been disconnected and unlock the waiter
     */
    public void fakeMove() {
        setSetDice(A);
        useTools = A;
        move++;
        notifyAll();
    }

    /**
     * method that compare turn with the number of players in game
     *
     * @return a number between 1 and 2
     */
    private int firstOrSecond() {
        if ((turn + 1) < (players.size()))
            return 1;
        else
            return 2;
    }

    /**
     * method that send a error's text to a player
     *
     * @param error the message_socket
     */
    public void manageError(String error) {
        view.createMessageError(error, players.indexOf(playersInRound.get(turn)));
    }

    /**
     * method that return the virtual vView instance
     *
     * @return the instance of virtual vView
     */
    public VirtualView getView() {
        return view;
    }

    /**
     * method that decides that all players didn't use the card Running Pliers yet
     */
    private void resetRunning() {
        for (Player player : players) player.setRunningPliers(false);
    }

    /**
     * method that manages the card Copper
     *
     * @param title      title of the card
     * @param dice       dice needed to be repositioned
     * @param rowDest    row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     * @param rowMit     the row's coordinate of the dice to be repositioned
     * @param columnMit  the column's coordinate of the dice to be repositioned
     */

    public void manageCopper(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice
                , rowDest, columnDest, null, rowMit,
                columnMit, null, null, null, 0)) {
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        } else
            setTools();
    }

    /**
     * method that manages the card Cork-backed
     *
     * @param title      title of the card
     * @param dice       dice needed to be repositioned
     * @param rowDest    row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     */
    public void manageCork(String title, Dice dice, int rowDest, int columnDest) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()),
                    dice, rowDest, columnDest, null, 0, 0, null, null,
                    null, 0))
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            else {
                getGame().removeDiceStock(dice);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     * method that manages the card Eglomise
     *
     * @param title      title of the card
     * @param dice       dice needed to be repositioned
     * @param rowDest    row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     * @param rowMit     the row's coordinate of the dice to be repositioned
     * @param columnMit  the column's coordinate of the dice to be repositioned
     */
    public void manageEglomise(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice,
                rowDest, columnDest, null, rowMit, columnMit, null, null,
                null, 0))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

    /**
     * method that manages the card FluxBrush
     *
     * @param title      title of the card
     * @param dice       a stock dice
     * @param rowDest    row's coordinate where to position the dice
     * @param columnDest column's coordinate where to position the dice
     * @param diceBefore a stock dice before the throw
     */
    public void manageFluxBrush(String title, Dice dice, int rowDest, int columnDest, Dice diceBefore) {

        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice,
                    rowDest, columnDest, getGame().getStock(), 0, 0, diceBefore, null,
                    null, 0))
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            else {
                getGame().removeDiceStock(diceBefore);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     * method that manages the card Flux Remover
     *
     * @param title  of the card
     * @param dice   the chosen dice
     * @param row    row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     */
    public void manageFluxRemover(String title, Dice dice, int row, int column) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice, row, column, getGame().getStock(),
                    0, 0, null, null, null, 0)) {
                getGame().getStock().add(dice);
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            } else {
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     * method that extract a new dice from the dice bag and remove the before dice in the stock
     *
     * @param title string, title of the this tool card
     * @param dice  the dice to be reset
     */
    public void manageFluxRemoverExtractDice(String title, Dice dice) {
        getGame().getDiceBag().getBox().add(dice);
        getGame().removeDiceStock(dice);
        Collections.shuffle(getGame().getDiceBag().getBox());
        Dice dice1 = getGame().getDiceBag().getBox().remove(0);
        dice1.throwDice();
        getView().manageFluxRemover2(dice1, title, getPlayersInRound().get(getTurn()));
    }


    /**
     * method that manages the card Glazing
     *
     * @param title title of the card
     */
    public void manageGlazing(String title) {

        if (getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()),
                null, firstOrSecond(), 0, getGame().getStock(), 0, 0,
                null, null, null, 0))
            setTools();
        else
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());

    }

    /**
     * method that manages the card Grinding
     *
     * @param title      title of the card
     * @param dice       the chosen dice
     * @param row        row's coordinate where to position the dice
     * @param column     column's coordinate where to position the dice
     * @param diceBefore dice to be removed
     */
    public void manageGrinding(String title, Dice dice, int row, int column, Dice diceBefore) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice, 0, 0, getGame().getStock(), row,
                    column, null, null, null, 0)) {
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());

            } else {
                getGame().removeDiceStock(diceBefore);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     * method that manages the card Grozing
     *
     * @param title   title of the card
     * @param dice    the chosen dice
     * @param rowDest row's coordinate on the map where the chosen dice to be positioned
     * @param colDest column's coordinate on the map where the chosen dice to be positioned
     */
    public void manageGrozing(String title, Dice dice, int rowDest, int colDest) {
        if (!setDice) {

            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice, rowDest, colDest, getGame().getStock(), 0, 0,
                    null, null, null, 0)) {
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            } else {
                getGame().removeDiceStock(dice);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);


    }

    /**
     * method that manages the card Lathekin
     *
     * @param title       title of the card
     * @param row1Dest    row's coordinate on the map where the first dice needed to be repositioned
     * @param column1Dest column's coordinate on the map where the first dice needed to be repositioned
     * @param dices       an array list with the dices to move
     * @param col1Mit     column's coordinate on the map where the first dice is first
     * @param col2Mit     column's coordinate on the map where the second dice is first
     * @param row1Mit     row's coordinate on the map where the first dice is first
     * @param row2Mit     row's coordinate on the map where the second dice is first
     * @param row2Dest    row's coordinate on the map where the second dice needed to be repositioned
     * @param column2Dest column's coordinate on the map where the second dice needed to be repositioned
     */
    public void manageLathekin(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest, int column1Dest,
                               List<Dice> dices, int row2Dest, int column2Dest) {
        getGame().searchToolCard(title).getStrategy().setRow3(row1Mit);
        getGame().searchToolCard(title).getStrategy().setRow4(row2Mit);
        getGame().searchToolCard(title).getStrategy().setColumn3(col1Mit);
        getGame().searchToolCard(title).getStrategy().setColumn4(col2Mit);
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()),
                null, row1Dest, column1Dest, dices, row2Dest, column2Dest, null, null, null,
                0))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

    /**
     * method that manages the card Lens Cutter
     *
     * @param title       title of the card
     * @param diceStock   a chosen dice from the stock
     * @param numberRound which round contains the dice on the round scheme
     * @param row         row's coordinate on the map where the chosen dice to be positioned
     * @param column      column's coordinate on the map where the chosen dice to be positioned
     * @param diceRound   a chosen dice from the Round Scheme
     */
    public void manageLens(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), diceStock, numberRound, 0, getGame().getStock(),
                    row, column, diceRound, getGame().getRoundSchemeMap(), null, 0)) {
                getGame().getStock().add(diceRound);
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            } else {
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     * method that manages the card RunningPliers
     *
     * @param title      title of a card
     * @param dice       chose dice
     * @param rowDest    row's coordinate on the map where the dice should be placed
     * @param columnDest column's coordinate on the map where the dice should be placed
     */
    public void manageRunning(String title, Dice dice, int rowDest, int columnDest) {
        if (setDice){
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()), dice,
                    firstOrSecond(), 0, getGame().getStock(), rowDest, columnDest, null,
                    null, getPlayersInRound(), 0))
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            else {
                setSetDice(A);
                setTools();
            }
        }
        else
            manageError("Il giocatore non ha ancora effettuato la proprio prima mossa in questo turno");


    }

    /**
     * method that manages the card Tap Wheels
     *
     * @param title                title of a card
     * @param diceRoundScheme      a chosen dice from the Round Scheme
     * @param row1Dest             row's coordinate on the map where the first chosen dice to be positioned
     * @param column1Dest          column's coordinate on the map where the first chosen dice to be positioned
     * @param diceToMove           an array list of the dices to be moved
     * @param row2Dest             row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2Dest          column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param posDiceinSchemeRound which round contains the dice on the round scheme
     * @param row1Mit              the row's coordinate of the first dice to be repositioned
     * @param row2Mit              the row's coordinate of the second dice to be repositioned
     * @param col1Mit              the column's coordinate of the first dice to be repositioned
     * @param col2Mit              the column's coordinate of the second dice to be repositioned
     */
    public void manageTap(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, Dice diceRoundScheme, int row1Dest,
                          int column1Dest, List<Dice> diceToMove, int row2Dest, int column2Dest, int posDiceinSchemeRound) {
        getGame().searchToolCard(title).getStrategy().setRow3(row1Mit);
        getGame().searchToolCard(title).getStrategy().setRow4(row2Mit);
        getGame().searchToolCard(title).getStrategy().setColumn3(col1Mit);
        getGame().searchToolCard(title).getStrategy().setRow3(col2Mit);
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurn()),
                diceRoundScheme, row1Dest, column1Dest, diceToMove, row2Dest, column2Dest, null,
                getGame().getRoundSchemeMap(), null, posDiceinSchemeRound))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

    /**
     * destroys all controller data
     */
    @SuppressWarnings("Deprecated")
    @Override
    public void finalize() throws Throwable {
        game.finalize();
        players.clear();
        playersInRound.clear();
        System.gc();
        super.finalize();
    }

}
