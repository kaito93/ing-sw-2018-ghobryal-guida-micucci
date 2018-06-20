package it.polimi.se2018.util.deserializer.public_cards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnShadeVarietyStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserialize column shades strategy card
 * extends Builder
 */
public class ColumnShadeVarietyStrategyBuilder extends Builder{
    ObjectiveCardStrategy csvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColumnShadeVarietyStrategyBuilder(){
        super();
        csvs = new ColumnShadeVarietyStrategy();
        this.setStrategy(csvs);
        this.setToBeCompared("ColumnShadeVarietyStrategy");
    }
}
