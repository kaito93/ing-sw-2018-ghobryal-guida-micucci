package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrozingPliers;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize Grozing pliers strategy card
 * extends ToolBuilder
 */
public class GrozingPliersBuilder extends ToolBuilder {
    private ToolCardStrategy gp;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public GrozingPliersBuilder(){
        super();
        gp = new GrozingPliers();
        this.setStrategy(gp);
        this.setToBeCompared("GrozingPliers");
    }
}
