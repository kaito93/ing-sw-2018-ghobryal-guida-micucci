package it.polimi.se2018.client.view.ViewGuiPack.HostesPage;

import it.polimi.se2018.shared.model_shared.Cell;

import java.util.ArrayList;

/**
 * model class for the window of the other players
 * @author Andrea Micucci
 */
public class HostesModel {

    ArrayList<Cell[][]> maps = new ArrayList<>();
    ArrayList<String> user = new ArrayList<>();
    ArrayList<Integer> fav = new ArrayList<>();

    /**
     * empty class constructor
     */
    public HostesModel(){

    }

    /**
     * setter method for the favour
     * @param fav int of favour of the other players
     */
    public void setFav(ArrayList<Integer> fav) {
        this.fav = fav;
    }

    /**
     * setter method for the maps
     * @param maps an Arraylist of cell represent the maps of the others player
     */
    public void setMaps(ArrayList<Cell[][]> maps) {
        this.maps = maps;
    }

    /**
     * setter method for the username of the others player
     * @param user an Arraylist of string with the username
     */
    public void setUser(ArrayList<String> user) {
        this.user = user;
    }

    /**
     * getter method for the maps
     * @return an arraylist of matrix of cell
     */
    public ArrayList<Cell[][]> getMaps() {
        return maps;
    }

    /**
     * getter method for the favour
     * @return an Arraylist of integer represent the favour
     */
    public ArrayList<Integer> getFav() {
        return fav;
    }

    /**
     * getter method for the username
     * @return an ArrayList of String
     */
    public ArrayList<String> getUser() {
        return user;
    }
}
