package it.polimi.se2018.util.deserializer.public_cards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.RowShadeVarietyStrategy;

/**
 * class that is used to deserialize the Row Shade variety strategy
 * extends Builder
 */
public class RowShadeVarietyStrategyBuilder extends Builder {
    ObjectiveCardStrategy rsvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RowShadeVarietyStrategyBuilder(){
        super();
        rsvs = new RowShadeVarietyStrategy();
        this.setStrategy(rsvs);
        this.setToBeCompared("RowShadeVarietyStrategy");
    }
}
