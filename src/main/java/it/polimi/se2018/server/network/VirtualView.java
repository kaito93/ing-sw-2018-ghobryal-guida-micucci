package it.polimi.se2018.server.network;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Game;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.shared.Logger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;

/**
 * Class that talk between network Server and Controller
 *
 * @author Samuele Guida
 */
public class VirtualView implements Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private Controller controller;
    private ArrayList<ConnectionServer> connections = new ArrayList<>();
    private ArrayList<Player> playersActive = new ArrayList<>();
    private ArrayList<PlayerPlay> playersPlay = new ArrayList<>();
    private PlayerPlay currentPlayer;

    private ArrayList<Player> playersSuspend = new ArrayList<>();
    private ArrayList<ConnectionServer> connectionsSuspend = new ArrayList<>();
    private ArrayList<PlayerPlay> playerNotPlay = new ArrayList<>();
    private boolean notTerminate = true;
    private static final String REMOTEERROR = "Errore di connessione: {0} !";

    /**
     * method that sets the connections and create the instance of all players and create listener of message_socket
     *
     * @param connect arraylist of connections
     * @return an arraylist of player
     */
    List<Player> setClients(List<ConnectionServer> connect) {
        try {
            setView(connect);
            for (ConnectionServer aConnect : connect) {
                connections.add(aConnect.cloneObj());
            }

            for (ConnectionServer connection : connections) { // per ogni connessione creata
                PlayerPlay player = new PlayerPlay(connection);// crea un thread per il giocatore
                playersPlay.add(player); // aggiungi il thread all'elenco
                playersActive.add(new Player(connection.getUsername())); // crea un giocatore e aggiungilo all'elenco dei giocatori attivi
                //se non ci sono più giocatori attivi, coglie l'eccezione
            }
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        } catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, "Non ci sono più giocatori attivi", e);
        }

        return playersActive;

    }

    /**
     * method that send to players the maps
     */
    public void start() {

        for (int i = 0; i < this.connections.size(); i++) { // per ogni giocatore
            try {
                connections.get(i).sendMap(playersActive.get(i));
            } catch (NullPointerException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
            }

        }
        LOGGER.log(Level.INFO, "Tutto è pronto! Si cominciaaaaaaaa");
    }

    /**
     * method that let's start to listen the listener of message_socket
     */

    void startServer() {
        for (PlayerPlay aPlayersPlay : this.playersPlay)
            aPlayersPlay.start(); // avvia i thread ascoltatori dei giocatori
    }

    /**
     * method that send the private information to all players
     */
    public void startGame() {
        // invia le informazioni al singolo giocatore delle SUE caratteristiche
        try {
            for (int i = 0; i < playersActive.size(); i++) { // per ogni giocatore
                connections.get(i).sendPrivateInformation(playersActive.get(i).getCardPrivateObj());
            }
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send the public information to all players
     *
     * @param publicCards the arraylist of public cards
     */
    public void publicInformation(List<PublicObjectiveCard> publicCards) {
        // invia le informazioni a tutti i giocatori delle informazioni GENERALI della partita.
        List<ToolCard> tools = controller.getGame().getToolCards();
        try {
            for (ConnectionServer connection : this.connections) { // per ogni giocatore
                connection.sendPublicInformation(publicCards, tools);
            }
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * Internal class that manage the listener of message_socket throws Socket
     */
    class PlayerPlay extends Thread implements Serializable {

        private ConnectionServer client;
        boolean connected;

        /**
         * class constructor
         *
         * @param player the network for this player
         */
        private PlayerPlay(ConnectionServer player) {
            this.client = player;
            this.connected = true;
        }

        /**
         * method that listen for a new message_socket
         */
        @Override
        public void run() { // metodo sempre in esecuzione che controlla se il giocatore è ancora connesso
            receive();
        }

        /**
         * method that call the receive message while is connected
         */
        private void receive() {
            while (connected) {
                try {
                    connected = client.receiveMessage();
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
                }
            }
            reconn();
            closeThreadConnection();


        }

        /**
         * method that manage the reconnection for this player
         */
        void reconn() {
            Player temp = connectionLost();
            controller.updatePlayers(temp);
            if (currentPlayer == this) { // se toccava al giocatore sospeso
                controller.fakeMove();
            }
            if (notTerminate) {
                while (!connected) {
                    try {
                        String text = "Il giocatore " + client.getUsername() + " si è disconnesso. Il giocatore è stato sospeso.";

                        for (int i = 0; i < connections.size(); i++) { // per ogni giocatore
                            connections.get(i).sendLostConnection(text, i);
                        }

                        // GESTIONE DELLA RICONNESSIONE

                        this.client.tryReconnect();
                        this.client.setConnected(true);
                        connected = true;
                        text = "Il giocatore " + client.getUsername() + " si è riconnesso. Tornerà in gioco dal prossimo round.";
                        for (ConnectionServer connection : connections) { // per ogni giocatore
                            tryToSendGainConnection(connection, text);
                        }
                        client.sendAcceptReconnection(
                                "Ti sei riconnesso. Ricomincerai a giocare al prossimo turno.", playersPlay.size());
                    } catch (RemoteException e) {
                        try {
                            this.client.setDisconnected();
                        } catch (RemoteException e1) {
                            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
                        }
                        LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
                    }
                }

                int ind2 = playerNotPlay.indexOf(this);

                playersPlay.add(this); // aggiungi il thread del giocatore tra quelli attivi
                playerNotPlay.remove(this); // rimuovi il thread del giocatore tra quelli sospesi
                playersActive.add(playersSuspend.get(ind2)); // aggiungi il giocatore all'elenco dei giocatori in gioco
                playersSuspend.remove(ind2); // rimuovi il giocatore riconnesso tra quelli in sospeso
                connections.add(connectionsSuspend.get(ind2)); //aggiungi la connessione del giocatore tra quelle attive
                connectionsSuspend.remove(ind2); // rimuovi la connessione del giocatore da quelle sospese
                receive(); // riavvio l'ascolto della vView
            } else
                this.interrupt();
        }

        /**
         * method that try to send gain connection
         *
         * @param connection player to send the news
         * @param text       the news
         */
        private void tryToSendGainConnection(ConnectionServer connection, String text) {
            try {
                connection.sendGainConnection(text);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
            }
        }

        /**
         * method that update the arraylist with the player that has been reconnected
         *
         * @return the player
         */
        private Player connectionLost() {

            this.connected = false; // il giocatore non è connesso

            int index = connections.indexOf(this.client); // ricerca l'indice del giocatore
            playersSuspend.add(playersActive.get(index)); // aggiungi il giocatore all'elenco di giocatori sospesi
            Player temp = playersActive.get(index);
            playersActive.remove(index); // rimuovi il giocatore sospeso dai giocatori attivi
            connectionsSuspend.add(this.client); // aggiungi la connessione nell array delle connessioni sospese
            connections.remove(index); // rimuovi la connessione dalle connessioni attive
            playerNotPlay.add(this); // aggiungi questo thread all'elenco di thread riferiti a giocatori sospesi
            playersPlay.remove(this); // rimuovi questo thread all'elenco di thread riferiti a giocatori attivi
            return temp;
        }

        /**
         * close this thread
         */
        private void closeThread() {
            this.interrupt();
        }
    }

    /**
     * close every thread
     */
    private void closeThreadConnection() {
        for (PlayerPlay aPlayersPlay : playersPlay) aPlayersPlay.closeThread();
        for (PlayerPlay aPlayerNotPlay : playerNotPlay) aPlayerNotPlay.closeThread();
    }

    /**
     * method that search a player
     *
     * @param user the username of a player
     * @return an integer, index of the user in arraylist Connections
     */
    private int searchUser(String user) {
        boolean trovato = false;
        int i = 0;
        try {
            while (!trovato) {
                if (connections.get(i).getUsername().equalsIgnoreCase(user))
                    trovato = true;
                else
                    i++;
            }
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
        return i;
    }

    /**
     * method that set the controller
     *
     * @param controller an instance of controller
     */
    // DA TESTARE
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * method that send to a player the information that is his turn
     *
     * @param playersInRound arraylist of players in round
     * @param turno          the turn's game
     * @param posDice        if a player has positioned a dice before
     * @param useTool        if a player has used a tool card before
     */
    public void sendMessageTurn(List<Player> playersInRound, int turno, boolean posDice, boolean useTool) {
        try {
            connections.get(searchUser(playersInRound.get(turno).getName())).sendIsYourTurn(
                    posDice, useTool);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send to a player the update of the game
     *
     * @param model an instance of game
     * @param name  an username
     */
    public void sendMessageUpdate(Game model, String name) {
        // INVIA A TUTTI I GIOCATORI LE INFORMAZIONI DI TUTTI I GIOCATORI.
        ArrayList<Map> maps = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        ArrayList<Boolean> tools = new ArrayList<>();
        ArrayList<Integer> fav = new ArrayList<>();
        String message = "E' il turno di " + name;


        for (Player aPlayersActive : playersActive) {
            maps.add(aPlayersActive.getMap());
            users.add(aPlayersActive.getName());
            fav.add(aPlayersActive.getFavSig());
        }
        for (int i = 0; i < model.getToolCards().size(); i++)
            tools.add(model.getToolCards().get(i).isUsed());

        try {
            for (int i = 0; i < playersActive.size(); i++)
                connections.get(i).sendUpdate(maps, users, message, tools, model.getRoundSchemeMap(), model.getStock(), fav,name);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Copper Foil Burnisher"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageCopper(String title, int player) {
        try {
            connections.get(player).manageCopper(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Corkbacked Straightedge"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageCork(String title, int player) {
        try {
            connections.get(player).manageCork(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Eglomise Brush"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageEglomise(String title, int player) {
        try {
            connections.get(player).manageEglomise(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Flux Brush"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageFluxBrush(String title, int player) {
        try {
            connections.get(player).manageFluxBrush(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a first message_socket for use the tool card "Flux Remover"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageFluxRemover(String title, int player) {
        try {
            connections.get(player).manageFluxRemover(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that call a method for use the tool card "Glazing Hammer"
     *
     * @param title title of card
     */
    public void createMessageGlazing(String title) {
        controller.manageGlazing(title);
    }

    /**
     * method that send a message_socket for use the tool card "Grinding Stone"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageGrinding(String title, int player) {
        try {
            connections.get(player).manageGrinding(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Grozing Pliers"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageGrozing(String title, int player) {
        try {
            connections.get(player).manageGrozing(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Lathekin"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageLathekin(String title, int player) {
        try {
            connections.get(player).manageLathekin(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Lens Cutter"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageLens(String title, int player) {
        try {
            connections.get(player).manageLens(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Running Pliers"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageRunning(String title, int player) {
        try {
            connections.get(player).manageRunning(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a message_socket for use the tool card "Tap Wheel"
     *
     * @param title  title of card
     * @param player a player
     */
    public void createMessageTap(String title, int player) {
        try {
            connections.get(player).manageTap(title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send to a player the final score of all players
     *
     * @param players arraylist of all players
     */
    public void sendScorePlayers(List<Player> players) {
        for (int i = 0; i < playersActive.size(); i++) {
            try {
                connections.get(i).sendFinalPlayers(players);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
            }
        }
    }

    /**
     * method that send a generic error to a player
     *
     * @param error  an error string
     * @param player a player
     */
    public void createMessageError(String error, int player) {
        try {
            connections.get(player).manageError(error);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that send a second message_socket for use the tool card "Flux Remover"
     *
     * @param title  title of card
     * @param player a player
     * @param dice   a choose dice
     */
    public void manageFluxRemover2(Dice dice, String title, Player player) {
        try {
            connections.get(playersActive.indexOf(player)).manageFluxRemover2(dice, title);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that set the current Player
     *
     * @param currentPlayer a player
     */
    // DA TESTARE
    public void setCurrentPlayer(Player currentPlayer) {
        for (int i = 0; i < playersActive.size(); i++) {
            if (currentPlayer == playersActive.get(i))
                this.currentPlayer = playersPlay.get(i);
        }
    }

    /**
     * method that send message_socket of victory for left of others players
     */
    public void manageVictoryAbbandon() {
        try {
            notTerminate = false;
            connections.get(0).sendVictoryAbbandon();
            for (ConnectionServer connectionServer : connectionsSuspend) {
                connectionServer.disconnectSuspendedPlayer();
            }
        } catch (RemoteException | ConcurrentModificationException ignored) {
            //non fare nulla
        }

    }

    /**
     * method that returns true if the game is terminated
     *
     * @return a boolean
     */
    public boolean isTermi() {
        return notTerminate;
    }

    /**
     * method that disconnect all connections
     */
    void disconnect() {
        try {
            for (ConnectionServer connection : this.connections) connection.setDisconnected();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that returns an instance of controller
     *
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * method that set the Virtual view in every ConnectionServer
     */
    private void setView(List<ConnectionServer> connect) {
        try {
            for (ConnectionServer connection : connect) connection.setvView(this);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public ConnectionServer searchPlayer(String user) {
        try {
            for (ConnectionServer connection : connections) {
                if (connection.getUsername().equalsIgnoreCase(user))
                    return connection;
            }
        } catch (RemoteException e) {
            // nulla
        }
        return null;
    }

    public boolean isAnySocket(){
        for (ConnectionServer connection : connections) {
            try {
                if (connection.isConnection())
                    return true;
            } catch (RemoteException e) {
                return false;
            }
        }
        return false;
    }


}
