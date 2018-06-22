package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * class that manage the final message_socket from Server
 * @author Samuele Guida
 */
public class MessageFinalGame implements MessageSystem{

    private static final long serialVersionUID = 3911166895507190888L;
    private String message;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the message_socket to print
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * method that get the message_socket
     * @return a string
     */

    public String getMessage() {
        return message;
    }
}
