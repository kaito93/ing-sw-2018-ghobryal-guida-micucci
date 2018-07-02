package it.polimi.se2018.server.deserializer.maps.cells;

/**
 * class of support to deserializer the cell in json file
 * like strings
 */
public class CellBuilder {

    private int numberCell;
    private String type;
    private String color;
    private int value;

    /**
     * class constructor: set the numberCell and the value of cell to default value
     * 0
     */
    public CellBuilder(){
        numberCell = 0;
        value = 0;
    }

    /**
     * getter method for the type of the cell
     * @return a string represent the type of cell
     */
    public String getType() {
        return type;
    }

    /**
     * getter method for the value of the cell
     * @return an integer represent the value of the cell
     */
    public int getValue() {
        return value;
    }

    /**
     * getter method for the color
     * @return a string represent the color of the cell
     */
    public String getColor() {
        return color;
    }

    /**
     * getter method for the number of cell
     * @return an integer represent the number of cell
     */
    public int getNumberCell() {
        return numberCell;
    }
}
