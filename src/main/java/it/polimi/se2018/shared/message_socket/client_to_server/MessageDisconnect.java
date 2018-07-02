package it.polimi.se2018.shared.message_socket.client_to_server;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.message_socket.MessageVC;

/**
 * Class that contain information about a disconnection of a player
 * @author Samuele Guida
 */

public class MessageDisconnect implements MessageVC {

    private static final long serialVersionUID = -7488306524541797257L;
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        // non viene usato.
    }
}

