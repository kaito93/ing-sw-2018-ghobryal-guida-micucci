package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.network.client.message.RequestConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionServerSocket extends ConnectionServer {

    Socket client;
    ObjectOutputStream output;
    ObjectInputStream input;
    String username;

    public ConnectionServerSocket(Socket client, RequestConnection obj){
        this.client=client;
        this.username=obj.getUser();
        try {
                input= new ObjectInputStream(this.client.getInputStream());
                output = new ObjectOutputStream(this.client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Socket getSocket(){
        return this.client;

    }

    public void send(Object message) throws IOException {
        this.output.writeObject(message);

    }

}
