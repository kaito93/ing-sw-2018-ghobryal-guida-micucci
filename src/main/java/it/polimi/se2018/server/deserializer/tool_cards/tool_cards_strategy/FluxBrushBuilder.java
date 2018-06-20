package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.FluxBrush;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer the flux brush strategy card
 * extends ToolBuilder
 */
public class FluxBrushBuilder extends ToolBuilder {
    private ToolCardStrategy fb;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public FluxBrushBuilder(){
        super();
        fb = new FluxBrush();
        this.setStrategy(fb);
        this.setToBeCompared("FluxBrush");
    }
}
