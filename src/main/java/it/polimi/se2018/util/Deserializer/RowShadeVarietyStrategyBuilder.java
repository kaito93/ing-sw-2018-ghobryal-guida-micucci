package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.RowShadeVarietyStrategy;

public class RowShadeVarietyStrategyBuilder extends Builder{
    RowShadeVarietyStrategy rsvs;

    public RowShadeVarietyStrategyBuilder(){
        super();
        this.setStrategy(rsvs);
        this.setToBeCompared("RowShadeVarietyStrategy");
    }
}
