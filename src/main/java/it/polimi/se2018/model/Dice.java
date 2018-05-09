package it.polimi.se2018.model;

/**Class Dice
* contains all method to make usable the Dice
* @author Andrea Micucci
*/
import java.util.Random;

public class Dice {

    int value;
    Color color;
    
    /**class constructor
     * @param no parameters
     */
    public Dice(){
        value = 1;
        color = null;
    }
    
    /** throw the dice
     * @param no parameters
     * @return an integer between 1 and 6
     */
    public void throwDice(){
        Random x = new Random();
        value = x.nextInt(6)+1;
    }
    
    /** get the value of the dice
     * @param no parameters
     * @return an integer between 1 and 6 that is the value of the dice during the match
     */
    public int getValue(){
        return value;
    }

    /** get the color of the dice
     * @param no parameters
     * @return one of the color presented in the color enumerations Color assigned to the dice
     */
    public Color getColor() {
        return color;
    }
}
