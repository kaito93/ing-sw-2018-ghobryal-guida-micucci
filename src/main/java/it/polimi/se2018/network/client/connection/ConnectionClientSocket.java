package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.*;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

public class ConnectionClientSocket extends ConnectionClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    Listen listener;


    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMMESSAGE=2;

    public ConnectionClientSocket(int port, String ip, View view){

        this.ip=ip;
        this.port=port;
        this.view = view;
        this.username= view.request("Quale sarà il tuo username?");

    }

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
            listener.start(); // metto il client ad ascoltare i messaggi in arrivo dal server





        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }

    class Listen extends Thread{

        @Override
        public void run() {

            Message message; // crea una variabile per contenere il messaggio ricevuto
            boolean condition=true;
            while(condition){

                try{
                     message= (Message)input.readObject(); // leggi il messaggio
                    if (message.getType()==CVEVENT){// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV)message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni

                    }

                    if (message.getType()==SYSTEMMESSAGE){ // se il tipo di messaggio è di Sistema
                        MessageSystem mess = (MessageSystem) message.getEvent();
                        mess.accept(ConnectionClientSocket.this);
                    }
                    if (message.getType()==Message.MVEVENT){ // se il tipo di messaggio è di Sistema
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


    public void update (MessageVC message){
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void requestNewUsername(){
        ConnectionClientSocket newClient= new ConnectionClientSocket(this.port,this.ip,this.view);
        newClient.run();
    }

    @Override
    public void visit(MessageChooseMap message) {
        this.username=message.getUsername(); // setto il giocatore proprietario di questa connessione
        ArrayList<Cell[][]> cells = new ArrayList<>();
        for (int i=0; i<message.getMaps().size();i++)
            cells.add(message.getMaps().get(i).getCell());
        Cell[][] mapPlayer = view.chooseMap(cells,username); // invoco la view per scegliere la mappa
        int i= cells.indexOf(mapPlayer);
        update(new ResponseMap(message.getMaps().get(i),username)); // invio la risposta al server
    }

    @Override
    public void visit(MessagePublicInformation message) {
        view.setPublicInformation(message.getTitlePublicObjective(),message.getDescriptionPublicObjective(),
                message.getTitleTools(),message.getDescriptionTools());
    }

    @Override
    public void visit(MessageStart message) {
        view.setPrivateInformation(message.getTitlePrivateCard(),message.getDescriptionPrivateCard());
    }


    @Override
    public void visit(MessageYourTurn message) {
        view.updateFavor(message.getFavor());
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
}
