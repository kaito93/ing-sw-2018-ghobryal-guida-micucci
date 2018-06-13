package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.util.Logger;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * a generic objective card implementation
 * @author Anton Ghobryal
 */

public abstract class ObjectiveCardStrategy implements Serializable {

    protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * implements the card's description
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    public abstract int search(Map map, int score);

    //coordinate1=i, coordinate2=j, coordinate3=k
    //se false break
    protected boolean isColorDuplicated(Map map, int coordinate1, int coordinate2, int coordinate3){
        if ((!map.isEmptyCell(coordinate2, coordinate1)) && (!map.isEmptyCell(coordinate3, coordinate1))) {    //controls if there is a dice or not
            try {
                if (map.getCell(coordinate2, coordinate1).getDice().getColor().equals(map.getCell(coordinate3, coordinate1).getDice().getColor())) {    //controls if there is two consecutive dices with the same color
                    return true;
                } else {
                    return false;
                }
            } catch (notValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString() + "\nisColorDuplicated method in abstract class ColumnColorVarietyStrategy", e);
            }
        }
        return true;
    }
}
