package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.io.Serializable;

/**
 * Interface that manage the system message. Only a news message
 * @author Samuele Guida
 */
public interface MessageSystem extends Serializable {
    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
     void accept(ConnectionClientSocket client);
}
