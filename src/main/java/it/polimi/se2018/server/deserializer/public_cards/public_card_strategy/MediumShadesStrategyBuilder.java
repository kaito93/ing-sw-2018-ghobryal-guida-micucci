package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.MediumShadesStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the medium shade strategy card
 * extends Builder
 */
public class MediumShadesStrategyBuilder extends Builder{
    ObjectiveCardStrategy mss;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public MediumShadesStrategyBuilder(){
        super();
        mss = new MediumShadesStrategy();
        this.setStrategy(mss);
        this.setToBeCompared("MediumShadesStrategy");
    }
}
