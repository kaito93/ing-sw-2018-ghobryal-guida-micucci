package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ColorVarietyStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the color variety strategy cards
 * extends Builder
 */
public class ColorVarietyStrategyBuilder extends Builder {

    private ObjectiveCardStrategy cvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColorVarietyStrategyBuilder(){
        super();
        cvs = new ColorVarietyStrategy();
        this.setStrategy(cvs);
        this.setToBeCompared("ColorVarietyStrategy");
    }
}
