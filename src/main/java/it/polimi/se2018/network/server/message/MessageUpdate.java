package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.util.ArrayList;

public class MessageUpdate implements MessageCV {

    ArrayList<Cell[][]> cells = new ArrayList<>();
    ArrayList <String> users = new ArrayList<>();

    ArrayList<Boolean> useTools = new ArrayList<>();
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void addMaps(Map maps) {
        this.cells.add(maps.getCell());
    }

    @Override
    public void accept(ConnectionClient client) {
        client.visit(this);
    }

    public void addUsers(String user) {
        this.users.add(user);
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Cell[][]> getCells() {
        return cells;
    }

    public ArrayList<Boolean> getUseTools() {
        return useTools;
    }

    public void addUseTools(Boolean useTools) {
        this.useTools.add(useTools);
    }

    public void setCells(ArrayList<Cell[][]> cells) {
        this.cells = cells;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
