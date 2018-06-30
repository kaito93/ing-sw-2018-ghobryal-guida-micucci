package it.polimi.se2018.shared.model_shared;

import it.polimi.se2018.shared.exception.InvalidValueException;

import java.io.Serializable;
import java.util.Random;

/**
 * Class Dice
 * contains all method to make usable the Dice
 *
 * @author Andrea Micucci
 */

public class Dice implements Serializable, Cloneable {

    private String[] dataColor = {"\u2718","\u0031","\u0032","\u0033","\u0034","\u0035","\u0036",null};
    private int value;
    private Color color;

    /**
     * class constructor
     */
    public Dice() {
        throwDice();
        color = null;
    }

    /**
     * throw the dice with a value between 1 and 6
     */
    public void throwDice() {
        Random x = new Random();
        value = x.nextInt(6) + 1;
    }

    /**
     * get the value of the dice
     *
     * @return an integer between 1 and 6 that is the value of the dice during the match
     */
    public int getValue() {
        return value;
    }

    /**
     * get the color of the dice
     *
     * @return one of the color presented in the color enumerations Color assigned to the dice
     */
    public Color getColor() {
        return color;
    }

    /**
     * changes the dice's value
     *
     * @param value dice's wanted value
     */
    public void setValue(int value) throws InvalidValueException {
        if (value < 1 || value > 6) throw new InvalidValueException();
        else this.value = value;
    }

    /**
     * sets the dice's color
     *
     * @param color a Color to give to "this" dice
     */
    //da testare
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * clones the dice
     * @return a cloned dice with the same status
     * @throws CloneNotSupportedException when it isn't possible to cloneObj this object
     */
    @Override
    public Dice clone() throws CloneNotSupportedException {
        return (Dice) super.clone();
    }

    /**
     * makes a number that occupies two spaces on the cli
     * @return a unicode number from one to six
     */
    @Override
    public String toString() {
        return dataColor[value];
    }
    /**
     * creates a symbolic null dice
     * @return a symbolic null dice
     */
    public static Dice diceNull(){
        Dice dice=new Dice();
        dice.value=0;
        dice.color=Color.NULL;
        return dice;
    }
}
