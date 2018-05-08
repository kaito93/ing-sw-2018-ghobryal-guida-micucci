package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.network.client.message.RequestConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClientSocket extends ConnectionClient {

    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;

    public ConnectionClientSocket(int port, String ip){

        this.ip=ip;
        this.port=port;

    }

    @Override
    public void run() {

        try {
            System.setProperty("java.net.preferIPv4Stack" , "true"); //setta preferenze di protocollo IP V4
            this.socket = new Socket(ip, port); // crea un collegamento socket con il server
            System.out.println("Connessione col server stabilita");
            this.output= new ObjectOutputStream(socket.getOutputStream());
            this.input= new ObjectInputStream(socket.getInputStream()); // inizializza la variabile input e output
            sendRequestConnection(new RequestConnection()); // manda la richiesta di connessione al server
            System.out.println("Invio richiesta");


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

}
