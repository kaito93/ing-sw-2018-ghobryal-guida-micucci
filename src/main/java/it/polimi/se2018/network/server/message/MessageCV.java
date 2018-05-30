package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.io.Serializable;

public interface MessageCV extends Serializable {

    void accept(ConnectionClient client);

}
