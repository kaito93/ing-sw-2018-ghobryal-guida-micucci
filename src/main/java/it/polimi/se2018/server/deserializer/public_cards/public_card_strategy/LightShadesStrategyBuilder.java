package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.LightShadesStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the light shades strategy
 * extends Builder
 * @author Andrea Micucci
 */
public class LightShadesStrategyBuilder extends Builder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LightShadesStrategyBuilder(){
        super();
        ObjectiveCardStrategy lss = new LightShadesStrategy();
        this.setStrategy(lss);
        this.setToBeCompared("LightShadesStrategy");
    }
}
