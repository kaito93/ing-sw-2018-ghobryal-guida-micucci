package it.polimi.se2018.model;

/**Class Dice
* contains all method to make usable the Dice
* @author Andrea Micucci
*/
import java.util.Random;

public class Dice {

    private int value;
    private Color color;
    
    /**class constructor
     */
    public Dice(){
        throwDice();
        color = null;
    }
    
    /** throw the dice
     * @return an integer between 1 and 6
     * @throws NumberNotValidException: when the number is not in the interval 1-6
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

    public void setValue(int value) {
        this.value = value;
    }
    
    public void setColorDice(Color colour){
        this.color = colour;
    }
}
