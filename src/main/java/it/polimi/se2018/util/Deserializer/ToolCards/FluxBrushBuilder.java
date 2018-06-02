package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.FluxBrush;

/**
 * class to deserialize the flux brush strategy card
 * extends ToolBuilder
 */
public class FluxBrushBuilder extends ToolBuilder {
    private FluxBrush fb;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public FluxBrushBuilder(){
        super();
        this.setStrategy(fb);
        this.setToBeCompared("FluxBrush");
    }
}
