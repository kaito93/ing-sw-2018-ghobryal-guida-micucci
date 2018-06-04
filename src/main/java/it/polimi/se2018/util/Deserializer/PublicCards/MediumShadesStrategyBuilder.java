package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.MediumShadesStrategy;

/**
 * class to deserialize the medium shade strategy card
 * extends Builder
 */
public class MediumShadesStrategyBuilder extends Builder{
    MediumShadesStrategy mss;

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
