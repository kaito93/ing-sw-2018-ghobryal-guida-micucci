package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.util.Logger;

import java.util.logging.Level;


public class RequestConnection implements MessageVC{
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    String msg;
    String username;

    public RequestConnection(String user){
        msg="connesso";
        username=user;
    }

    public String getUser(){
        return username;

    }

    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di connessione");

    }
}
