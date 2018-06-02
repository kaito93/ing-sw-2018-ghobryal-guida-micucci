package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.LightShadesStrategy;

/**
 * class to deserialize the light shades strategy
 * extends Builder
 */
public class LightShadesStrategyBuilder extends Builder{
    LightShadesStrategy lss;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LightShadesStrategyBuilder(){
        super();
        this.setStrategy(lss);
        this.setToBeCompared("LightShadesStrategy");
    }
}