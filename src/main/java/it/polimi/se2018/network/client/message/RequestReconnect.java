package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestReconnect implements MessageVC, MessageCV {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final long serialVersionUID = 2349636460774404003L;

    String message;
    int newIndex;

    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di riconnessione");
    }

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }
}
