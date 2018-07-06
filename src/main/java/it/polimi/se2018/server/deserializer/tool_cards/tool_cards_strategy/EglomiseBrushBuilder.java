package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.EglomiseBrush;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Eglomise brush strategy card
 * extends ToolBuilder
 * @author Andrea Micucci
 */
public class EglomiseBrushBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public EglomiseBrushBuilder(){
        super();
        ToolCardStrategy eb = new EglomiseBrush();
        this.setToBeCompared("EglomiseBrush");
        this.setStrategy(eb);
    }
}
