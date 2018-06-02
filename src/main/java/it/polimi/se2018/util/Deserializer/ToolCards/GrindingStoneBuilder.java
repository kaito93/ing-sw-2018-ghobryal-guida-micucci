package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrindingStone;

/**
 * class to deserialize Grinding Stone strategy card
 * extends ToolBuilder
 */
public class GrindingStoneBuilder extends ToolBuilder {
    private GrindingStone gs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrindingStoneBuilder(){
        super();
        this.setStrategy(gs);
        this.setToBeCompared("GrindingStone");
    }
}
