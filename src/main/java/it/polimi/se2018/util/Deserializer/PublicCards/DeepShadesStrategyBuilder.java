package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.DeepShadesStrategy;

/**
 * class to deserialize the deep shades strategy card
 * extends Builder
 */
public class DeepShadesStrategyBuilder extends Builder{
    DeepShadesStrategy dss;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public DeepShadesStrategyBuilder(){
        super();
        this.setStrategy(dss);
        this.setToBeCompared("DeepShadesStrategy");
    }
}