package it.polimi.se2018.util.deserializer;

import java.util.ArrayList;

/**
 * class that create some data structure for the support of cards deserializing:
 * it takes all value about one map deserialized from json file
 * like string
 */
public class TransitionForMaps {

    private String title;
    private int level;
    ArrayList<Maps> maps;

    /**
     * class constructor: it build the arraylist and initialize the variable level
     */
    public TransitionForMaps(){
        int level = 0;
        maps = new ArrayList<Maps>();
    }

    /**
     * method that set the arraylist of strings that takes in input to the arraylist inside the class
     * @param map: arraylist of cell of the maps memorized like strings
     */
    public void setMap(ArrayList<Maps> map) {
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
     * method that set the maps name
     * @param mapName that is a string with the name of the map
     */
    public void setMapName(String mapName) {
        this.title = mapName;
    }

    /**
     * getter method to get the maps strings arraylist
     * @return the arraylist of string represent the map
     */
    public ArrayList<Maps> getMap() {
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
