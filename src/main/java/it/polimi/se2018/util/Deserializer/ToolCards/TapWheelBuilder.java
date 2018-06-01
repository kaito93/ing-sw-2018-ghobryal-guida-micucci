package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.TapWheel;

public class TapWheelBuilder extends ToolBuilder {
    private TapWheel tw;

    public TapWheelBuilder(){
        super();
        this.setStrategy(tw);
        this.setToBeCompared("TapWheel");
    }
}
