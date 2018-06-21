package it.polimi.se2018.server.network;

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
import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;
import it.polimi.se2018.shared.message_socket.RequestReconnect;
import it.polimi.se2018.shared.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 * class that manage the network between Server and Client throws Socket, Server side
 * @author Samuele Guida
 */
public class ConnectionServerSocket extends ConnectionServer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private Message mex;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Constructor class
     * @param client socket network
     * @param user username of player
     * @param out output stream
     * @param inp input stream
     */
    public ConnectionServerSocket(Socket client, String user,ObjectOutputStream out, ObjectInputStream inp){
        this.client=client;
        this.setUsername(user);
        input= inp;
        output = out;
    }

    /**
     * method that return the socket
     * @return a socket
     */
    public Socket getSocket(){
        return this.client;
    }

    /**
     * method that return the input object
     * @return the object input stream
     */
    public ObjectInputStream getInput(){
        return this.input;
    }

    /**
     * method that send an object to the Client
     * @param message an object
     */
     public synchronized void send(Object message) {
        try {
            output.writeUnshared(message);
            output.flush();
            output.reset();
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * method that send the maps set before
     * @param maps an arraylist of maps
     * @param player a player
     */
    @Override
    public void sendMapConn(List<Map> maps, Player player) {
        MessageChooseMap message = new MessageChooseMap(); // prepara un messaggio da inviare per scegliere la carta schema
        for (Map map : maps) { // sceglie 2 carte schema
            message.addMap(map); // aggiunge la mappa estratta al messaggio da inviare
        }
        message.setPlayer(player); // invio alla vView il giocatore proprietario
        mex= new Message(Message.CVEVENT, message);
        send(mex); // viene inviato il messaggio al giocatore per scegliere la carta

    }
    /**
     * method that send the private information of a player
     * @param card private objective card
     */
    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        MessageStart message = new MessageStart(); //crea un nuovo messaggio
        mex = new Message(Message.CVEVENT,message);
        message.setCard(card);
        send(mex);
    }
    /**
     * method that send the public information of the game
     * @param cards arraylist of public objective cards
     * @param tools arraylist of tool cards
     */
    @Override
    public void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools) {
        MessagePublicInformation messag = new MessagePublicInformation();
        mex= new Message(Message.CVEVENT,messag);
        messag.setPublicObjective(cards);
        messag.setToolCards(tools);
        send(mex);
    }
    /**
     * method that send a warning to other players that a player has left the game for disconnection
     * @param text a string
     * @param index new index of player in the array
     */
    @Override
    public void sendLostConnection(String text, int index) {
        MessagePlayerDisconnect message= new MessagePlayerDisconnect();
        message.setMessage(text);
        message.setIndex(index);
        mex = new Message(Message.SYSTEMEVENT, message); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
        send(mex);
    }
    /**
     * method that listen request of reconnection of a disconnected player
     */
    @Override
    public void tryReconnect() {


        boolean reconnect=false;
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
     * method that send the final message_socket to a player
     * @param players a player
     */
    @Override
    public void sendFinalPlayers(List<Player> players) {
        MessageFinalScore messag = new MessageFinalScore();
        messag.setPlayersFinal(players);
        mex = new Message(Message.CVEVENT,messag);
        send(mex);
    }
    /**
     * method that send the information of a turn for a player
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     */
    @Override
    public void sendIsYourTurn(boolean dice, boolean tool) {
        MessageYourTurn mes = new MessageYourTurn();
        mes.setPosDice(dice);
        mes.setUseTools(tool);
        mex = new Message(Message.CVEVENT,mes);
        send(mex);
    }
    /**
     * method that send a error to a player at the choose of username
     */
    @Override
    public void sendErrorUser() {
        MessageNewUsername message = new MessageNewUsername(); // crea un nuovo messaggio
        mex= new Message(Message.SYSTEMEVENT,message); // creo un messaggio di sistema
        send(mex); //invia il messaggio. [nota bene: non si salva conness nell'array]
    }
    /**
     * method that send update of the client with a new news
     * @param maps an arraylist of maps of all players in game
     * @param users an arraylist of string of username of all players in game
     * @param messa a generic message_socket
     * @param tools an arraylist of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock a matrix of dices
     * @param favor an arraylist of integer
     */
    @Override
    public void sendUpdate(List<Map> maps, List<String> users, String messa, List<Boolean> tools,
                           RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favor) {
        MessageUpdate message= new MessageUpdate();
        message.setMessage(messa);
        message.setCells(maps);
        message.setStock(stock);
        message.setUseTools(tools);
        message.setUsers(users);
        message.setFavUsers(favor);
        message.setRoundSchemeMap(roundSchemeMap);
        mex = new Message(Message.MVEVENT,message);
        send(mex);
    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI
    /**
     * method that manage the send for the tool card "Tap Wheel
     * @param title the title of this card
     */
    @Override
    public void manageTap(String title) {
        MessageTapWheel message = new MessageTapWheel();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }
    /**
     * method that manage the send for the tool card "Running Pliers"
     * @param title the title of this card
     */
    @Override
    public void manageRunning(String title) {
        MessageRunningPliers message = new MessageRunningPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }
    /**
     * method that manage the send for the tool card "Lens Cutter"
     * @param title the title of this card
     */
    @Override
    public void manageLens(String title) {
        MessageLensCutter message= new MessageLensCutter();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "Lathekin"
     * @param title the title of this card
     */
    @Override
    public void manageLathekin(String title) {
        MessageLathekin message= new MessageLathekin();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "Grozing Pliers"
     * @param title the title of this card
     */
    @Override
    public void manageGrozing(String title) {
        MessageGrozingPliers message = new MessageGrozingPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * abstract method that manage the send for the tool card "Grinding Stone"
     * @param title the title of this card
     */
    @Override
    public void manageGrinding(String title) {
        MessageGrindingStone message = new MessageGrindingStone();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the first send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover(String title) {
        MessageFluxRemover message = new MessageFluxRemover();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "Flux Brush"
     * @param title the title of this card
     */
    @Override
    public void manageFluxBrush(String title) {
        MessageFluxBrush message= new MessageFluxBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "CEglomise Brush"
     * @param title the title of this card
     */
    @Override
    public void manageEglomise(String title) {
        MessageEglomiseBrush message = new MessageEglomiseBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "Cork Backed Straiightedge"
     * @param title the title of this card
     */
    @Override
    public void manageCork(String title) {
        MessageCorkBackedStraightedge message = new MessageCorkBackedStraightedge();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }
    /**
     * method that manage the send for the tool card "Copper Foil Burnisher"
     * @param title the title of this card
     */
    @Override
    public void manageCopper(String title) {
        MessageCopperFoilBurnisher message = new MessageCopperFoilBurnisher();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }
    /**
     * method that send a generic error to the client
     * @param error a string
     */
    @Override
    public void manageError(String error) {
        MessageError message= new MessageError();
        message.setErrorMessage(error);
        mex = new Message(Message.SYSTEMEVENT,message);
        send(mex);
    }
    /**
     * method that manage the second send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover2(Dice dice, String title) {
        MessageFluxRemover message = new MessageFluxRemover();
        message.setTitle(title);
        message.setDice(dice);
        message.setFirstMessage(true);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }
    /**
     * method that send a communication of victory with disconnection of others players
     */
    @Override
    public void sendVictoryAbbandon() {
        MessagePlayerDisconnect message = new MessagePlayerDisconnect();
        message.setMessage("Hai vinto per abbandono degli altri giocatori. Congratulazioni (?)");
        mex = new Message(Message.SYSTEMEVENT,message);
        send(mex);
    }
    /**
     * method that close the network between server and client
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
     * method that send a news: a player has been reconnected
     * @param text a string of text
     */
    @Override
    public void sendGainConnection(String text) {
        MessagePlayerDisconnect message = new MessagePlayerDisconnect();
        message.setMessage(text);
        mex = new Message(Message.SYSTEMEVENT,message);
        send(mex);
    }
    /**
     * method that send a confirm of reconnection
     * @param text a string
     * @param index an integer for manage the new index of this player
     */
    @Override
    public void sendAcceptReconnection(String text, int index) {
        RequestReconnect message = new RequestReconnect();
        message.setMessage(text);
        message.setNewIndex(index);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }


}
