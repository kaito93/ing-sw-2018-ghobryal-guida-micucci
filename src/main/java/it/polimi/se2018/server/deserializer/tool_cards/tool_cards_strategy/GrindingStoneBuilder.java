package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.GrindingStone;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Grinding Stone strategy card
 * extends ToolBuilder
 * @author Andrea Micucci
 */
public class GrindingStoneBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrindingStoneBuilder(){
        super();
        ToolCardStrategy gs = new GrindingStone();
        this.setStrategy(gs);
        this.setToBeCompared("GrindingStone");
    }
}
