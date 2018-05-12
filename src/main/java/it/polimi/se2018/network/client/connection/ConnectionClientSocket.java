package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.client.message.SocketMassage;
import it.polimi.se2018.network.server.message.MessageCV;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClientSocket extends ConnectionClient {

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    String user;

    public static final int MVEvent=0;
    public static final int CVEvent=1;
    public static final int SystemMessage=2;

    public ConnectionClientSocket(int port, String ip,String user){

        this.ip=ip;
        this.port=port;
        this.user=user;

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

            sendRequestConnection(new RequestConnection(user)); //chiamo il metodo per inviare la richiesta
            Listen list = new Listen(); // creo un oggetto ascoltatore
            list.run(); // metto il client ad ascoltare i messaggi in arrivo dal server


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendRequestConnection(RequestConnection message){

        try {
            this.output.writeObject(message);
            System.out.print("Mando la richiesta per iscrivermi al gioco");
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    class Listen extends Thread{

        @Override
        public void run() {

            SocketMassage message; // crea una variabile per contenere il messaggio ricevuto

            while(true){

                try{
                     message= (SocketMassage)input.readObject(); // leggi il messaggio
                    if (message.getType()==CVEvent){// se il tipo di messaggio viene dal controller
                        MessageCV messag = (MessageCV)message.getEvent(); // casta il messaggio
                        messag.accept(ConnectionClientSocket.this); // accetta il messaggio e svolgi le azioni
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
}
