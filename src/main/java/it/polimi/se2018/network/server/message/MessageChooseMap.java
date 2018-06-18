package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;

/**
 * class that manage the information send by Server for the choose of a Map
 * @author Samuele Guida
 */

public class MessageChooseMap implements MessageCV {

    private static final long serialVersionUID = 7757782513072303419L;
    private ArrayList<Map> maps = new ArrayList<>();
    private String username;

    /**
     * method that accept this message client side
     * @param client connection socket client side
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
     * @return
     */
    public ArrayList<Map> getMaps() {
        return maps;
    }

    /**
     * method that return the username of the client
     * @return
     */
    public String getUsername() {
        return username;
    }

}
