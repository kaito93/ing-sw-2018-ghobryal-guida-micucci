package it.polimi.se2018.server.network;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.shared.message_socket.client_to_server.MessageDisconnect;
import it.polimi.se2018.shared.message_socket.server_to_client.*;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.shared.message_socket.server_to_client.Message;
import it.polimi.se2018.shared.message_socket.message_tools.*;
import it.polimi.se2018.shared.message_socket.MessageVC;
import it.polimi.se2018.shared.message_socket.RequestReconnect;
import it.polimi.se2018.shared.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * class that manage the network between Server and Client throws Socket, Server side
 *
 * @author Samuele Guida
 */
public class ConnectionServerSocket implements ConnectionServer, Cloneable {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String PLAYER = "Il player";

    private VirtualView vView = null;
    private String username = null;
    private boolean connected = true;

    private Message mex;
    private transient Socket client;
    private transient ObjectOutputStream output;
    private transient ObjectInputStream input;
    private final boolean socket=true;

    /**
     * Constructor class
     *
     * @param client socket network
     * @param user   username of player
     * @param out    output stream
     * @param inp    input stream
     */
    public ConnectionServerSocket(Socket client, String user, ObjectOutputStream out, ObjectInputStream inp) {
        this.client = client;
        this.setUsername(user);
        input = inp;
        output = out;
    }

    /**
     * method that return the socket
     *
     * @return a socket
     */
    public Socket getSocket() {
        return this.client;
    }

    /**
     * method that return the input object
     *
     * @return the object input stream
     */
    public ObjectInputStream getInput() {
        return this.input;
    }

    /**
     * method that send an object to the Client
     *
     * @param message an object
     */
    public synchronized void send(Object message) {
        try {
            output.writeUnshared(message);
            output.flush();
            output.reset();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * sets the possessor of this server connection
     * @param username the possessor of this connection in a string format
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the possessor of this server connection
     * @return the possessor of this connection in a string format
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * method that sends the maps to be chosen from the player
     *
     * @param maps   an array list of maps
     * @param player a player
     */
    @Override
    public void sendMapConn(List<Map> maps, Player player) {
        MessageChooseMap message = new MessageChooseMap(); // prepara un messaggio da inviare per scegliere la carta schema
        for (Map map : maps) { // sceglie 4 carte schema
            message.addMap(map); // aggiunge la mappa estratta al messaggio da inviare
        }
        message.setPlayer(player); // invio alla vView il giocatore proprietario
        mex = new Message(Message.CVEVENT, message);
        send(mex); // viene inviato il messaggio al giocatore per scegliere la carta

    }

    /**
     * method that sends the private card information of a player
     *
     * @param card private objective card
     */
    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        MessageStart message = new MessageStart(); //crea un nuovo messaggio
        mex = new Message(Message.CVEVENT, message);
        message.setCard(card);
        send(mex);
    }

    /**
     * method that sends the public information of the game
     *
     * @param cards array list of public objective cards
     * @param tools array list of tool cards
     */
    @Override
    public void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools) {
        MessagePublicInformation messag = new MessagePublicInformation();
        mex = new Message(Message.CVEVENT, messag);
        messag.setPublicObjective(cards);
        messag.setToolCards(tools);
        send(mex);
    }

    /**
     * method that sends a warning to other players that a player has left the game for disconnection
     *
     * @param text  a string
     * @param index new index of player in the array
     */
    @Override
    public void sendLostConnection(String text, int index) {
        MessagePlayerDisconnect message = new MessagePlayerDisconnect();
        message.setMessage(text);
        message.setIndex(index);
        mex = new Message(Message.SYSTEMEVENT, message); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
        send(mex);
    }

