package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.CopperFoilBurnisher;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Copper Foil Burnished strategy card
 * extends Tool Builder
 */
public class CopperFoilBurnisherBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public CopperFoilBurnisherBuilder(){
        super();
        ToolCardStrategy cfb = new CopperFoilBurnisher();
        this.setToBeCompared("CopperFoilBurnisher");
        this.setStrategy(cfb);
    }
}
