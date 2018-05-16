package it.polimi.se2018.model.cell;

/** abstract class Cell
 *  contains methods and datas about the cell of the matrix
 *  @author Andrea Micucci, Anton Ghobryal
 */

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;

public abstract class Cell {

    private Dice dice;
    
    /** class constructor
     *  @return a cell object with no dice inside
     */
    public Cell(){
        dice = null;
    }
    
    /** method that set the dice incide the cell
     * @param nextDice: dice the player want to put in the cell
     */
    public void setDice(Dice nextDice){
        dice = nextDice;
    }
    
    /** method that return the dice inside cell
     * @return: the dice object inside the cell, null if dice isn't in it
     */
    public Dice getDice(){
        return dice;
    }
    
    /** method that return the color of the dice inside the cell
     */
    public abstract Color getColor();

    /** method that return the value of the dice inside the cell
     */
    public abstract int getValue();
}
