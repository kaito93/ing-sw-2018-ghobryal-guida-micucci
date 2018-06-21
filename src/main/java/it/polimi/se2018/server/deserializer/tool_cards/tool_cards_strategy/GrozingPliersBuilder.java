package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.GrozingPliers;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Grozing pliers strategy card
 * extends ToolBuilder
 */
public class GrozingPliersBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrozingPliersBuilder(){
        super();
        ToolCardStrategy gp = new GrozingPliers();
        this.setStrategy(gp);
        this.setToBeCompared("GrozingPliers");
    }
}
