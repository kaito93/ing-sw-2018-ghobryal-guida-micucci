package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;

import java.io.Serializable;

/** abstract class Cell
 *  contains methods and datas about the cell of the matrix
 *  @author Andrea Micucci, Anton Ghobryal
 */

public abstract class Cell implements Serializable {

    protected int value;
    protected Color color;
    private Dice dice;
    private int numberCell;
    
    /** class constructor
     *  return a cell object with no dice inside
     */
    public Cell(){
        value=0;
        color=Color.NULL;
        dice = null;
        numberCell=0;
    }
    
    /** method that set the dice incide the cell
     * @param nextDice: dice the player want to put in the cell
     */
    public void setDice(Dice nextDice){
        try {
            dice = nextDice;
        }catch (NullPointerException e){
            dice = null;
        }
    }
    
    /** method that return the dice inside cell
     * @return the dice object inside the cell, null if dice isn't in it
     */
    public Dice getDice(){
        try {
            return dice;
        }catch (NullPointerException e){
            return null;
        }
    }
    
    /** method that return the color of the dice inside the cell
     */
    public abstract Color getColor();

    /** method that return the value of the dice inside the cell
     */
    public abstract int getValue();

    /**
     * sets the cell's value on the map
     * @param value cell's assigned value
     */
    public abstract void setValue(int value);

    /**
     * sets the cell's color on the map
     * @param color cell's assigned color
     */
    public abstract void setColor(Color color);

    /**
     * sets the cell's number in a sequential mood, like if it was in array list [1...20]
     * @param numberCell cell's assigned number
     */
    public void setNumberCell(int numberCell) {
        this.numberCell = numberCell;
    }

    /**
     * gets the cell's number
     * @return an integer of the cell's assigned number
     */
    public int getNumberCell() {
        return numberCell;
    }
    
}
