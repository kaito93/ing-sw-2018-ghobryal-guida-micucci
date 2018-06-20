package it.polimi.se2018.shared.model_shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class Round Scheme Cell
 * contains all the information about the dices stored because they are not used during the match
 *
 * @author Andrea Micucci
 */

public class RoundSchemeCell implements Serializable {
    private ArrayList<Dice> restOfStock;

    /**
     * class constructor
     * return an object formed by an arraylist of dice
     */
    public RoundSchemeCell() {
        restOfStock = new ArrayList<>();
    }

    /**
     * method that return the dices stored in one cell
     *
     * @return an arraylist of dices not used during the turn
     */
    public List<Dice> getRestOfStock() {
        return restOfStock;
    }



    /**
     * @param value integer of the value of the dice the player is searching
     * @param color color of the dice the player is searching
     * @return a dice Object if the dice exists in the arraylist, else null
     * @deprecated method that search a dice of which is known the color and the value
     */
    @Deprecated
    public Dice getDice(int value, Color color) {
        for (Dice aRestOfStock : restOfStock) {
            if (aRestOfStock.getColor().equalsColor(color) && aRestOfStock.getValue() == value)
                return aRestOfStock;
        }
        return null;
    }

    /**
     * removes a chose dice from the round scheme cell
     *
     * @param dice the chosen dice
     */
    public void removeDice(Dice dice) {
        restOfStock.remove(dice);
    }

    /**
     * method that add an arraylist of dice to a single cell
     * @param toAdd the arraylist contains the dices not used during the turn
     */
    public void setDices(List<Dice> toAdd) {

        if(restOfStock.isEmpty()){
            restOfStock.addAll(toAdd);
        }
        else{
            if (restOfStock.get(0).getValue() == 0)
                restOfStock.addAll(0, toAdd);
            else
                restOfStock.addAll(toAdd);
        }

    }
}

