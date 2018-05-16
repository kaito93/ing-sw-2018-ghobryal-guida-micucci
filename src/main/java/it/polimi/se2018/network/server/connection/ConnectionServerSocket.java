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


    public ConnectionServerSocket(Socket client, RequestConnection obj,ObjectOutputStream out, ObjectInputStream inp){
        this.client=client;
        this.setUsername(obj.getUser());
        input= inp;
        output = out;



    }

    public Socket getSocket(){
        return this.client;

    }

    public ObjectInputStream getInput(){
        return this.input;
    }

    public void send(Object message) {
        try {
            this.output.writeObject(message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
