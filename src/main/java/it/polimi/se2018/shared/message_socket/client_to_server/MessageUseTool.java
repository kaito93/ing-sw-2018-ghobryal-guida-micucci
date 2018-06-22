package it.polimi.se2018.shared.message_socket.client_to_server;

import it.polimi.se2018.server.controller.Controller;


/**
 * Class that contain information about a move: use a tool card
 * @author Samuele Guida
 */
public class MessageUseTool implements MessageVC {

    private static final long serialVersionUID = -1139168132175799279L;
    private String title;

    /**
     * method that set the title of a card
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.useTools(title);
    }
}
