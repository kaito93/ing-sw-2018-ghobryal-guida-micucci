package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.TapWheel;

/**
 * class to deserialize tap wheel strategy card
 * extends ToolBuilder
 */
public class TapWheelBuilder extends ToolBuilder {
    private TapWheel tw;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public TapWheelBuilder(){
        super();
        this.setStrategy(tw);
        this.setToBeCompared("TapWheel");
    }
}