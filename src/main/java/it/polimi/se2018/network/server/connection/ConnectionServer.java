package it.polimi.se2018.network.server.connection;


import java.io.ObjectInputStream;

public abstract class ConnectionServer {

    String username;

    public abstract void send (Object message);

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract ObjectInputStream getInput();

}
