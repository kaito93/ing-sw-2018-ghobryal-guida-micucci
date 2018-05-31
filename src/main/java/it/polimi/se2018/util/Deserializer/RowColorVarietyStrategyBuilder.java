package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.RowColorVarietyStrategy;

public class RowColorVarietyStrategyBuilder extends Builder{
    RowColorVarietyStrategy rcvs;

    public RowColorVarietyStrategyBuilder(){
        super();
        this.setStrategy(rcvs);
        this.setToBeCompared("RowColorVarietyStrategy");
    }
}
