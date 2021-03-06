package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ShadeVarietyStrategy;

/**
 * class to deserializer the shade variety strategy cards
 * extends Builder
 * @author Andrea Micucci
 */
public class ShadeVarietyStrategyBuilder extends Builder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ShadeVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy svs = new ShadeVarietyStrategy();
        this.setStrategy(svs);
        this.setToBeCompared("ShadeVarietyStrategy");
    }
}
