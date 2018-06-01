package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.CorkbackedStraightedge;

public class CorkbackedStraightedgeBuilder extends ToolBuilder{
    private CorkbackedStraightedge cbs;

    public CorkbackedStraightedgeBuilder(){
        super();
        this.setStrategy(cbs);
        this.setToBeCompared("CorkbackedStraightedge");
    }
}
