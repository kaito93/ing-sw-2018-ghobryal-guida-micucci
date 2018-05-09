package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.io.Serializable;

public interface MessageCV extends Serializable {

    public void accept(ConnectionClientSocket socket);

}
