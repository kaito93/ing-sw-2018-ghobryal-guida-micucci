package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.io.Serializable;

/**
 * interface that manage the message berween Controller and View
 * @author Samuele Guida
 */
public interface MessageCV extends Serializable {
    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    void accept(ConnectionClientSocket client);

}
