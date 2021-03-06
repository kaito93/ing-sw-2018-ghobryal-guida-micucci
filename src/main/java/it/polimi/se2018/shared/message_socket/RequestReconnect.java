package it.polimi.se2018.shared.message_socket;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.client.network.ConnectionClientSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contain information about a request of reconnection
 * @author Samuele Guida
 */
public class RequestReconnect implements MessageVC, MessageCV {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final long serialVersionUID = 2349636460774404003L;

    private String message;
    private int newIndex;
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di riconnessione");
    }
    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set a string message_socket
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * method that return the message_socket from server
     * @return a string
     */
    public String getMessage() {
        return message;
    }

    /**
     * method that set the new index in the arrayList of players
     * @param newIndex an integer
     */
    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    /**
     * method that return the new index
     * @return an integer
     */
    public int getNewIndex() {
        return newIndex;
    }
}
