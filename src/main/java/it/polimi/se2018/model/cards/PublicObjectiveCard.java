package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

import java.util.List;

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
    @Override
    public int getScore() {
        return score;
    }

    @Override
    public ToolCardStrategy getStrategy() {
        return null;
    }

    @Override
    public boolean isUsed() {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock, int row2, int column2,
                           Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap, List<Player> turns, int posDice1) {
        return false;
    }

    /**
     * searches on the player's map where and how many times he achieved the card's target
     * @param map player's map
     * @return the score that the player achieved out of this card
     */
    @Override
    public int search(Map map){
        return strategy.search(map, score);
    }
}
