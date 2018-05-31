package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnShadeVarietyStrategy;

public class ColumnShadeVarietyStrategyBuilder extends Builder{
    ColumnShadeVarietyStrategy csvs;

    public ColumnShadeVarietyStrategyBuilder(){
        super();
        this.setStrategy(csvs);
        this.setToBeCompared("ColumnShadeVarietyStrategy");
    }
}
