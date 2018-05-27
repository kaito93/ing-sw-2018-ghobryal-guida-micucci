package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.util.ArrayList;

public class MessageChooseMap implements MessageCV {

    ArrayList<Map> maps = new ArrayList<>();
    Player player;

    @Override
    public void accept(ConnectionClient socket) {
        socket.chooseMap(maps,player);
    }

    public void addMap(Map map){
        this.maps.add(map);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
