package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.RunningPliers;

/**
 * class to deserialize Running pliers strategy card
 * extends ToolBuilder
 */
public class RunningPliersBuilder extends ToolBuilder {
    private RunningPliers rp;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RunningPliersBuilder(){
        super();
        this.setStrategy(rp);
        this.setToBeCompared("RunningPliers");
    }
}
