package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

/**
 * Class that manage the request of a new Username
 */
public class MessageNewUsername implements MessageSystem {

    private static final long serialVersionUID = -6694182992571968678L;

    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.requestNewUsername();
    }
}
