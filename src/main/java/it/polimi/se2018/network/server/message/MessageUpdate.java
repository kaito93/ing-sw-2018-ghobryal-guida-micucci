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


    protected ArrayList<Map> maps;
    protected ArrayList<Cell[][]> cells = new ArrayList<>();
    protected ArrayList<String> users = new ArrayList<>();

    protected ArrayList<Boolean> useTools;

    protected ArrayList<Dice> stock;
    protected RoundSchemeCell roundSchemeMap[];

    protected String message;

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

    public void setMaps(ArrayList<Map> maps) {
        this.maps = maps;
    }

   /* public void setCells(ArrayList<Map> maps) {

        for (int i=0; i<maps.size();i++){
            Cell[][] cell = new Cell[maps.get(0).getCells().length][maps.get(0).getCells()[0].length];
            for (int j=0; j<maps.get(i).getCells().length;j++){
                for (int k=0;k<maps.get(i).getCells()[j].length;k++)
                    cell[j][k]=maps.get(i).getCells()[j][k];
            }
            this.cells.add(cell);
        }

    }
        for (int mappa = 0; mappa < maps.size(); mappa++) {
            Cell[][] myInt = new Cell[maps.get(mappa).getCells().length][];
            for (int i = 0; i < maps.get(mappa).getCells().length; i++) {
                myInt[i] = maps.get(mappa).getCells()[i].clone();
            }
            cells.add(myInt);
        }
    }*/
}
