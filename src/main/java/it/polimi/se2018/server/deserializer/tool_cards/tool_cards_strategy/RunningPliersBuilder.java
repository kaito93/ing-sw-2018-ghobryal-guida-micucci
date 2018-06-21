package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.RunningPliers;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer Running pliers strategy card
 * extends ToolBuilder
 */
public class RunningPliersBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RunningPliersBuilder(){
        super();
        ToolCardStrategy rp = new RunningPliers();
        this.setStrategy(rp);
        this.setToBeCompared("RunningPliers");
    }
}
