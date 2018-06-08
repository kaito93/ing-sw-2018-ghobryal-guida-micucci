package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.*;
import it.polimi.se2018.network.client.message.MessageTools.*;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 * Class ConnectionClientSocket
 * @author Samuele Guida
 * class that manage the connection between client and server throws socket. Side Client
 */
public class ConnectionClientSocket extends ConnectionClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    Listen listener;


    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMMESSAGE=2;

    /**
     * class constructor
     * @param port integer of the port for the connection
     * @param ip integer of the ip for the connection
     * @param view the view for the interaction with the player
     */
    public ConnectionClientSocket(int port, String ip, View view){

        this.ip=ip;
        this.port=port;
        this.view = view;

    }

    /**
     * method that setup the connection between client and server
     */
    @Override
    public void run() {

        try {

            System.setProperty("java.net.preferIPv4Stack" , "true"); //setta preferenze di protocollo IP V4
            this.socket = new Socket(ip, port);
            LOGGER.log(Level.INFO,"Connessione col server stabilita");


            this.output= new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            this.input= new ObjectInputStream(socket.getInputStream()); // inizializza la variabile input e output

            update(new RequestConnection(username)); //chiamo il metodo per inviare la richiesta
            listener = new Listen(); // creo un oggetto ascoltatore
            listener.run(); // metto il client ad ascoltare i messaggi in arrivo dal server





        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }

    /**
     * internal class that listen for the request from server
     */
    class Listen{

        /**
         * method that listen the request for the whole game
         */
        synchronized public void run() {

            //Message message; // crea una variabile per contenere il messaggio ricevuto
            boolean condition=true;
            while(condition){

                try{
                    Message message = new Message(2,"ciao");
                    message= (Message)input.readObject(); // leggi il messaggio

                    if (message.getType()==CVEVENT){// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV)message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni

                    }

                    if (message.getType()==SYSTEMMESSAGE){ // se il tipo di messaggio è di Sistema
                        MessageSystem mess = (MessageSystem) message.getEvent();
                        mess.accept(ConnectionClientSocket.this);
                    }
                    if (message.getType()==Message.MVEVENT){ // se il tipo di messaggio viene dal model
                        MessageMV mess = (MessageMV) message.getEvent();
                        mess.accept(view);
                    }



                }
                catch (IOException|ClassNotFoundException e) {
                    condition=false;
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }

            }



        }
    }

    /**
     * method that send a VCMessage to server
     * @param message the message that has sent
     */
    synchronized public void update (MessageVC message){
        try {
            this.output.writeUnshared(message);
            this.output.flush();
            this.output.reset();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * method that manage a request of a new username
     */

    public void requestNewUsername(){
        ConnectionClientSocket newClient= new ConnectionClientSocket(this.port,this.ip,this.view);
        newClient.setUsername(view.askNewUsername());
        newClient.run();
    }

    /**
     * method that manage the request for choose a map
     * @param message message received by server
     */
    public void visit(MessageChooseMap message) {
        this.username=message.getUsername(); // setto il giocatore proprietario di questa connessione
        ArrayList<Cell[][]> cells = new ArrayList<>();
        for (int i=0; i<message.getMaps().size();i++)
            cells.add(message.getMaps().get(i).getCells());
        Cell[][] mapPlayer = view.chooseMap(cells,username); // invoco la view per scegliere la mappa
        int i= cells.indexOf(mapPlayer);
        update(new ResponseMap(message.getMaps().get(i),username)); // invio la risposta al server
    }
    /**
     * abstract method that manage the update of public news
     * @param message message received by server
     */
    public void visit(MessagePublicInformation message) {
        view.setPublicInformation(message.getTitlePublicObjective(),message.getDescriptionPublicObjective(),
                message.getTitleTools(),message.getDescriptionTools());
    }
    /**
     * abstract method that manage the update of private news
     * @param message message received by server
     */
    public void visit(MessageStart message) {
        view.setPrivateInformation(message.getTitlePrivateCard(),message.getDescriptionPrivateCard());
    }
    /**
     * abstract method that manage the update of turn's news.
     * @param message message received by server
     */

    public void visit(MessageYourTurn message) {
        view.updateFavor(message.getFavor(),message.isPosDice(),message.isUseTools());
        view.myTurn(message.isPosDice(),message.isUseTools());
    }

    public void sendPosDice(Dice dice, int column, int row){
        MessagePosDice message= new MessagePosDice();
        message.setDiceChoosed(dice);
        message.setColumn(column);
        message.setRow(row);
        update(message);
    }

    @Override
    public void sendUseTool(String titleCardTool) {
        MessageUseTool message = new MessageUseTool();
        message.setTitleCardChoosed(titleCardTool);
        update(message);
    }

    public void visit(MessageError message) {
        view.manageError(message.getErrorMessage());
    }

    public void visit(MessageCopperFoilBurnisher message) {
        String title = message.getTitle();
        ArrayList<Object> obj= view.manageCCEFR();
        message.setDice((Dice)obj.get(0));
        message.setRowMit((int)obj.get(1));
        message.setColumnMit((int)obj.get(2));
    }

    public void visit(MessageCorkBackedStraightedge message) {

    }

    public void visit(MessageEglomiseBrush message) {

    }

    public void visit(MessageFluxBrush message) {

    }

    public void visit(MessageFluxRemover message) {

    }

    public void visit(MessageGrindingStone message) {

    }

    public void visit(MessageGrozingPliers message) {

    }

    public void visit(MessageLathekin message) {

    }

    public void visit(MessageLensCutter message) {

    }

    public void visit(MessageRunningPliers message) {

    }

    public void visit(MessageTapWheel message) {

    }

    @Override
    public void sendPassMove() {
        MessagePassTurn message = new MessagePassTurn();
        update(message);
    }
}
