package it.polimi.se2018.util.Deserializer;

/**
 * class of support to deserialize the cell in json file
 * like strings
 */
public class Maps {

    private int numbercell;
    private String type;
    private String color;
    private int value;

    /**
     * class constructor: set the numbercell and the value of cell to default value
     * 0
     */
    public Maps(){
        numbercell = 0;
        value = 0;
    }

    /**
     * setter method for color
     * @param color a string represent the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * setter method for numbercell
     * @param numberCell an integer that represent the number of the cell
     */
    public void setNumberCell(int numberCell) {
        this.numbercell = numberCell;
    }

    /**
     * setter method for the type of Cell
     * @param type a string that represent the type of cell
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * setter method for the value of the cell
     * @param value an integer represent the value of the cell
     */
    public void setValue(int value) {
        this.value = value;
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
        return numbercell;
    }
}
