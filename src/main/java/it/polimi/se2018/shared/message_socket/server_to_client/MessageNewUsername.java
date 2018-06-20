package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * Class that manage the request of a new Username
 */
public class MessageNewUsername implements MessageSystem {

    private static final long serialVersionUID = -6694182992571968678L;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.requestNewUsername();
    }
}
