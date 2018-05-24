package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.network.server.message.MessageCV;

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

    public abstract Object readInput();
}
