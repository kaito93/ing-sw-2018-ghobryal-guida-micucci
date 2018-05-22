package it.polimi.se2018.network.client.message;

import it.polimi.se2018.model.Map;

public class ResponseMap implements MessageVC {
    Map mapChoose;

    public ResponseMap(Map map){
        this.mapChoose=map;
    }
}
