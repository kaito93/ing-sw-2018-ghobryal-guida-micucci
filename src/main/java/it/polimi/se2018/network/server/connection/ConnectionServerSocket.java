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

    public ConnectionServerSocket(Socket client, RequestConnection obj,ObjectOutputStream out, ObjectInputStream inp){
        this.client=client;
        this.username=obj.getUser();
        input= inp;
        output = out;



    }

    public Socket getSocket(){
        return this.client;

    }

    public void send(Object message) throws IOException {
        this.output.writeObject(message);

    }

}
