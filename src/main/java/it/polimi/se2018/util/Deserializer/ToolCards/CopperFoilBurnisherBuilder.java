package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.CopperFoilBurnisher;

/**
 * class to deserialize Copper Foil Burnished strategy card
 * extends Tool Builder
 */
public class CopperFoilBurnisherBuilder extends ToolBuilder {
    CopperFoilBurnisher cfb;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public CopperFoilBurnisherBuilder(){
        super();
        cfb = new CopperFoilBurnisher();
        this.setToBeCompared("CopperFoilBurnisher");
        this.setStrategy(cfb);
    }
}
