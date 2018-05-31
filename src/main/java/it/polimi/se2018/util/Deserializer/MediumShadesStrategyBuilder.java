package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.MediumShadesStrategy;

public class MediumShadesStrategyBuilder extends Builder{
    MediumShadesStrategy mss;

    public MediumShadesStrategyBuilder(){
        super();
        this.setStrategy(mss);
        this.setToBeCompared("MediumShadesStrategy");
    }
}
