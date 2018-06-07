package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;

public class MessageChooseMap implements MessageCV {

    private static final long serialVersionUID = 7757782513072303419L;
    ArrayList<Map> maps = new ArrayList<>();
    String username;

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.visit(this);
    }

    public void addMap(Map map){
        this.maps.add(map);
    }

    public void setPlayer(Player player) {
        this.username = player.getName();
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public String getUsername() {
        return username;
    }

}
