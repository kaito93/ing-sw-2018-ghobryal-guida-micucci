package it.polimi.se2018.shared.message_socket.client_to_server;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.model.Map;

/**
 * Class that contain information about a response of map
 * @author Samuele Guida
 */
public class ResponseMap implements MessageVC {
    private static final long serialVersionUID = -880966329705705431L;
    private Map mapChoose;
    private String username;

    /**
     * method constructor
     * @param map the map that user have choose
     * @param user the username of this player
     */
    public ResponseMap(Map map, String user ){
        this.mapChoose=map;
        this.username=user;
    }

    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.map(username,mapChoose);
    }
}
