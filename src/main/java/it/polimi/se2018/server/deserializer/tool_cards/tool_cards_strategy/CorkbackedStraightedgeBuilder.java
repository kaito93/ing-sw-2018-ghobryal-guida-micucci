package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.CorkbackedStraightedge;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer the cork backed Straighedge card
 * extends ToolBuilder
 * @author Andrea Micucci
 */
public class CorkbackedStraightedgeBuilder extends ToolBuilder{

    /**
     * class constructor that inizialize the string and the strategy
     */
    public CorkbackedStraightedgeBuilder(){
        super();
        ToolCardStrategy cbs = new CorkbackedStraightedge();
        this.setStrategy(cbs);
        this.setToBeCompared("CorkbackedStraightedge");
    }
}
