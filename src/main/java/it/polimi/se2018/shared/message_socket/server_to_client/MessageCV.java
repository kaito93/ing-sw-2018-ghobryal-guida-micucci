package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

import java.io.Serializable;

/**
 * interface that manage the message_socket berween Controller and View
 * @author Samuele Guida
 */
public interface MessageCV extends Serializable {
    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    void accept(ConnectionClientSocket client);

}
