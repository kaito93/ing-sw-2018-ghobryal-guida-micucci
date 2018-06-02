package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.FluxRemover;

/**
 * class to deserialize the flux remover strategy card
 * extends ToolBuilder
 */
public class FluxRemoverBuilder extends ToolBuilder {
    private FluxRemover fr;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public FluxRemoverBuilder(){
        super();
        this.setToBeCompared("FluxRemover");
        this.setStrategy(fr);
    }
}
