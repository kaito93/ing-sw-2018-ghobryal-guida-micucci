package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.RunningPliers;

public class RunningPliersBuilder extends ToolBuilder {
    private RunningPliers rp;

    public RunningPliersBuilder(){
        super();
        this.setStrategy(rp);
        this.setToBeCompared("RunningPliers");
    }
}
