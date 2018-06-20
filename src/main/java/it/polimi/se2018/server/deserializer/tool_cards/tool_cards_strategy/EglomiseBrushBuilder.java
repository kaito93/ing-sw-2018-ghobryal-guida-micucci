package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.EglomiseBrush;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Eglomise brush strategy card
 * extends ToolBuilder
 */
public class EglomiseBrushBuilder extends ToolBuilder {
    private ToolCardStrategy eb;

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
