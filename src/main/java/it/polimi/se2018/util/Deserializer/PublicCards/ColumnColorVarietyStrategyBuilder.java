package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnColorVarietyStrategy;

/**
 * class to deserialize column color variety strategy card
 * extends Builder
 */
public class ColumnColorVarietyStrategyBuilder extends Builder{

    ColumnColorVarietyStrategy ccvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColumnColorVarietyStrategyBuilder(){
        super();
        ccvs = new ColumnColorVarietyStrategy();
        this.setStrategy(ccvs);
        this.setToBeCompared("ColumnColorVarietyStrategy");
    }
}
