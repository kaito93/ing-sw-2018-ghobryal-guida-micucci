package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.view.View;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * class that manage the update of the client with a new news
 * @author Samuele Guida
 */
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

    /**
     * Method that return the new Round Scheme Map
     * @return a matrix of RoundSchemeCell
     */
    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    /**
     * method that return the new Stock
     * @return an arraylist of dice
     */
    public ArrayList<Dice> getStock() {
        return stock;
    }

    /**
     * method that set the new round scheme map
     * @param roundSchemeMap a matrix of roundSchemeCell
     */
    public void setRoundSchemeMap(RoundSchemeCell[] roundSchemeMap) {
        this.roundSchemeMap = roundSchemeMap;
    }

    /**
     * method that set the new stock
     * @param stock an arraylist of dice
     */
    public void setStock(ArrayList<Dice> stock) {
        this.stock = stock;
    }

    /**
     * method that set a message
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * method that returns the users in game
     * @return an arraylist of strings
     */
    public ArrayList<String> getUsers() {
        return users;
    }

    /**
     * method that returns the message
     * @return a string
     */
    public String getMessage() {
        return message;
    }

    /**
     * method that returns the maps of all players
     * @return an arraylist of matrix of cells
     */
    public ArrayList<Cell[][]> getCells() {
        return cells;
    }

    /**
     * method that returns the usage of all tool cards in game
     * @return an arraylist of boolean
     */
    public ArrayList<Boolean> getUseTools() {
        return useTools;
    }
    /**
     * method that accept this message Server side
     * @param view an istance of Virtual View server side
     */
    @Override
    public void accept(View view) {
        view.accept(this);
    }

    /**
     * method that set the usage of all tool cards in game
     * @param useTools an arraylist of boolean
     */
    public void setUseTools(ArrayList<Boolean> useTools) {
        this.useTools = useTools;
    }

    /**
     * method that set the users in game
     * @param users an arraylist of strings
     */
    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }


    // DA TESTARE

    /**
     * method that set the map of all players
     * @param maps an arraylist of maps
     */
    public void setCells(ArrayList<Map> maps) {
        try {
            for (int i=0; i<maps.size();i++)
                cells.add(maps.get(i).getCells());
        }
        catch (NullPointerException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }

    /**
     * method that returns the number of favor that is not used in this game by all players
     * @return an arraylist of integer
     */
    public ArrayList<Integer> getFavUsers() {
        return favUsers;
    }

    /**
     * method that set the number of favor that is not used in this game by all players
     * @param favUsers an arraylist of integer
     */
    public void setFavUsers(ArrayList<Integer> favUsers) {
        this.favUsers = favUsers;
    }
}
