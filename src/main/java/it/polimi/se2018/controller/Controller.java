package it.polimi.se2018.controller;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;

import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.message.MessageFinalScore;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observer;


import java.util.ArrayList;
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
    Boolean b;
    Game game;
    int move = 0;
    VirtualView view;
    ArrayList<Player> players;
    boolean setDice;
    boolean useTools;
    int turno;
    ArrayList<Player> playersInRound = new ArrayList<>();


    /**
     * class constructor initialize the object controller
     *
     * @param view    an occurence of virtual view
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
     * @return Arraylist<Player>
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
    public void visit(ResponseMap message) {
        int index = searchUser(message.getUsername());
        if (index >= 0) {
            this.players.get(index).setMap(message.getMapChoose());
            this.players.get(index).setFavorSig();
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
        // TO DO: SE IL GIOCATORE SI DISCONNETTE BISOGNA MODIFICARE TUTTI I CONTATORI DEI GIOCATORI IN GIOCO
    }

    /**
     * method that managed the whole game
     */
    public void game() {


        setPlayersInRound(playersInRound);

        // CICLO CHE GESTISCE I ROUND
        for (int round = 0; round < game.getMaxRound(); round++) {

            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1
            game.setStock(game.getDiceBag().extractDice(playersInRound.size() + 1));

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for (turno = 0; turno < playersInRound.size(); turno++) {

                playersInRound.get(turno).setSetDice(false);
                playersInRound.get(turno).setUseTools(false);

                // CICLO CHE GESTISCE LE DUE MOSSE DEL GIOCATORE DENTRO IL SINGOLO TURNO
                for (move = 0; move < 2; move++) {
                    // Invia a tutti i giocatori le informazioni generali del turno
                    view.sendMessageUpdate(turno, getGame(), playersInRound.get(turno).getName());

                    // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI PER IL PROPRIO TURNO DI GIOCO
                    view.sendMessageTurn(playersInRound, turno);

                    b = false;
                    waitMove();
                    syncPlayers(playersInRound.get(turno));
                    LOGGER.log(Level.INFO, "Termine mossa " + String.valueOf(move) + " del giocatore "
                            + playersInRound.get(turno).getName());

                }

            }

            // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
            game.getRoundSchemeMap()[round].setDices(game.getStock());

            // aggiorna l'arraylist per i turni dentro al round. quello che era primo diventa ultimo
            updatePlayersInRound(playersInRound);
        }

        // FINE PARTITA

        // CALCOLA PUNTEGGI
        calcScore();
        ArrayList<Player> finalPlayers = vsScore(playersInRound);

        // INVIA AI GIOCATORI I PUNTEGGI FINALI + MAPPE FINALI + OBIETTIVI PRIVATI DI TUTTI I GIOCATORI

        view.sendScorePlayers(finalPlayers);

    }

    public void syncPlayers(Player playerGame){
        for (int i=0; i<players.size();i++)
            if (players.get(i).getName().equalsIgnoreCase(playerGame.getName())){
                players.remove(i);
                players.add(i,playerGame);
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
        this.playersInRound.get(turno).setUseTools(A);
        notifyAll();
    }

    /**
     * method that assign a value to setPos and unlock the waiter
     */

    synchronized public void setPos(Dice dice, int row, int column) {
        String error = "ciao";
        if (!this.playersInRound.get(turno).posDice(dice, row, column)) {
            manageError(error);
        }
        else {
            game.removeDiceStock(dice);
            this.playersInRound.get(turno).setSetDice(A);
            notifyAll();
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
     * method that assign a value to useTools
     *
     * @param useTools a boolean value
     */

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }

    /**
     * method that simulate a fake move from the player that have been disconnected and unlock the waiter
     */
    synchronized public void fakeMove() {
        setSetDice(true);
        setUseTools(true);
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

    public void manageError(String error){
        view.createMessageError(error,players.indexOf(playersInRound.get(turno)));
    }
}
