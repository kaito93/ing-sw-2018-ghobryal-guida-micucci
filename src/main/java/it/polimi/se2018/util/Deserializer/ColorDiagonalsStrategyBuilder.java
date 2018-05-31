package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColorDiagonalsStrategy;

public class ColorDiagonalsStrategyBuilder extends Builder{

    ColorDiagonalsStrategy cds;
    public ColorDiagonalsStrategyBuilder(){
        super();
        this.setStrategy(cds);
        this.setToBeCompared("ColorDiagonalsStrategy");
    }
}
