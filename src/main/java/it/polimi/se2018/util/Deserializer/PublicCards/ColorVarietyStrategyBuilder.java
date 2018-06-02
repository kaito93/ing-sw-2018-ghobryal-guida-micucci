package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColorVarietyStrategy;

/**
 * class to deserialize the color variety strategy cards
 * extends Builder
 */
public class ColorVarietyStrategyBuilder extends Builder {

    private ColorVarietyStrategy cvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColorVarietyStrategyBuilder(){
        super();
        this.setStrategy(cvs);
        this.setToBeCompared("ColorVarietyStrategy");
    }
}
