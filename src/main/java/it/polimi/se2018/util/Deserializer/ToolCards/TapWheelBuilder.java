package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.TapWheel;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize tap wheel strategy card
 * extends ToolBuilder
 */
public class TapWheelBuilder extends ToolBuilder {
    private ToolCardStrategy tw;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public TapWheelBuilder(){
        super();
        tw = new TapWheel();
        this.setStrategy(tw);
        this.setToBeCompared("TapWheel");
    }
}
