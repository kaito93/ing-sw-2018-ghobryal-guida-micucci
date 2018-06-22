package it.polimi.se2018.shared.message_socket.client_to_server;

import it.polimi.se2018.server.controller.Controller;

/**
 * Class that contain information about a turn passed
 * @author Samuele Guida
 */

public class MessagePassTurn implements MessageVC {
    private static final long serialVersionUID = -2296402075002947888L;
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.fakeMove();
    }
}
