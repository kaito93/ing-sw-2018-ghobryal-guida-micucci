package it.polimi.se2018.server.deserializer.maps;

import it.polimi.se2018.shared.model_shared.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * class that is used to make support data structure for deserializer the maps
 * it represent a map deserialized from json file with the arraylist of matrix
 * in place of the GlassWindow matrix
 */
public class EntireMap {
    private String title;
    private int level;
    private List<Cell> matrix;

    /**
     * class constructor, initialize the variable level to 0
     * and create the matrix arraylist
     */
    public EntireMap()
    {
        level = 0;
        matrix = new ArrayList<>();
    }

    /**
     * setter method for the arraylist of cell
     * @param matrix arraylist that has to be setted
     */
    void setAllMatrix(List<Cell> matrix) {
        this.matrix = matrix;
    }

    /**
     * setter method for one cell of the matrix
     * @param cellOfMatrix cell that has to be setted
     */
    public void setMatrix(Cell cellOfMatrix) {
        this.matrix.add(cellOfMatrix);
    }


    /**
     * getter method that return a cell of the arraylist of Cell
     * @param index of the cell that need to return
     * @return a cell object included into the arraylist
     */
    Cell getCellOfMatrix(int index){
        return this.getMatrix().get(index);
    }

    /**
     * setter method for the title of the map
     * @param title a string that represent the title of the map
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setter method for the difficulty level of the map
     * @param level that has to be setted
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * getter method for the title of the map
     * @return a string that represent the title of the map
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter method for the difficulty level of the map
     * @return an integer represent the level of difficulty of map
     */
    public int getLevel() {
        return level;
    }

    /**
     * getter method that return the arraylist of cell that compose the matrix
     * @return an arraylist of cell
     */
    public List<Cell> getMatrix() {
        return matrix;
    }
}
