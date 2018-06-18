package it.polimi.se2018.controller;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;


/**
 * class Controller
 * contains all method for the manage of the game
 *
 * @author Samuele Guida
 */
public class Controller implements Observer<MessageVC> {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private static final boolean A = true;
    private Boolean b;
    private Game game;
    private int move = 0;
    private VirtualView view;
    private ArrayList<Player> players;
    private boolean setDice;
    private boolean useTools;
    private int turno;
    private ArrayList<Player> playersInRound = new ArrayList<>();
    private int mappe = 0;
    private boolean disconnect = false;
    private static final String ERR="Hai già piazzato il massimo numero di dadi per questo turno [1]";


    /**
     * class constructor initialize the object controller
     *
     * @param view    an occurence of virtual vView
     * @param players arraylist of players in game
     */
    public Controller(VirtualView view, ArrayList<Player> players) {
        this.game = new Game();
        this.view = view;
        this.players = players;
        view.addObservers(this);
    }

    /**
     * method that return the players in the game
     *
     * @return an arraylist of player
     */
    public ArrayList<Player> getPlayersInRound() {
        return playersInRound;
    }

    /**
     * method that return the turn of the game in a round
     *
     * @return an integer between 1 and #players*2
     */
    public int getTurno() {
        return turno;
    }

    /**
     * method that accept the message of type Socket
     *
     * @param message the message received
     */
    public void update(MessageVC message) {
        message.accept(this);

    }

    /**
     * method that manage the choose of the scheme of one player
     *
     * @param message message received
     */
    synchronized public void visit(ResponseMap message) {
        int index = searchUser(message.getUsername());
        if (index >= 0) {
            this.players.get(index).setMap(message.getMapChoose());
            this.players.get(index).setFavorSig();
            mappe++;
            if (mappe == players.size())
                notifyAll();
        } else
            LOGGER.log(Level.WARNING, "E' stato passato un giocatore errato");
    }

    /**
     * method that research a player throws his username
     *
     * @param user username of a player
     * @return the number in the arralist of players where is the username searched
     */

