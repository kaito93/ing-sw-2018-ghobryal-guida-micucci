package it.polimi.se2018.shared.message_socket.client_to_server;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.Logger;
import java.util.logging.Level;

/**
 * Class that contain information about a request of connection
 * @author Samuele Guida
 */
public class RequestConnection implements MessageVC{
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final long serialVersionUID = 762745222969866032L;

    private String username;

    /**
     * method constructor
     * @param user the username that user have choose
     */
    public RequestConnection(String user){
        username=user;
    }

    /**
     * method that return the username
     * @return a string
     */
    public String getUser(){
        return username;

    }
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        LOGGER.log(Level.SEVERE,"Ho ricevuto una richiesta di connessione");

    }
}
