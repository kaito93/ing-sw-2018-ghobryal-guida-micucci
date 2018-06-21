package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.DeepShadesStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the deep shades strategy card
 * extends Builder
 */
public class DeepShadesStrategyBuilder extends Builder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public DeepShadesStrategyBuilder(){
        super();
        ObjectiveCardStrategy dss = new DeepShadesStrategy();
        this.setStrategy(dss);
        this.setToBeCompared("DeepShadesStrategy");
    }
}