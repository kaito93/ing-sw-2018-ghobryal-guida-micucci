package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GlazingHammer;

/**
 * class to deserialize Glazing Hammer strategy card
 * extends ToolBuilder
 */
public class GlazingHammerBuilder extends ToolBuilder{
    private GlazingHammer gh;

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
