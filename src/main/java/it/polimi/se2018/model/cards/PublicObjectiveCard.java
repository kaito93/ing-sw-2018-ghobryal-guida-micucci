package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * a generic public objective card
 * @author Anton Ghobryal
 */

public class PublicObjectiveCard extends Card {
    private int score;
    private ObjectiveCardStrategy strategy;

    /**
     * Class Constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param score1 the score the player achieves out of this card
     * @param strategy1 which card it is of all public objective cards
     */
    public PublicObjectiveCard(String title, String description, int score1, ObjectiveCardStrategy strategy1){
        super(title, description);
        score = score1;
        strategy = strategy1;
    }

    /**
     * @return the achieved score of a public card
     */
    public int getScore() {
        return score;
    }

    /**
     * searches on the player's map where and how many times he achieved the card's target
     * @param map player's map
     * @return the score that the player achieved out of this card
     */

    public int search(Map map){
        return strategy.search(map, score);
    }
}
