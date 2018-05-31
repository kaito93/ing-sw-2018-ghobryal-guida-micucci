package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.LightShadesStrategy;

public class LightShadesStrategyBuilder extends Builder{
    LightShadesStrategy lss;

    public LightShadesStrategyBuilder(){
        super();
        this.setStrategy(lss);
        this.setToBeCompared("LightShadesStrategy");
    }
}
