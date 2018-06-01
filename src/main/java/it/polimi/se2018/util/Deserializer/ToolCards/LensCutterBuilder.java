package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.LensCutter;

public class LensCutterBuilder extends ToolBuilder {
    private LensCutter lc;

    public LensCutterBuilder(){
        super();
        this.setStrategy(lc);
        this.setToBeCompared("LensCutter");
    }
}
