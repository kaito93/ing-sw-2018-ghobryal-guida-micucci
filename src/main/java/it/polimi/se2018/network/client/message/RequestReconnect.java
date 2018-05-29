package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestReconnect implements MessageVC{

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di riconnessione");
    }
}
