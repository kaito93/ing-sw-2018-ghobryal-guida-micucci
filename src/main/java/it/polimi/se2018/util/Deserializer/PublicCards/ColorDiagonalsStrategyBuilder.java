package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColorDiagonalsStrategy;

/**
 * class to deserialize the color diagonals strategy
 * extends Builder
 */
public class ColorDiagonalsStrategyBuilder extends Builder{
    ColorDiagonalsStrategy cds;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColorDiagonalsStrategyBuilder(){
        super();
        this.setStrategy(cds);
        this.setToBeCompared("ColorDiagonalsStrategy");
    }
}
