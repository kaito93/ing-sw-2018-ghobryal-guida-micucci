package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrindingStone;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize Grinding Stone strategy card
 * extends ToolBuilder
 */
public class GrindingStoneBuilder extends ToolBuilder {
    private ToolCardStrategy gs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrindingStoneBuilder(){
        super();
        gs = new GrindingStone();
        this.setStrategy(gs);
        this.setToBeCompared("GrindingStone");
    }
}
