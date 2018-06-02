package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrozingPliers;

/**
 * class to deserialize Grozing pliers strategy card
 * extends ToolBuilder
 */
public class GrozingPliersBuilder extends ToolBuilder {
    private GrozingPliers gp;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrozingPliersBuilder(){
        super();
        this.setStrategy(gp);
        this.setToBeCompared("GrozingPliers");
    }
}