    public int searchUser(String user) {
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
     * method that return the object with the proprieties of the game
     *
     * @return the class Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * method that manage the disconnession of a player during the game
     *
     * @param player player disconnected
     */
    public void updatePlayers(Player player) {
        for (int i = 0; i < playersInRound.size(); i++) {
            if (player == playersInRound.get(i)) {
                playersInRound.remove(i);
                i--;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (player == players.get(i)) {
                players.remove(i);
                i--;
            }
        }
        turno--;
        if (players.size() == 1) {
            view.manageVictoryAbbandon();
        }
        disconnect = true;


    }

    /**
     * method that managed the whole game
     */
    public void game() {

        int round;
        waitw();
        setPlayersInRound(playersInRound);

        // CICLO CHE GESTISCE I ROUND
        for (round = 0; round < game.getMaxRound(); round++) {
            resetRunning();
            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1
            game.setStock(game.getDiceBag().extractDice(playersInRound.size() + 1));

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for (turno = 0; turno < playersInRound.size(); turno++) {
                disconnect = false;
                setDice=false;
                useTools=false;
                view.setCurrentPlayer(playersInRound.get(turno));
                // CICLO CHE GESTISCE LE DUE MOSSE DEL GIOCATORE DENTRO IL SINGOLO TURNO
                if (!playersInRound.get(turno).getRunningPliers()){
                    for (move = 0; move < 2; move++) {
                        if (!view.isTerminate()) {
                            // Invia a tutti i giocatori le informazioni generali del turno
                            view.sendMessageUpdate(turno, getGame(), playersInRound.get(turno).getName());

                            // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI PER IL PROPRIO TURNO DI GIOCO
                            view.sendMessageTurn(playersInRound, turno,setDice,useTools);

                            b = false;
                            waitMove();
                            if (!view.isTerminate() && !disconnect) {
                                LOGGER.log(Level.INFO, "Termine mossa " + String.valueOf(move) + " del giocatore "
                                        + playersInRound.get(turno).getName());
                            }
                        } else {// se la partita è terminata aumenta tutti i contatori per uscire dai cicli for.
                            move = 2;
                            turno = playersInRound.size();
                            round = game.getMaxRound();
                        }
                    }
                }
            }

            if (!view.isTerminate()) {
                // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
                game.getRoundSchemeMap()[round].setDices(game.getStock());

                // aggiorna l'arraylist per i turni dentro al round. quello che era primo diventa ultimo
                updatePlayersInRound(playersInRound);
            }

        }

        if (!view.isTerminate()) {
            // FINE PARTITA terminata normalmente

            // CALCOLA PUNTEGGI
            calcScore();
            ArrayList<Player> finalPlayers = vsScore(playersInRound);

            // INVIA AI GIOCATORI I PUNTEGGI FINALI + MAPPE FINALI + OBIETTIVI PRIVATI DI TUTTI I GIOCATORI

            view.sendScorePlayers(finalPlayers);
        } else {
            LOGGER.log(Level.INFO, "Partita terminata per abbandoni");
        }

    }

    /**
     * method that wait the map of a player
     */
    synchronized public void waitw() {

        try {
            this.wait();
        } catch (InterruptedException e1) {
            LOGGER.log(Level.SEVERE, e1.toString(), e1);
        }
    }

    /**
     * method that wait the move of a player
     */
    synchronized public void waitMove() {
        try {
            LOGGER.log(Level.INFO, "Attendo che il giocatore " + this.playersInRound.get(turno).getName() + " effettui la sua mossa");

            while (!b) {
                this.wait();
                b = true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * method that assign a value to setTool and unlock the waiter
     */

    synchronized public void setTools() {
        this.setDice=A;
        notifyAll();
    }


    /**
     * method that assign a value to setPos and unlock the waiter
     * @param dice
     * @param row
     * @param column
     */
    synchronized public void setPos(Dice dice, int row, int column) {
        if (!setDice) {
            if (!this.playersInRound.get(turno).posDice(dice, row, column)) {
                manageError(Map.getErrorBool().getErrorMessage());
            } else {
                game.removeDiceStock(dice);
                setDice=A;
                notifyAll();
            }
        } else {
            manageError(ERR);
        }

    }

    /**
     * method that build the array of players that can play in a round
     *
     * @param players the list with all players
     */
    public void setPlayersInRound(ArrayList<Player> players) {
        // inizializza la prima metà dell'array
        for (int i = 0; i < this.players.size(); i++)
            players.add(this.players.get(i));
        // inizializza la seconda metà dell'array
        for (int i = this.players.size(); i > 0; i--)
            players.add(this.players.get(i - 1));
    }

    /**
     * method that move the first player in the last place to play
     *
     * @param players the list of players that play in a round (x2)
     */

    public void updatePlayersInRound(ArrayList<Player> players) {
        Player exFirst = players.get(0); // salvo il giocatore da spostare
        players.remove(players.size() - 1); // elimino il giocatore che si trova in fondo
        players.remove(0); // elimino il giocatore che si trova in prima posizione
        players.add(this.players.size() - 1, exFirst); // aggiungi il primo turno del giocatore
        players.add(this.players.size(), exFirst); // aggiungi il secondo turno del giocatore
        checkPlayerRound();// controlla che non si siano riconnessi giocatori. Se sì, li aggiunge come ultimi giocatori.
    }

    public void checkPlayerRound() {
        for (int i = 0; i < players.size(); i++) {
            if (!playersInRound.contains(players.get(i))) {
                playersInRound.add(this.playersInRound.size() - 1, players.get(i)); // aggiungi il primo turno del giocatore che non era presente
                playersInRound.add(this.playersInRound.size(), players.get(i)); // aggiungi il secondo turno del giocatore che non era presente
            }
        }
    }

    /**
     * method for the calculus of score for every players
     */

    public void calcScore() {
        // cicla i giocatori
        for (int i = 0; i < this.players.size(); i++) {
            // cicla le carte obiettivo pubbliche
            for (int j = 0; j < game.getPublicObjCard().size(); j++) {
                // calcola il punteggio ottenuto tramite la carta obiettivo pubblica
                players.get(i).setScore(players.get(i).getScore() + game.getPublicObjCard().get(j).search(players.get(i).getMap()));
            }
            // calcola il punteggio ottenuto tramite la carta obiettivo privata
            players.get(i).setScore(players.get(i).getScore() + players.get(i).getCardPrivateObj().search(players.get(i).getMap()));
            // calcola il punteggio ottenuto tramite i segnalini favore rimasti
            players.get(i).setScore(players.get(i).getScore() + players.get(i).getFavSig());
            // calcola il punteggio ottenuto sottraendo gli spazi liberi nella mappa
            players.get(i).setScore(players.get(i).getScore() - players.get(i).getMap().emptyCells());
        }
    }

    /**
     * method that compare the score of the player
     *
     * @param playersInLastRound list of players in order that play the last game
     * @return a list of player ordered by score
     */
    public ArrayList<Player> vsScore(ArrayList<Player> playersInLastRound) {
        boolean set;
        int j;
        ArrayList<Player> playersFinal = new ArrayList<>();
        // metti il primo giocatore nell'array.
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


        return playersFinal;
    }

    /**
     * method that call the method for use the card that players have requested
     *
     * @param titleCard Title of tool card that player wants to use
     */
    public void useTools(String titleCard) {
        game.searchToolCard(titleCard).getStrategy().requestMessage(view, titleCard, players.indexOf(playersInRound.get(turno)));
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
     * method that simulate a fake move from the player that have been disconnected and unlock the waiter
     */
    synchronized public void fakeMove() {
        setSetDice(A);
        useTools=A;
        move++;
        notifyAll();
    }

    /**
     * method that compare turn with the number of players in game
     *
     * @return a number between 1 and 2
     */
    public int firstOrSecond() {
        if ((turno + 1) < (players.size()))
            return 1;
        else
            return 2;
    }

    /**
     * method that send a error's text to a player
     * @param error the message
     */
    public void manageError(String error) {
        view.createMessageError(error, players.indexOf(playersInRound.get(turno)));
    }

    /**
     * method that return the virtual vView instance
     * @return the instance of virtual vView
     */
    public VirtualView getView() {
        return view;
    }


    public void resetRunning(){
        for (int i=0; i<players.size();i++)
            players.get(i).setRunningPliers(false);
    }

    /**
     * method that manage the card Copper
     * @param title title of the card
     * @param dice dice needed to be repositioned
     * @param rowDest row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     * @param rowMit the row's coordinate of the dice to be repositioned
     * @param columnMit the column's coordinate of the dice to be repositioned
     */

    public void manageCopper(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()), dice
                , rowDest, columnDest, null, false, rowMit,
                columnMit, null, null, null, 0)) {
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        } else
            setTools();
    }

    /**
     *
     * @param title title of the card
     * @param dice dice needed to be repositioned
     * @param rowDest row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     */
    public void manageCork(String title, Dice dice, int rowDest, int columnDest) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()),
                    dice, rowDest, columnDest, null, false, 0, 0, null, null,
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
     *
     * @param title of the card
     * @param dice dice needed to be repositioned
     * @param rowDest row's coordinate on the map where the chosen dice to be positioned
     * @param columnDest column's coordinate on the map where the chosen dice to be positioned
     * @param rowMit the row's coordinate of the dice to be repositioned
     * @param columnMit the column's coordinate of the dice to be repositioned
     */
    public void manageEglomise(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()), dice,
                rowDest, columnDest, null, false, rowMit, columnMit, null, null,
                null, 0))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

