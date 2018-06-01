package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.CopperFoilBurnisher;

public class CopperFoilBurnisherBuilder extends ToolBuilder {
    CopperFoilBurnisher cfb;

    public CopperFoilBurnisherBuilder(){
        super();
        this.setToBeCompared("CopperFoilBurnisher");
        this.setStrategy(cfb);
    }
}
