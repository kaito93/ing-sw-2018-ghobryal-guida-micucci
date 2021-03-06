package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ColumnShadeVarietyStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer column shades strategy card
 * extends Builder
 * @author Andrea Micucci
 */
public class ColumnShadeVarietyStrategyBuilder extends Builder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColumnShadeVarietyStrategyBuilder(){
        super();
        ObjectiveCardStrategy csvs = new ColumnShadeVarietyStrategy();
        this.setStrategy(csvs);
        this.setToBeCompared("ColumnShadeVarietyStrategy");
    }
}