    /**
     *
     * @param title of the card
     * @param dice a stock dice
     * @param rowDest row's coordinate where to position the dice
     * @param columnDest column's coordinate where to position the dice
     * @param diceBefore a stock dice before the throw
     */
    public void manageFluxBrush(String title, Dice dice, int rowDest, int columnDest, Dice diceBefore) {

        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()), dice,
                    rowDest, columnDest, getGame().getStock(), false, 0, 0, diceBefore, null,
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
     *
     * @param a if the card is used
     * @param title of the card
     * @param dice
     * @param row       row's coordinate where to position the dice
     * @param column    column's coordinate where to position the dice
     */
    public void manageFluxRemover(boolean a, String title, Dice dice, int row, int column) {
        if (!setDice) {
            if (a) {
                if (!getGame().searchToolCard(title).useTool(null, dice, row, column, getGame().getDiceBag().getBox(),
                        false, 0, 0, null, null, null, 0)) {
                    getGame().getStock().add(dice);
                    manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
                } else {
                    setSetDice(A);
                    setTools();
                }

            } else {
                getGame().getDiceBag().getBox().add(dice);
                getGame().removeDiceStock(dice);
                Collections.shuffle(getGame().getDiceBag().getBox());
                dice = getGame().getDiceBag().getBox().remove(0);
                getView().manageFluxRemover2(dice, title, getPlayersInRound().get(getTurno()));
            }
        } else
            manageError(ERR);

    }

