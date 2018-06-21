package it.polimi.se2018.server.deserializer.maps;

import it.polimi.se2018.server.deserializer.maps.cells.CellBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * class that create some data structure for the support of cards deserializing:
 * it takes all value about one map deserialized from json file
 * like string
 */
public class TransitionForMaps {

    private String title;
    private int level;
    private List<CellBuilder> maps;

    /**
     * class constructor: it build the arraylist and initialize the variable level
     */
    public TransitionForMaps(){
        level = 0;
        maps = new ArrayList<>();
    }

    /**
     * method that set the arraylist of strings that takes in input to the arraylist inside the class
     * @param map: arraylist of cell of the maps memorized like strings
     */
    public void setMap(List<CellBuilder> map) {
        this.maps = map;
    }

    /**
     * method that set the difficulty level of the maps
     * @param level an integer indicate the difficulty level of the maps
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * getter method to get the maps strings arraylist
     * @return the arraylist of string represent the map
     */
    public List<CellBuilder> getMap() {
        return maps;
    }

    /**
     * getter method to get the difficulty level of the map
     * @return an integer represent the difficulty level of the map
     */
    public int getLevel() {
        return level;
    }

    /**
     * getter method to get the map name
     * @return a string that represent the map name
     */
    public String getMapName() {
        return title;
    }
}
