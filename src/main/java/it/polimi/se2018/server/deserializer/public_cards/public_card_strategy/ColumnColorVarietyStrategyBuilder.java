package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ColumnColorVarietyStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer column color variety strategy card
 * extends Builder
 */
public class ColumnColorVarietyStrategyBuilder extends Builder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColumnColorVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy ccvs = new ColumnColorVarietyStrategy();
        this.setStrategy(ccvs);
        this.setToBeCompared("ColumnColorVarietyStrategy");
    }
}
