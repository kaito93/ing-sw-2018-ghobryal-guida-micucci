package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.EglomiseBrush;

/**
 * class to deserialize Eglomise brush strategy card
 * extends ToolBuilder
 */
public class EglomiseBrushBuilder extends ToolBuilder {
    private EglomiseBrush eb;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public EglomiseBrushBuilder(){
        super();
        eb = new EglomiseBrush();
        this.setToBeCompared("EglomiseBrush");
        this.setStrategy(eb);
    }
}