    /**
     *
     * @param title of the card
     */
    public void manageGlazing(String title) {
        if (!setDice){
            getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()),
                    null, firstOrSecond(), 0, getGame().getStock(),
                    setDice, 0, 0, null,
                    null, null, 0);
            setTools();
        }
        else manageError("Hai già piazzato un dado in questo turno! Non puoi attivare questa carta utensile");

    }

    /**
     *
     * @param title of the card
     * @param dice    the chosen dice
     * @param row     row's coordinate where to position the dice
     * @param column  column's coordinate where to position the dice
     * @param diceBefore dice to be removed
     */
    public void manageGrinding(String title, Dice dice, int row, int column, Dice diceBefore) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(null, dice, 0, 0, null, false,
                    row, column, null, null, null, 0)) {
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());

            } else {
                getGame().getStock().remove(diceBefore);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     *
     * @param title of the card
     * @param dice the chosen dice
     * @param rowDest row's coordinate on the map where the chosen dice to be positioned
     * @param colDest column's coordinate on the map where the chosen dice to be positioned
     */
    public void manageGrozing(String title, Dice dice, int rowDest, int colDest) {
        if (!setDice) {

            if (!getGame().searchToolCard(title).useTool(null, dice, rowDest, colDest, null, false, 0, 0,
                    null, null, null, 0)) {
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            } else {
                getGame().getStock().remove(dice);
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);


    }

    /**
     *
     * @param title of the card
     * @param row1Dest row's coordinate on the map where the first dice needed to be repositioned
     * @param column1Dest column's coordinate on the map where the first dice needed to be repositioned
     * @param dices an array list with the dices to move
     * @param col1Mit
     * @param col2Mit
     * @param row1Mit
     * @param row2Mit
     * @param row2Dest row's coordinate on the map where the second dice needed to be repositioned
     * @param column2Dest column's coordinate on the map where the second dice needed to be repositioned
     */
    public void manageLathekin(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest, int column1Dest,
                               ArrayList<Dice> dices, int row2Dest, int column2Dest) {
        getGame().searchToolCard(title).getStrategy().setRow3(row1Mit);
        getGame().searchToolCard(title).getStrategy().setRow4(row2Mit);
        getGame().searchToolCard(title).getStrategy().setColumn3(col1Mit);
        getGame().searchToolCard(title).getStrategy().setRow3(col2Mit);
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()),
                null, row1Dest, column1Dest, dices, false, row2Dest, column2Dest, null, null, null,
                0))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

    /**
     *
     * @param title of the card
     * @param diceStock a chosen dice from the stock
     * @param numberRound which round contains the dice on the round scheme
     * @param row row's coordinate on the map where the chosen dice to be positioned
     * @param column column's coordinate on the map where the chosen dice to be positioned
     * @param diceRound a chosen dice from the Round Scheme
     */
    public void manageLens(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound) {
        if (!setDice) {
            if (!getGame().searchToolCard(title).useTool(null, diceStock, numberRound, 0, getGame().getStock(),
                    false, row, column, diceRound, getGame().getRoundSchemeMap(), null, 0))
            {
                getGame().getStock().add(diceRound);
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            }
            else {
                setSetDice(A);
                setTools();
            }
        } else
            manageError(ERR);

    }

    /**
     *
     * @param title of a card
     * @param dice chose dice
     * @param rowDest row's coordinate on the map where the dice should be placed
     * @param columnDest column's coordinate on the map where the dice should be placed
     */
    public void manageRunning(String title, Dice dice, int rowDest, int columnDest) {
            if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()), dice,
                    firstOrSecond(), 0, getGame().getStock(), false, rowDest, columnDest, null,
                    null, getPlayersInRound(), 0))
                manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            else {
                setSetDice(A);
                setTools();
            }
    }

    /**
     *
     * @param title of a card
     * @param diceRoundScheme a chosen dice from the Round Scheme
     * @param row1Dest row's coordinate on the map where the first chosen dice to be positioned
     * @param column1Dest column's coordinate on the map where the first chosen dice to be positioned
     * @param diceToMove an array list of the dices to be moved
     * @param row2Dest  row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2Dest column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param posDiceinSchemeRound which round contains the dice on the round scheme
     * @param row1Mit the row's coordinate of the first dice to be repositioned
     * @param row2Mit the row's coordinate of the second dice to be repositioned
     * @param col1Mit the column's coordinate of the first dice to be repositioned
     * @param col2Mit the column's coordinate of the second dice to be repositioned
     */
    public void manageTap(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, Dice diceRoundScheme, int row1Dest,
                          int column1Dest, ArrayList<Dice> diceToMove, int row2Dest, int column2Dest, int posDiceinSchemeRound) {
        getGame().searchToolCard(title).getStrategy().setRow3(row1Mit);
        getGame().searchToolCard(title).getStrategy().setRow4(row2Mit);
        getGame().searchToolCard(title).getStrategy().setColumn3(col1Mit);
        getGame().searchToolCard(title).getStrategy().setRow3(col2Mit);
        if (!getGame().searchToolCard(title).useTool(getPlayersInRound().get(getTurno()),
                diceRoundScheme, row1Dest, column1Dest, diceToMove, false, row2Dest, column2Dest, null,
                getGame().getRoundSchemeMap(), null, posDiceinSchemeRound))
            manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            setTools();
    }

}
