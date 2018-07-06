package it.polimi.se2018.server.deserializer.maps;

import it.polimi.se2018.server.deserializer.maps.cells.CellBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * class that create some data structure for the support of cards deserializing:
 * it takes all value about one map deserialized from json file
 * like string
 * @author Andrea Micucci
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
