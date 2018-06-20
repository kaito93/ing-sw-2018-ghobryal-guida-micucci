package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

import java.io.Serializable;

/**
 * Interface that manage the system message_socket. Only a news message_socket
 * @author Samuele Guida
 */
public interface MessageSystem extends Serializable {
    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
     void accept(ConnectionClientSocket client);
}
