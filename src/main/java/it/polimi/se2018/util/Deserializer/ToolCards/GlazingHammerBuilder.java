package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GlazingHammer;

public class GlazingHammerBuilder extends ToolBuilder{
    private GlazingHammer gh;

    public GlazingHammerBuilder(){
        super();
        this.setStrategy(gh);
        this.setToBeCompared("GlazingHammer");
    }
}
