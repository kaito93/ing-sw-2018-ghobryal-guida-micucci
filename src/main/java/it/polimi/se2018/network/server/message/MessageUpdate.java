package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.view.View;

import java.util.ArrayList;
import java.util.logging.Level;

public class MessageUpdate implements MessageMV {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    private static final long serialVersionUID = 2201748410369437792L;


    private ArrayList<Cell[][]> cells = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<Integer> favUsers = new ArrayList<>();

    private ArrayList<Boolean> useTools;

    private ArrayList<Dice> stock;
    private RoundSchemeCell roundSchemeMap[];

    private String message;

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
        try {
            for (int i=0; i<maps.size();i++)
                cells.add(maps.get(i).getCells());
        }
        catch (NullPointerException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }

    public ArrayList<Integer> getFavUsers() {
        return favUsers;
    }

    public void setFavUsers(ArrayList<Integer> favUsers) {
        this.favUsers = favUsers;
    }
}
