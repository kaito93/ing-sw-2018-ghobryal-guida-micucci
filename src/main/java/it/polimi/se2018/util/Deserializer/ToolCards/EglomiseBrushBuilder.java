package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.EglomiseBrush;

public class EglomiseBrushBuilder extends ToolBuilder {
    private EglomiseBrush eb;

    public EglomiseBrushBuilder(){
        super();
        this.setToBeCompared("EglomiseBrush");
        this.setStrategy(eb);
    }
}
