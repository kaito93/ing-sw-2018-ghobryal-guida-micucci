package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ShadeVarietyStrategy;

public class ShadeVarietyStrategyBuilder extends Builder{
    ShadeVarietyStrategy svs;

    public ShadeVarietyStrategyBuilder(){
        super();
        this.setStrategy(svs);
        this.setToBeCompared("ShadeVarietyStrategy");
    }
}
