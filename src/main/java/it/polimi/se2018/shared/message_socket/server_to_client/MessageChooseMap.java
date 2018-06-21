package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.client.network.ConnectionClientSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * class that manage the information send by Server for the choose of a Map
 * @author Samuele Guida
 */

public class MessageChooseMap implements MessageCV {

    private static final long serialVersionUID = 7757782513072303419L;
    private ArrayList<Map> maps = new ArrayList<>();
    private String username;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that add a map to the arraylist of maps
     * @param map a map
     */
    public void addMap(Map map){
        this.maps.add(map);
    }

    /**
     * method that set a player
     * @param player a player
     */
    public void setPlayer(Player player) {
        this.username = player.getName();
    }

    /**
     * method that return an arrayList of maps
     * @return a list of map
     */
    public List<Map> getMaps() {
        return maps;
    }

    /**
     * method that return the username of the client
     * @return a string
     */
    public String getUsername() {
        return username;
    }

}
