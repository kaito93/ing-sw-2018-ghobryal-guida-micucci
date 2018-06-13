package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ColorDiagonalsStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserialize the color diagonals strategy
 * extends Builder
 */
public class ColorDiagonalsStrategyBuilder extends Builder{
    ObjectiveCardStrategy cds;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ColorDiagonalsStrategyBuilder(){
        super();
        cds = new ColorDiagonalsStrategy();
        this.setStrategy(cds);
        this.setToBeCompared("ColorDiagonalsStrategy");
    }
}
