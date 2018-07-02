package it.polimi.se2018.shared.message_socket;

import it.polimi.se2018.server.controller.Controller;

import java.io.Serializable;

/**
 * interface that manage the message_socket between View and Controller
 * @author Samuele Guida
 */

public interface MessageVC extends Serializable {
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    void accept(Controller controller);
}
