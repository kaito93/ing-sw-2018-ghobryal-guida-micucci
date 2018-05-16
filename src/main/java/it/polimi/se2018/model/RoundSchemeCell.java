package it.polimi.se2018.model;

import java.util.ArrayList;

/** class RoundschemeCell
 *  contains all the information about the dices stored because they are not used during the match
 *  @author Andrea Micucci
 */

public class RoundSchemeCell {
    private ArrayList<Dice> restOfStock;

    /** class constructor
     * return an object formed by an arraylist of dice
     */
    public RoundSchemeCell(){
        restOfStock = new ArrayList<Dice>();
    }
    
    /** method that return the dices stored in one cell
     * @return an arraylist of dices not used during the turn
     */
    public ArrayList<Dice> getRestOfStock(){
        return restOfStock;
    }
    
    /** method that add an arraylist of dice to a single cell
     * @param toAdd the arraylist contains the dices not used during the turn
     */
    public void setDice(ArrayList<Dice> toAdd){
        restOfStock.addAll(toAdd);
    }
    
    /** method that search a dice of which is known the color and the value
     * @param value integer of the value of the dice the player is searching
     * @param color color of the dice the player is searching
     * @return a dice Object if the dice exists in the arraylist, else null
     */
    public Dice getDice(int value, Color color){
        for (Dice aRestOfStock : restOfStock) {
            if (!((aRestOfStock.getColor() != color) || (aRestOfStock.getValue() > value)))
                return aRestOfStock;
        }
    return null;
    }
}

