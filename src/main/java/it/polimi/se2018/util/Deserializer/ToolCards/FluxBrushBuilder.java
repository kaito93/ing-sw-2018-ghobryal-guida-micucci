package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.FluxBrush;

public class FluxBrushBuilder extends ToolBuilder {
    private FluxBrush fb;

    public FluxBrushBuilder(){
        super();
        this.setStrategy(fb);
        this.setToBeCompared("FluxBrush");
    }
}