    /**
     * method that listens request of reconnection of a disconnected player
     */
    @Override
    public void tryReconnect() {


        boolean reconnect = false;
        // fin quando il giocatore non si è riconnesso
        while (!reconnect) {
            try {
                MessageVC reconn = (MessageVC) getInput().readObject();
                reconnect = reconn instanceof RequestReconnect;
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    /**
     * method that sends the final message_socket to a players
     *
     * @param players all active players
     */
    @Override
    public void sendFinalPlayers(List<Player> players) {
        MessageFinalScore messag = new MessageFinalScore();
        messag.setPlayersFinal(players);
        mex = new Message(Message.CVEVENT, messag);
        send(mex);
    }

    /**
     * method that sends the information of a turn for a player
     *
     * @param dice boolean True if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else False
     */
    @Override
    public void sendIsYourTurn(boolean dice, boolean tool) {
        MessageYourTurn mes = new MessageYourTurn();
        mes.setPosDice(dice);
        mes.setUseTools(tool);
        mex = new Message(Message.CVEVENT, mes);
        send(mex);
    }

    /**
     * method that sends an error to a player at the username choice
     */
    @Override
    public void sendErrorUser() {
        MessageNewUsername message = new MessageNewUsername(); // crea un nuovo messaggio
        mex = new Message(Message.SYSTEMEVENT, message); // creo un messaggio di sistema
        send(mex); //invia il messaggio. [nota bene: non si salva conness nell'array]
    }

    /**
     * method that sends update of the client with a new news
     *
     * @param maps           an array list of maps of all players in game
     * @param users          an array list of string of username of all players in game
     * @param msg          a generic message_socket
     * @param tools          an array list of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock          a matrix of dices
     * @param favor          an array list of integer
     */
    @Override
    public void sendUpdate(List<Map> maps, List<String> users, String msg, List<Boolean> tools,
                           RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favor, String username) {
        MessageUpdate message = new MessageUpdate();
        message.setMessage(msg);
        message.setCells(maps);
        message.setStock(stock);
        message.setUseTools(tools);
        message.setUsers(users);
        message.setFavUsers(favor);
        message.setUsername(username);
        message.setRoundSchemeMap(roundSchemeMap);
        mex = new Message(Message.MVEVENT, message);
        send(mex);
    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    /**
     * method that manages the transmission for the tool card "Tap Wheel"
     *
     * @param title the title of this card
     */
    @Override
    public void manageTap(String title) {
        MessageTapWheel message = new MessageTapWheel();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);
    }

    /**
     * method that manages the transmission for the tool card "Running Pliers"
     *
     * @param title the title of this card
     */
    @Override
    public void manageRunning(String title) {
        MessageRunningPliers message = new MessageRunningPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);
    }

    /**
     * method that manages the transmission for the tool card "Lens Cutter"
     *
     * @param title the title of this card
     */
    @Override
    public void manageLens(String title) {
        MessageLensCutter message = new MessageLensCutter();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Lathekin"
     *
     * @param title the title of this card
     */
    @Override
    public void manageLathekin(String title) {
        MessageLathekin message = new MessageLathekin();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Grozing Pliers"
     *
     * @param title the title of this card
     */
    @Override
    public void manageGrozing(String title) {
        MessageGrozingPliers message = new MessageGrozingPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Grinding Stone"
     *
     * @param title the title of this card
     */
    @Override
    public void manageGrinding(String title) {
        MessageGrindingStone message = new MessageGrindingStone();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the first transmission for the tool card "Flux Remover"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover(String title) {
        MessageFluxRemover message = new MessageFluxRemover();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the second transmission for the tool card "Flux Remover"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover2(Dice dice, String title) {
        MessageFluxRemover message = new MessageFluxRemover();
        message.setTitle(title);
        message.setDice(dice);
        message.setFirstMessage(true);
        mex = new Message(Message.CVEVENT, message);
        send(mex);
    }

    /**
     * method that manages the transmission for the tool card "Flux Brush"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxBrush(String title) {
        MessageFluxBrush message = new MessageFluxBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Eglomise Brush"
     *
     * @param title the title of this card
     */
    @Override
    public void manageEglomise(String title) {
        MessageEglomiseBrush message = new MessageEglomiseBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Cork-backed Straightedge"
     *
     * @param title the title of this card
     */
    @Override
    public void manageCork(String title) {
        MessageCorkBackedStraightedge message = new MessageCorkBackedStraightedge();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);

    }

    /**
     * method that manages the transmission for the tool card "Copper Foil Burnisher"
     *
     * @param title the title of this card
     */
    @Override
    public void manageCopper(String title) {
        MessageCopperFoilBurnisher message = new MessageCopperFoilBurnisher();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT, message);
        send(mex);
    }

    /**
     * method that sends a generic error to the client
     *
     * @param error a string
     */
    @Override
    public void manageError(String error) {
        MessageError message = new MessageError();
        message.setErrorMessage(error);
        mex = new Message(Message.SYSTEMEVENT, message);
        send(mex);
    }

    /**
     * method that sends a communication of victory with disconnection of others players
     */
    @Override
    public void sendVictoryAbbandon() {
        MessagePlayerDisconnect message = new MessagePlayerDisconnect();
        message.setMessage("Hai vinto per abbandono degli altri giocatori. Congratulazioni (?)");
        message.setIndex(5);
        mex = new Message(Message.SYSTEMEVENT, message);
        send(mex);
    }

    /**
     * method that closes the network between server and client
     */
    @Override
    public void closeConnection() {
        try {
            client.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * method that sends news: a player has been reconnected
     *
     * @param text a string of text
     */
    @Override
    public void sendGainConnection(String text) {
        MessagePlayerDisconnect message = new MessagePlayerDisconnect();
        message.setIndex(-1);
        message.setMessage(text);
        mex = new Message(Message.SYSTEMEVENT, message);
        send(mex);
    }

    /**
     * method that sends a confirm of reconnection
     *
     * @param text  a string
     * @param index an integer for manage the new index of this player
     */
    @Override
    public void sendAcceptReconnection(String text, int index) {
        RequestReconnect message = new RequestReconnect();
        message.setMessage(text);
        message.setNewIndex(index);
        mex = new Message(Message.CVEVENT, message);
        send(mex);
    }

    public void sendMap(Player player) {
        ArrayList<Map> mapsToPlayer = new ArrayList<>();
        for (int j = 0; j < 4; j++) { // sceglie 4 carte schema
            mapsToPlayer.add(vView.getController().getGame().randomMap()); // aggiunge la mappa estratta al messaggio da inviare
        }
        sendMapConn(mapsToPlayer, player);
    }

    /**
     * method to clone object of Connection Server
     *
     * @return the cloned network
     */
    @Override
    public ConnectionServer cloneObj() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return null;
    }

    /**
     * method to clone object of Connection Server
     * @return the cloned network
     * @throws CloneNotSupportedException catches this exception if the object is not cloneable
     */
    @Override
    protected ConnectionServer clone() throws CloneNotSupportedException {
        return (ConnectionServer) super.clone();
    }

    @Override
    public void setClientRMI(ConnectionClient stub, String username) {
        //serve solo per RMI
    }

    /**
     * method that sets the status of network
     */
    public void setDisconnected() {
        this.connected = false;
    }

    /**
     * method that sets an instance of Virtual View
     *
     * @param vView a Virtual View
     */
    public void setvView(VirtualView vView) {
        this.vView = vView;
    }

    /**
     * method that listen for a new message socket
     */
    public boolean receiveMessage() {
        try {
            MessageVC message = (MessageVC) getInput().readUnshared();
            if (message instanceof MessageDisconnect) {
                connected = false;
                LOGGER.log(Level.OFF, PLAYER + " {0} non ha effettuato una mossa in tempo\n" +
                        "l'ho messo in sospensione", getUsername());
            } else{
                update(message);
                return true;
            }

        } catch (IOException e) {
            connected = false;
            //if (client.isConnected())
                //LOGGER.log(Level.OFF, PLAYER + getUsername() + " si è disconnesso. Non ho ricevuto nulla", e);

        } catch (ClassNotFoundException e) {
            connected = false;
            LOGGER.log(Level.OFF, PLAYER + getUsername() + " si è disconnesso. Non manda dati corretti", e);
        }
        return false;
    }

    /**
     * only used by RMI
     */
    @Override
    public void tapSet(String title, Dice roundSchemeDice, List<Dice> dicesToMove, int row1Mit, int row2Mit, int column1Mit, int column2Mit, int row1Dest, int row2Dest, int column1Dest, int column2Dest, int roundSchemeDicePos) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void coppperSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void corkSet(String title, Dice dice, int rowDest, int columnDest) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void eglomiseSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void fluxBrushSet(String title, Dice dice, int rowDest, int cloumnDest, Dice diceBefore) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void fluxRemoverSet(String title, Dice dice, int row, int column) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void grindingSet(String title, Dice dice, int row, int column, Dice diceBefore) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void grozingSet(String title, Dice dice, int rowDest, int colDest) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void lathekinSet(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest, int col1Dest, List<Dice> dicesToMove, int row2Dest, int col2Dest) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void lensSet(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void runningSet(String title, Dice dice, int rowDest, int colDest) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void posDice(Dice dice, int row, int column) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void useTool(String title) {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void passTurn() {
        //solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void setConnected(boolean connected) {
        // solo per RMI
    }

    /**
     * only used by RMI
     */
    @Override
    public void setPlayerOnline() {
        //solo per RMI
    }

    @Override
    public boolean isConnection() throws RemoteException {
        return socket;
    }

    /**
     * method that accepts the message_socket of type Socket
     * @param message the received message_socket
     */
    public void update(MessageVC message) {
        message.accept(vView.getController());
    }


}
