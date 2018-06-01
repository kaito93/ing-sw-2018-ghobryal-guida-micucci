package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.GrozingPliers;

public class GrozingPliersBuilder extends ToolBuilder {
    private GrozingPliers gp;

    public GrozingPliersBuilder(){
        super();
        this.setStrategy(gp);
        this.setToBeCompared("GrozingPliers");
    }
}
