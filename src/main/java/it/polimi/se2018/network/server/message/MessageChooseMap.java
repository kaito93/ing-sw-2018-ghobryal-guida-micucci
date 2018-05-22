package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageChooseMap implements MessageCV {

    ArrayList<Map> maps = new ArrayList<Map>();

    @Override
    public void accept(ConnectionClient socket) {
        socket.chooseMap(maps);
    }

    public void addMap(Map map){
        this.maps.add(map);
    }
}
