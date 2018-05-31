package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.DeepShadesStrategy;

public class DeepShadesStrategyBuilder extends Builder{
    DeepShadesStrategy dss;

    public DeepShadesStrategyBuilder(){
        super();
        this.setStrategy(dss);
        this.setToBeCompared("DeepShadesStrategy");
    }
}