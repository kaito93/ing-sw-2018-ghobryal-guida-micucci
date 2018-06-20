package it.polimi.se2018.util.deserializer.tool_cards;

import it.polimi.se2018.model.cards.tool_card_strategy.FluxBrush;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize the flux brush strategy card
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
