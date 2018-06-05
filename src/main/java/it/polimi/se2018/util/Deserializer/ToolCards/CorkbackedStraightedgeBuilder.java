package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.CorkbackedStraightedge;

/**
 * class to deserialize the cork backed Straighedge card
 * extends ToolBuilder
 */
public class CorkbackedStraightedgeBuilder extends ToolBuilder{
    private CorkbackedStraightedge cbs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public CorkbackedStraightedgeBuilder(){
        super();
        cbs = new CorkbackedStraightedge();
        this.setStrategy(cbs);
        this.setToBeCompared("CorkbackedStraightedge");
    }
}
