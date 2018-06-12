package it.polimi.se2018.model;

import it.polimi.se2018.model.exception.InvalidValueException;

import java.io.Serializable;
import java.util.Random;

/**
 * Class Dice
 * contains all method to make usable the Dice
 *
 * @author Andrea Micucci
 */

public class Dice implements Serializable, Cloneable {

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

    @Override
    public Dice clone() throws CloneNotSupportedException {
        return (Dice) super.clone();
    }

    @Override
    public String toString() {
        String a;
        switch (value) {
            case 0:
                a="\u2718";
                break;
            case 1:
                a = "\u0031";
                break;
            case 2:
                a = "\u0032";
                break;
            case 3:
                a = "\u0033";
                break;
            case 4:
                a = "\u0034";
                break;
            case 5:
                a = "\u0035";
                break;
            case 6:
                a = "\u0036";
                break;
            default:
                a=null;
                break;
        }
        return a;
    }
}
