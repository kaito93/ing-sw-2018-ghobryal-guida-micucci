package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestReconnect implements MessageVC, MessageCV {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final long serialVersionUID = 2349636460774404003L;

    private String message;
    private int newIndex;
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di riconnessione");
    }
    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set a string message
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * method that return the message from server
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
