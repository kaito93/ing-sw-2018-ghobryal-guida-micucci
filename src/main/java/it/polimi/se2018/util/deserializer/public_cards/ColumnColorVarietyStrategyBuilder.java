package it.polimi.se2018.util.deserializer.public_cards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnColorVarietyStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserialize column color variety strategy card
 * extends Builder
 */
public class ColumnColorVarietyStrategyBuilder extends Builder{

    ObjectiveCardStrategy ccvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColumnColorVarietyStrategyBuilder(){
        super();
        ccvs = new ColumnColorVarietyStrategy();
        this.setStrategy(ccvs);
        this.setToBeCompared("ColumnColorVarietyStrategy");
    }
}
