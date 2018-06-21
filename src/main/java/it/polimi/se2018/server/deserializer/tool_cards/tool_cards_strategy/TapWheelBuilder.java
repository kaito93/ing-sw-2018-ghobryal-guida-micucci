package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.TapWheel;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer tap wheel strategy card
 * extends ToolBuilder
 */
public class TapWheelBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public TapWheelBuilder(){
        super();
        ToolCardStrategy tw = new TapWheel();
        this.setStrategy(tw);
        this.setToBeCompared("TapWheel");
    }
}
