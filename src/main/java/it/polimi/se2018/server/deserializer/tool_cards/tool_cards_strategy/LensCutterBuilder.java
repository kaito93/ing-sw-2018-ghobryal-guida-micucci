package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.LensCutter;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Lens cutter strategy card
 * extends ToolCard
 */
public class LensCutterBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LensCutterBuilder(){
        super();
        ToolCardStrategy lc = new LensCutter();
        this.setStrategy(lc);
        this.setToBeCompared("LensCutter");
    }
}
