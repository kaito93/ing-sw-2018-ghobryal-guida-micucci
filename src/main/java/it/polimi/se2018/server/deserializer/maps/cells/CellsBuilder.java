package it.polimi.se2018.server.deserializer.maps.cells;

import it.polimi.se2018.server.deserializer.maps.EntireMap;
import it.polimi.se2018.server.deserializer.maps.TransitionForMaps;
import it.polimi.se2018.shared.model_shared.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * abstract class for the building of the cells
 * implements observer interface
 * @author Andrea Micucci
 */
public abstract class CellsBuilder implements Observer {

    private String toBeCompared;
    private Cell cell;
    private TransitionForMaps trans;
    private List<CellBuilder> cells;
    private EntireMap mappaIntera;

    /**
     * class constructor: build the object in the class
     */
    CellsBuilder(){
        trans = new TransitionForMaps();
        cells = new ArrayList<>();
        mappaIntera = new EntireMap();
    }

    /**
     * update method from the Observer interface: it compare the string of the classes
     * and, if the compare is true, it create the object
     * @param arg represent the cell of the map that has to be built
     */
    @Override
    public void update(Observable o, Object arg) {
        trans = (TransitionForMaps) arg;
        cells = trans.getMap();
        mappaIntera.setTitle(trans.getMapName());
        mappaIntera.setLevel(trans.getLevel());
        for (CellBuilder map : cells) {
            if (map.getType().equalsIgnoreCase(toBeCompared)) {
                cell = this.createCell(map.getColor(), map.getValue(), map.getNumberCell());
                mappaIntera.setMatrix(cell);
            }
        }
    }

    /**
     * abstract method to create a cell, that take all the info about the cell, and build
     * the cell of the type shown in the update method
     * @param color of the cell
     * @param value of the cell
     * @param number of the cell
     * @return a created cell
     */
    public abstract Cell createCell(String color, int value, int number);

    /**
     * setter method for the string that has to be compared to determine the type of cell
     * @param toBeCompared string that has to be assigned
     */
    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    /**
     * setter method for a cell of the arraylist
     * @param cell that has to be setted
     */
    public void setCell(it.polimi.se2018.shared.model_shared.Cell cell) {
        this.cell = cell;
    }

    /**
     * getter method for the title of map
     * @return a string that is the name of the map
     */
    public String getEntireMapTitle(){
        return mappaIntera.getTitle();
    }

    /**
     * getter method for the map level
     * @return an integer represent the level
     */
    public int getEntireMapLevel(){
        return mappaIntera.getLevel();
    }

    /**
     * getter method for EntireMap type of the array list of cells
     * @return EntireMap object of the array list of cells
     */
    public EntireMap getMappaIntera() {
        return mappaIntera;
    }
}
