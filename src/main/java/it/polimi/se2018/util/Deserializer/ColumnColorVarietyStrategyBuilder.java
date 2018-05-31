package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnColorVarietyStrategy;

public class ColumnColorVarietyStrategyBuilder extends Builder{

    ColumnColorVarietyStrategy ccvs;

    public ColumnColorVarietyStrategyBuilder(){
        super();
        this.setStrategy(ccvs);
        this.setToBeCompared("ColumnColorVarietyStrategy");
    }
}
