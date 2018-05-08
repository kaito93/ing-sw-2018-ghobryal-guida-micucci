package it.polimi.se2018.network.client.connection;

public abstract class ConnectionClient {

    String ip;
    int port;

    public abstract void run();

}
