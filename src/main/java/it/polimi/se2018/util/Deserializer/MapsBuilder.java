package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * abstract class for the building of the maps
 * implements observer interface
 */
public abstract class MapsBuilder implements Observer {

    String toBeCompared;
    Cell cell;
    TransitionForMaps trans;
    ArrayList<Maps> maps;
    ArrayList<Cell> toBeReturn;
    entireMap mappaIntera;

    /**
     * class constructor: build the object in the class
     */
    public MapsBuilder(){
        trans = new TransitionForMaps();
        maps = new ArrayList<>();
        toBeReturn = new ArrayList<>();
        mappaIntera = new entireMap();
    }



    @Override
    /**
     * update method from the Observer interface: it compare the string of the classes
     * and, if the compare is true, it create the object
     * @param arg represent the cell of the map that has to be built
     */
    public void update(Observable o, Object arg) {
        trans = (TransitionForMaps) arg;
        maps = trans.getMap();
        mappaIntera.setTitle(trans.getMapName());
        mappaIntera.setLevel(trans.getLevel());
        for(int index = 0; index<maps.size(); index++)
        {
            if(maps.get(index).getType().equalsIgnoreCase(toBeCompared)) {
                cell = this.createCell(maps.get(index).getColor(), maps.get(index).getValue(), maps.get(index).getNumberCell());
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
     * getter method for the arraylist of the cell
     * @return an arraylist of cell
     */
    public ArrayList<Cell> getToBeReturn() {
        return toBeReturn;
    }

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
    public void setCell(Cell cell) {
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
}
