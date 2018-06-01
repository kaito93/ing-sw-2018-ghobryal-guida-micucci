package it.polimi.se2018.model;

import it.polimi.se2018.model.exception.InvalidValueException;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Random;

/**Class Dice
 * contains all method to make usable the Dice
 * @author Andrea Micucci
 */

public class Dice implements Serializable {

    private int value;
    private Color color;
    
    /**class constructor
     */
    public Dice(){
        throwDice();
        color = null;
    }
    
    /** throw the dice with a value between 1 and 6
     */
    public void throwDice(){
        Random x = new Random();
        value = x.nextInt(6)+1;
    }
    
    /** get the value of the dice
     * @return an integer between 1 and 6 that is the value of the dice during the match
     */
    public int getValue(){
        return value;
    }

    /** get the color of the dice
     * @return one of the color presented in the color enumerations Color assigned to the dice
     */
    public Color getColor() {
        return color;
    }

    /** changes the dice's value
     * @param value dice's wanted value
     */
    public void setValue(int value) throws InvalidValueException {
        if (value<1 || value>6) throw new InvalidValueException();
        else this.value = value;
    }

    /**
     * sets the dice's color
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
