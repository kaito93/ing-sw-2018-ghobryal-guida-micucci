package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

/**
 * class that manage a generic message error send by server
 * @author Samuele Guida
 */
public class MessageError implements MessageSystem {

    private static final long serialVersionUID = 2509917442158095155L;
    private String errorMessage;

    /**
     * method that accept this message client side
     * @param client connection socket client side
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
     * method that return the error message
     * @return a string
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
