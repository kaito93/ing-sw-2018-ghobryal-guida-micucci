package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.message.MessageCV;
import it.polimi.se2018.network.server.message.MessageSystem;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionClientSocket extends ConnectionClient {

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    String user;

    public static final int MVEvent=0;
    public static final int CVEvent=1;
    public static final int SystemMessage=2;

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
            System.out.println("Connessione col server stabilita");
            this.output= new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            this.input= new ObjectInputStream(socket.getInputStream()); // inizializza la variabile input e output

            sendMessage(new RequestConnection(user)); //chiamo il metodo per inviare la richiesta
            Listen list = new Listen(); // creo un oggetto ascoltatore
            list.run(); // metto il client ad ascoltare i messaggi in arrivo dal server



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    class Listen extends Thread{

        @Override
        public void run() {

            Message message; // crea una variabile per contenere il messaggio ricevuto

            while(true){

                try{
                     message= (Message)input.readObject(); // leggi il messaggio
                    if (message.getType()==CVEvent){// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV)message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni
                    }

                    if (message.getType()==SystemMessage){ // se il tipo di messaggio è di Sistema
                        MessageSystem mess = (MessageSystem) message.getEvent();
                        mess.accept(ConnectionClientSocket.this);
                    }

                    //TO DO: Continua con gli altri tipi

                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }



        }
    }

    @Override
    public void update(MessageVC event) {

    }

    public void requestNewUsername(){
        ConnectionClientSocket newClient= new ConnectionClientSocket(this.port,this.ip,this.view);
        newClient.run();
    }

    @Override
    public void sendMessage(Object message) {
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
