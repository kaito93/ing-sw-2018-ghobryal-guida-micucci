package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * class that manage a generic message_socket error send by server
 * @author Samuele Guida
 */
public class MessageError implements MessageSystem {

    private static final long serialVersionUID = 2509917442158095155L;
    private String errorMessage;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the error
     * @param errorMessage a string
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * method that return the error message_socket
     * @return a string
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
