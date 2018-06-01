package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.FluxRemover;

public class FluxRemoverBuilder extends ToolBuilder {
    private FluxRemover fr;

    public FluxRemoverBuilder(){
        super();
        this.setToBeCompared("FluxRemover");
        this.setStrategy(fr);
    }
}
