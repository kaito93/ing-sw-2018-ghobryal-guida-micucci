package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.LensCutter;

/**
 * class to deserialize Lens cutter strategy card
 * extends ToolCard
 */
public class LensCutterBuilder extends ToolBuilder {
    private LensCutter lc;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LensCutterBuilder(){
        super();
        lc = new LensCutter();
        this.setStrategy(lc);
        this.setToBeCompared("LensCutter");
    }
}
