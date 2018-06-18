package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Map;

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
     * method that get the map that user have choose
     * @return a Map
     */
    public Map getMapChoose() {
        return mapChoose;
    }

    /**
     * method that get the username of this player
     * @return a string
     */
    public String getUsername() {
        return username;
    }
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.visit(this);
    }
}
