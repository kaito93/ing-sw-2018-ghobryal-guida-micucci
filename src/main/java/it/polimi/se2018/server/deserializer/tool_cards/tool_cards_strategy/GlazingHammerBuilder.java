package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.GlazingHammer;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Glazing Hammer strategy card
 * extends ToolBuilder
 * @author Andrea Micucci
 */
public class GlazingHammerBuilder extends ToolBuilder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GlazingHammerBuilder(){
        super();
        ToolCardStrategy gh = new GlazingHammer();
        this.setStrategy(gh);
        this.setToBeCompared("GlazingHammer");
    }
}
