package it.polimi.se2018.client.network;

import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.shared.message_socket.RequestReconnect;
import it.polimi.se2018.shared.message_socket.client_to_server.*;
import it.polimi.se2018.shared.message_socket.server_to_client.*;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.message_socket.message_tools.*;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.client.view.View;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


/**
 * Class ConnectionClientSocket
 *
 * @author Samuele Guida
 * class that manage the network between client and server throws socket. Side Client
 */
public class ConnectionClientSocket implements ConnectionClient,Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private transient Socket socket;
    private transient Listen listener;
    private boolean condition = true;

    private String ip;
    private int port;
    private transient View view;
    private String username;

    /**
     * class constructor
     *
     * @param port integer of the port for the network
     * @param ip   integer of the ip for the network
     * @param view the vView for the interaction with the player
     */
    public ConnectionClientSocket(int port, String ip, View view, String username) {

        this.ip = ip;
        this.port = port;
        this.view = view;
        this.username = username;

    }

    /**
     * method that setup the network between client and server
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
        public synchronized void run() {

            //message_socket message_socket; // crea una variabile per contenere il messaggio ricevuto
            while (condition) {

                try {
                    Message message = (Message) input.readObject(); // leggi il messaggio

                    if (message.getType() == Message.CVEVENT) {// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV) message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni

                    }

                    if (message.getType() == Message.SYSTEMEVENT) { // se il tipo di messaggio è di Sistema
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
     * @param message the message_socket that has sent
     */
     public synchronized void update(MessageVC message) {
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
        ConnectionClientSocket newClient = new ConnectionClientSocket(this.port, this.ip, this.view, this.username);
        newClient.setUsername(view.askNewUsername());
        newClient.run();
    }

    /**
     * method that manage the request for choose a map
     *
     * @param message message_socket received by server
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
        Cell[][] mapPlayer = view.chooseMap(cells, username, names, fav); // invoco la vView per scegliere la mappa
        int i = cells.indexOf(mapPlayer);
        update(new ResponseMap(message.getMaps().get(i), username)); // invio la risposta al server
    }

    /**
     * method that manage the update of public news
     *
     * @param message message_socket received by server
     */
    public void visit(MessagePublicInformation message) {
        view.setPublicInformation(message.getTitlePublicObjective(), message.getDescriptionPublicObjective(),
                message.getTitleTools(), message.getDescriptionTools(), message.getScorePublicObjective());
    }

    /**
     * method that manage the update of private news
     *
     * @param message message_socket received by server
     */
    public void visit(MessageStart message) {
        view.setPrivateInformation(message.getTitlePrivateCard(), message.getDescriptionPrivateCard());
    }

    /**
     * method that manage the update of turn's news.
     *
     * @param message message_socket received by server
     */

    public void visit(MessageYourTurn message) {
        view.updateFavor(message.isPosDice(), message.isUseTools());
        view.turn();
    }

    /**
     * a
     * method that send the choice of set a dice
     * @param dice the dice chosen
     * @param column the column where set the dice
     * @param row the row where set the dice
     */
    public void sendPosDice(Dice dice, int column, int row) {
        MessagePosDice message = new MessagePosDice();
        message.setDice(dice);
        message.setColumn(column);
        message.setRow(row);
        update(message);
    }

    /**
     * method that send the choice of use a tool card
     * @param titleCardTool the title of the tool card
     */
    public void sendUseTool(String titleCardTool) {
        MessageUseTool message = new MessageUseTool();
        message.setTitle(titleCardTool);
        update(message);
    }

    /**
     * method that manage the error.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageError message) {
        view.manageError(message.getErrorMessage());
    }

    /**
     * method that manage the tool card CopperFoilBurnisher.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageCopperFoilBurnisher message) {
        setListCE(message);
    }

    /**
     * method that join the request for the tool cards Eglomise and CopperFoil
     * @param message the message to re-send
     */
    private void setListCE(MessageTool message){
        List<Object> obj = view.manageCE();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        message.setRowMit((int) obj.get(3));
        message.setColumnMit((int) obj.get(4));
        update(message);
    }

    /**
     * method that manage the tool card CorkBackedStraightedge.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageCorkBackedStraightedge message) {
        List<Object> obj = view.manageCork();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        update(message);
    }

    /**
     * method that manage the tool card EglomiseBrush.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageEglomiseBrush message) {

        setListCE(message);
    }

    /**
     * method that manage the tool card FluxBrush.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageFluxBrush message) {
        // dice dicebefore, dice diceafter, int rowdest, int columndest
        List<Object> obj = view.managefluxBrush();
        message.setDiceBefore((Dice) obj.get(0));
        message.setDice((Dice) obj.get(1));
        message.setRowDest((int) obj.get(2));
        message.setColumnDest((int) obj.get(3));
        update(message);
    }

    /**
     * method that manage the tool card FluxRemover.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageFluxRemover message) {
        if (message.isFirstMessage()) {
            List<Object> obj = view.manageFluxRemove2(message.getDice());
            message.setDice((Dice) obj.get(0));
            message.setRowDest((int) obj.get(1));
            message.setColumnDest((int) obj.get(2));
        } else {
            message.setDice(view.managefluxRemove());
        }
        update(message);
    }

    /**
     * method that manage the tool card GrindingStone.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageGrindingStone message) {
        List<Object> obj = view.manageGrinding();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        message.setDiceBefore((Dice) obj.get(3));
        update(message);
    }

    /**
     * method that manage the tool card GrozingPliers.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageGrozingPliers message) {
        // Dice dice, int row, int column
        List<Object> obj = view.manageGrozing();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
        update(message);
    }

    /**
     * method that manage the tool card Lathekin.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageLathekin message) {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices
        List<Object> obj = view.manageLathekin();
        message.setRow1Mit((int) obj.get(0));
        message.setCol1Mit((int) obj.get(1));
        message.setRow1Dest((int) obj.get(2));
        message.setColumn1Dest((int) obj.get(3));
        message.setRow2Mit((int) obj.get(4));
        message.setCol2Mit((int) obj.get(5));
        message.setRow2Dest((int) obj.get(6));
        message.setColumn2Dest((int) obj.get(7));
        message.setDices((List<Dice>) obj.get(8));
        update(message);
    }

    /**
     * method that manage the tool card LensCutter.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageLensCutter message) {
        List<Object> obj = view.manageLens();
        message.setDiceStock((Dice) obj.get(0));
        message.setDiceRound((Dice) obj.get(1));
        message.setNumberRound((int) obj.get(2));
        message.setRowDest((int) obj.get(3));
        message.setColumnDest((int) obj.get(4));
        update(message);
    }
    /**
     * method that manage the tool card RunningPliers.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageRunningPliers message) {
        List<Object> obj = view.manageCork();
        message.setDice((Dice) obj.get(0));
        message.setRowDest((int) obj.get(1));
        message.setColumnDest((int) obj.get(2));
    }

    /**
     * method that manage the tool card TapWheel.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageTapWheel message) {
        //Dice diceRound,  int row1Mit, int column1Mit, int row1Dest, int column1Dest, int row2Mit, int column2Mit,
        // int row2Dest, int column2Dest, Arraylist Dice (dice1, Dice dice2), posizione dado in roundscheme
        List<Object> obj = view.manageTap();
        message.setDiceRoundScheme((Dice) obj.get(0));
        message.setRow1Mit((int) obj.get(1));
        message.setCol1Mit((int) obj.get(2));
        message.setRow1Dest((int) obj.get(3));
        message.setColumn1Dest((int)obj.get(4));
        message.setRow2Mit((int) obj.get(5));
        message.setCol2Mit((int) obj.get(6));
        message.setRow2Dest((int)obj.get(7));
        message.setColumn2Dest((int)obj.get(8));
        message.setDiceToMove((List<Dice>) obj.get(9));
        message.setPosDiceinSchemeRound((int) obj.get(10));
        update(message);
    }


    /**
     * method that send a empty move
     */
    public void sendPassMove() {
        MessagePassTurn message = new MessagePassTurn();
        update(message);
    }

    /**
     * method that send a request to reconnect to the game
     */
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
            condition=true;
            listener.run(); // metto il client ad ascoltare i messaggi in arrivo dal server
        }
    }

    /**
     * method that send a message_socket of inactivity
     */
    public void sendDisconnect() {
        MessageDisconnect message = new MessageDisconnect();
        update(message);
    }

    /**
     * method that manage the disconnect of a player.
     *
     * @param message message_socket received by server
     */
    public void visit(MessagePlayerDisconnect message) {
        view.updateIndex(message.getIndex());
        view.addError(message.getMessage());

    }

    /**
     * method that manage the end of the game.
     *
     * @param message message_socket received by server
     */
    public void visit(MessageFinalGame message) {
        view.addLog(message.getMessage());
        view.addLog("Per iniziare una nuova partita riavvia il client");
        condition = false;
    }

    /**
     * method that manage the request of reconnect to the game.
     *
     * @param message message_socket received by server
     */
    public void visit(RequestReconnect message){
        view.updateIndex(message.getNewIndex());
        view.addLog(message.getMessage());

    }

    public void visit(MessageFinalScore message){
        view.seeScore(message.getPlayersFinal());
    }

    /**
     * method that set the username of this player
     * @param username username's player
     */
    public void setUsername(String username){
        this.username= username;
    }

    public String getUsername() {
        return username;
    }

    public View getView() {
        return view;
    }

    @Override
    public int receiveMapConn(List<Cell[][]> cells, List<String> names, List<Integer> fav) {
        //solo per RMI
        return 0;
    }

    @Override
    public void viewPrivateCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void viewPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTool, List<String> descriptionTool, List<Integer> publicScore) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void viewScore(List<Integer> finalScore) throws RemoteException {
        //solo pr RMI
    }

    @Override
    public void isTurn(boolean dice, boolean tool) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void requestNewUsernameRMI() throws RemoteException {
        //solo per RMI
    }

    @Override
    public void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools, RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void tapWheel(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void copperFoil(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void corkbacked(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void eglomise(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void fluxBrush(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void grinding(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void grozing(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void lathekin(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void lens(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void running(String title) throws RemoteException {
        //solo per RMI
    }

    @Override
    public void handleError(String error) throws RemoteException {
        //solo per RMI
    }
}
