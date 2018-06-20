package it.polimi.se2018.util.deserializer.tool_cards;

import it.polimi.se2018.model.cards.tool_card_strategy.RunningPliers;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize Running pliers strategy card
 * extends ToolBuilder
 */
public class RunningPliersBuilder extends ToolBuilder {
    private ToolCardStrategy rp;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RunningPliersBuilder(){
        super();
        rp = new RunningPliers();
        this.setStrategy(rp);
        this.setToBeCompared("RunningPliers");
    }
}
