package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.RowColorVarietyStrategy;

/**
 * class to deserializer the row color variety strategy
 * extends Builder
 */
public class RowColorVarietyStrategyBuilder extends Builder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RowColorVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy rcvs = new RowColorVarietyStrategy();
        this.setStrategy(rcvs);
        this.setToBeCompared("RowColorVarietyStrategy");
    }
}
