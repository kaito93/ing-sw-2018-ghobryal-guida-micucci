package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.RowShadeVarietyStrategy;

/**
 * class that is used to deserializer the Row Shade variety strategy
 * extends Builder
 * @author Andrea Micucci
 */
public class RowShadeVarietyStrategyBuilder extends Builder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RowShadeVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy rsvs = new RowShadeVarietyStrategy();
        this.setStrategy(rsvs);
        this.setToBeCompared("RowShadeVarietyStrategy");
    }
}
