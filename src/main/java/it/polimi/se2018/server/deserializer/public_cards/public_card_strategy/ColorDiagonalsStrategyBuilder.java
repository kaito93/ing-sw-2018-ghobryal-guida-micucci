package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.controller.public_objective_card_strategy.ColorDiagonalsStrategy;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserializer the color diagonals strategy
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
