package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.Lathekin;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Lathekin strategy card
 * extends ToolBuilder
 */
public class LathekinBuilder extends ToolBuilder {
    private ToolCardStrategy lath;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LathekinBuilder(){
        super();
        lath = new Lathekin();
        this.setStrategy(lath);
        this.setToBeCompared("Lathekin");
    }
}
