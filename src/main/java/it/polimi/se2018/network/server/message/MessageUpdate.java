package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.util.ArrayList;

public class MessageUpdate implements MessageCV {

    ArrayList<Map> maps = new ArrayList<>();
    ArrayList <String> users = new ArrayList<>();
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void addMaps(Map maps) {
        this.maps.add(maps);
    }

    @Override
    public void accept(ConnectionClient client) {

    }

    public void addUsers(String user) {
        this.users.add(user);
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }
}
