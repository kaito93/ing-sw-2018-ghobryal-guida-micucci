package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GlazingHammer;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize Glazing Hammer strategy card
 * extends ToolBuilder
 */
public class GlazingHammerBuilder extends ToolBuilder{
    private ToolCardStrategy gh;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GlazingHammerBuilder(){
        super();
        gh = new GlazingHammer();
        this.setStrategy(gh);
        this.setToBeCompared("GlazingHammer");
    }
}
