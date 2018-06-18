package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

/**
 * method that manage the final message from Server
 * @author Samuele Guida
 */
public class MessageFinalGame implements MessageSystem{

    private static final long serialVersionUID = 3911166895507190888L;
    private String message;

    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the message to print
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * method that get the message
     * @return a string
     */

    public String getMessage() {
        return message;
    }
}
