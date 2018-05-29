package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;

/**
 * a generic objective card implementation
 * @author Anton Ghobryal
 */

public interface ObjectiveCardStrategy {

    /**
     * implements the card's description
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    public int search(Map map, int score);
}
