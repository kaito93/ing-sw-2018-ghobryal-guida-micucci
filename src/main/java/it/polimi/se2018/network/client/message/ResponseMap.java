package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;

public class ResponseMap implements MessageVC {
    Map mapChoose;
    String username;

    public ResponseMap(Map map, String user ){
        this.mapChoose=map;
        this.username=user;
    }

    public Map getMapChoose() {
        return mapChoose;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void accept(Controller controller) {
        controller.visit(this);
    }
}
