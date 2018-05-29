package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.message.MessageCV;
import it.polimi.se2018.network.server.message.MessageSystem;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class ConnectionClientSocket extends ConnectionClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    String user;
    Listen listener;


    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMMESSAGE=2;

    public ConnectionClientSocket(int port, String ip, View view){

        this.ip=ip;
        this.port=port;
        this.view = view;
        this.user= view.request("Quale sarà il tuo username?");

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

            sendMessage(new RequestConnection(user)); //chiamo il metodo per inviare la richiesta
            Listen list = new Listen(); // creo un oggetto ascoltatore
            list.start(); // metto il client ad ascoltare i messaggi in arrivo dal server





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
                        update(messag);
                    }

                    if (message.getType()==SYSTEMMESSAGE){ // se il tipo di messaggio è di Sistema
                        MessageSystem mess = (MessageSystem) message.getEvent();
                        mess.accept(ConnectionClientSocket.this);
                    }

                    //TO DO: Continua con gli altri tipi

                }
                catch (IOException|ClassNotFoundException e) {
                    condition=false;
                    e.printStackTrace();
                }

            }



        }
    }

    @Override
    public void update(MessageCV event) {
        event.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni
    }

    public void requestNewUsername(){
        ConnectionClientSocket newClient= new ConnectionClientSocket(this.port,this.ip,this.view);
        newClient.run();
    }

    @Override
    public void sendMessage(Object message) {
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
