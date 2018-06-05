package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.util.Logger;

import java.io.Serializable;

/**
 * a generic objective card implementation
 * @author Anton Ghobryal
 */

public class ObjectiveCardStrategy implements Serializable {

    protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * implements the card's description
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    public int search(Map map, int score){
        return 0;
    }

}
