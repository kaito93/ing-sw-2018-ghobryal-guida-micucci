package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.view.View;

import java.util.ArrayList;

public class MessageUpdate implements MessageMV {

    ArrayList<Cell[][]> cells = new ArrayList<>();
    ArrayList <String> users = new ArrayList<>();

    ArrayList<Boolean> useTools = new ArrayList<>();

    ArrayList<Dice> stock = new ArrayList<>();
    RoundSchemeCell roundSchemeMap[];

    String message;

    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    public ArrayList<Dice> getStock() {
        return stock;
    }

    public void setRoundSchemeMap(RoundSchemeCell[] roundSchemeMap) {
        this.roundSchemeMap = roundSchemeMap;
    }

    public void setStock(ArrayList<Dice> stock) {
        this.stock = stock;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addMaps(Map maps) {
        this.cells.add(maps.getCells());
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

    @Override
    public void accept(View view) {
        view.accept(this);
    }

    public void setUseTools(ArrayList<Boolean> useTools) {
        this.useTools = useTools;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void setCells(ArrayList<Map> maps) {
        for (int i=0; i<maps.size();i++)
            cells.add(maps.get(i).getCells());
    }
}
