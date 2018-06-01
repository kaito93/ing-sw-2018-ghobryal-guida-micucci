package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrindingStone;

public class GrindingStoneBuilder extends ToolBuilder {
    private GrindingStone gs;

    public GrindingStoneBuilder(){
        super();
        this.setStrategy(gs);
        this.setToBeCompared("GrindingStone");
    }
}
