package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.*;
import it.polimi.se2018.network.client.message.MessageTools.*;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.view.View;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 * Class ConnectionClientSocket
 *
 * @author Samuele Guida
 * class that manage the connection between client and server throws socket. Side Client
 */
public class ConnectionClientSocket extends ConnectionClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    Listen listener;
    boolean condition = true;


    public static final int MVEVENT = 0;
    public static final int CVEVENT = 1;
    public static final int SYSTEMMESSAGE = 2;

    /**
     * class constructor
     *
     * @param port integer of the port for the connection
     * @param ip   integer of the ip for the connection
     * @param view the view for the interaction with the player
     */
    public ConnectionClientSocket(int port, String ip, View view) {

        this.ip = ip;
        this.port = port;
        this.view = view;

    }

    /**
     * method that setup the connection between client and server
     */
    @Override
    public void run() {

        try {

            System.setProperty("java.net.preferIPv4Stack", "true"); //setta preferenze di protocollo IP V4
            this.socket = new Socket(ip, port);
            LOGGER.log(Level.INFO, "Connessione col server stabilita");


            this.output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            this.input = new ObjectInputStream(socket.getInputStream()); // inizializza la variabile input e output

            update(new RequestConnection(username)); //chiamo il metodo per inviare la richiesta
            listener = new Listen(); // creo un oggetto ascoltatore
            listener.run(); // metto il client ad ascoltare i messaggi in arrivo dal server
            view.addLog("La partita è terminata");


        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }

    /**
     * internal class that listen for the request from server
     */
    class Listen {

        /**
         * method that listen the request for the whole game
         */
        synchronized public void run() {

            //Message message; // crea una variabile per contenere il messaggio ricevuto
            while (condition) {

                try {
                    Message message = (Message) input.readObject(); // leggi il messaggio

                    if (message.getType() == CVEVENT) {// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV) message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni

                    }

                    if (message.getType() == SYSTEMMESSAGE) { // se il tipo di messaggio è di Sistema
                        MessageSystem mess = (MessageSystem) message.getEvent();
                        mess.accept(ConnectionClientSocket.this);
                    }
                    if (message.getType() == Message.MVEVENT) { // se il tipo di messaggio viene dal model
                        MessageMV mess = (MessageMV) message.getEvent();
                        mess.accept(view);
                    }


                } catch (IOException | ClassNotFoundException e) {
                    condition = false;
                }

            }


        }
    }

    /**
     * method that send a VCMessage to server
     *
     * @param message the message that has sent
     */
    synchronized public void update(MessageVC message) {
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

    public void requestNewUsername() {
        ConnectionClientSocket newClient = new ConnectionClientSocket(this.port, this.ip, this.view);
        newClient.setUsername(view.askNewUsername());
        newClient.run();
    }

    /**
     * method that manage the request for choose a map
     *
     * @param message message received by server
     */
    public void visit(MessageChooseMap message) {
        this.username = message.getUsername(); // setto il giocatore proprietario di questa connessione
        ArrayList<Cell[][]> cells = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> fav = new ArrayList<>();
        for (int i = 0; i < message.getMaps().size(); i++) {
            cells.add(message.getMaps().get(i).getCells());
            names.add(message.getMaps().get(i).getName());
            fav.add(message.getMaps().get(i).getDifficultyLevel());
        }
        Cell[][] mapPlayer = view.chooseMap(cells, username, names, fav); // invoco la view per scegliere la mappa
        int i = cells.indexOf(mapPlayer);
        update(new ResponseMap(message.getMaps().get(i), username)); // invio la risposta al server
    }

    /**
     * abstract method that manage the update of public news
     *
     * @param message message received by server
     */
    public void visit(MessagePublicInformation message) {
        view.setPublicInformation(message.getTitlePublicObjective(), message.getDescriptionPublicObjective(),
                message.getTitleTools(), message.getDescriptionTools());
    }

    /**
     * abstract method that manage the update of private news
     *
     * @param message message received by server
     */
    public void visit(MessageStart message) {
        view.setPrivateInformation(message.getTitlePrivateCard(), message.getDescriptionPrivateCard());
    }

    /**
     * abstract method that manage the update of turn's news.
     *
     * @param message message received by server
     */

    public void visit(MessageYourTurn message) {
        view.updateFavor(message.isPosDice(), message.isUseTools());
        view.turn(message.isPosDice(), message.isUseTools());
    }

    public void sendPosDice(Dice dice, int column, int row) {
        MessagePosDice message = new MessagePosDice();
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
        ArrayList<Object> obj = view.manageCE();
        message.setDice((Dice) obj.get(0));
        message.setRow((int) obj.get(1));
        message.setColumn((int) obj.get(2));
        message.setRowMit((int) obj.get(3));
        message.setColumnMit((int) obj.get(4));
        update(message);
    }

    public void visit(MessageCorkBackedStraightedge message) {
        ArrayList<Object> obj = view.manageCork();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        update(message);
    }

    public void visit(MessageEglomiseBrush message) {

        ArrayList<Object> obj = view.manageCE();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        message.setRowMit((int) obj.get(3));
        message.setColumnMit((int) obj.get(4));
        update(message);
    }

    public void visit(MessageFluxBrush message) {
        // dice dicebefore, dice diceafter, int rowdest, int columndest

        ArrayList<Object> obj = view.managefluxBrush();
        message.setDiceBefore((Dice) obj.get(0));
        message.setDice((Dice) obj.get(1));
        message.setRowDest((int) obj.get(2));
        message.setColumnDest((int) obj.get(3));
        update(message);
    }

    public void visit(MessageFluxRemover message) {
        // TO DO
        if (message.isA()) {
            ArrayList<Object> obj = view.manageFluxRemove2(message.getDice());
            message.setDice((Dice) obj.get(0));
            message.setRow((int) obj.get(1));
            message.setColumn((int) obj.get(2));
        } else {
            message.setDice(view.managefluxRemove());
        }
        update(message);
    }

    public void visit(MessageGrindingStone message) {
        ArrayList<Object> obj = view.manageGrinding();
        message.setDice((Dice) obj.get(0));
        message.setRow((int) obj.get(1));
        message.setColumn((int) obj.get(2));
        message.setDiceBefore((Dice) obj.get(3));
        update(message);
    }

    public void visit(MessageGrozingPliers message) {
        // Dice dice, int row, int column
        ArrayList<Object> obj = view.manageGrozing();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColDest((int) obj.get(2));
        update(message);
    }

    public void visit(MessageLathekin message) {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices
        ArrayList<Object> obj = view.manageLathekin();
        message.setRow1Mit((int) obj.get(0));
        message.setCol1Mit((int) obj.get(1));
        message.setRow1Dest((int) obj.get(2));
        message.setColumn1Dest((int) obj.get(3));
        message.setRow2Mit((int) obj.get(4));
        message.setCol2Mit((int) obj.get(5));
        message.setRow2Dest((int) obj.get(6));
        message.setColumn2Dest((int) obj.get(7));
        message.setDices((ArrayList<Dice>) obj.get(8));
        update(message);
    }

    public void visit(MessageLensCutter message) {
        ArrayList<Object> obj = view.manageLens();
        message.setDiceStock((Dice) obj.get(0));
        message.setDiceRound((Dice) obj.get(1));
        message.setNumberRound((int) obj.get(2));
        message.setRow((int) obj.get(3));
        message.setColumn((int) obj.get(4));
        update(message);
    }

    public void visit(MessageRunningPliers message) {
        ArrayList<Object> obj = view.manageCork();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
    }

    public void visit(MessageTapWheel message) {
        //Dice diceRound,  int row1, int column1, int row2, int column2,Arraylist Dice (dice1, Dice dice2), posizione dado
        // in roundscheme
        ArrayList<Object> obj = view.manageTap();
        message.setDiceRoundScheme((Dice) obj.get(0));
        message.setDiceToMove((ArrayList<Dice>) obj.get(5));
        message.setRow1Mit((int) obj.get(1));
        message.setRow2Mit((int) obj.get(3));
        message.setCol1Mit((int) obj.get(2));
        message.setCol2Mit((int) obj.get(4));
        message.setPosDiceinSchemeRound((int) obj.get(6));
        update(message);
    }

    @Override
    public void sendPassMove() {
        MessagePassTurn message = new MessagePassTurn();
        update(message);
    }

    @Override
    public void sendReconnect() {

        RequestReconnect message = new RequestReconnect();

        try {
            this.output.writeUnshared(message);
            this.output.flush();
            this.output.reset();
        } catch (IOException e) {
            try {

                System.setProperty("java.net.preferIPv4Stack", "true"); //setta preferenze di protocollo IP V4
                this.socket = new Socket(ip, port);
                this.output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                this.input = new ObjectInputStream(socket.getInputStream()); // inizializza la variabile input e output

            } catch (IOException f) {
                LOGGER.log(Level.SEVERE, f.toString(), f);
            }
            update(message);
            listener = new Listen(); // creo un oggetto ascoltatore
            listener.run(); // metto il client ad ascoltare i messaggi in arrivo dal server
        }
    }

    @Override
    public void sendDisconnect() {
        MessageDisconnect message = new MessageDisconnect();
        update(message);
    }

    public void visit(MessagePlayerDisconnect message) {

        view.addError(message.getMessage());

    }

    public void visit(MessageFinalGame message) {
        view.addLog(message.getMessage());
        view.addLog("Per iniziare una nuova partita riavvia il client");
        condition = false;
    }
}
