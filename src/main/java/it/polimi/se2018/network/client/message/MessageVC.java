package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

import java.io.Serializable;

/**
 * interface that manage the message between View and Controller
 * @author Samuele Guida
 */

public interface MessageVC extends Serializable {
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    void accept(Controller controller);
}
