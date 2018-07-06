package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ColorVarietyStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the color variety strategy cards
 * extends Builder
 * @author Andrea Micucci
 */
public class ColorVarietyStrategyBuilder extends Builder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColorVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy cvs = new ColorVarietyStrategy();
        this.setStrategy(cvs);
        this.setToBeCompared("ColorVarietyStrategy");
    }
}
