package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;


public class ConnectionServerSocket extends ConnectionServer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


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
            this.output.flush();
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

}
