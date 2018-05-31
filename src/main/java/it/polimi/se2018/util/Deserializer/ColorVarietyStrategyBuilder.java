package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColorVarietyStrategy;

public class ColorVarietyStrategyBuilder extends Builder {

    ColorVarietyStrategy cvs;

    ColorVarietyStrategyBuilder(){
        super();
        this.setStrategy(cvs);
        this.setToBeCompared("ColorVarietyStrategy");
    }
}
