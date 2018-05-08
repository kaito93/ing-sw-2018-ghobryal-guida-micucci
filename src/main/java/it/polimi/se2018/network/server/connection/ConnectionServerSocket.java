package it.polimi.se2018.network.server.connection;

import java.net.Socket;

public class ConnectionServerSocket extends ConnectionServer {

    Socket client;

    public ConnectionServerSocket(Socket client){
        this.client=client;

    }

}
