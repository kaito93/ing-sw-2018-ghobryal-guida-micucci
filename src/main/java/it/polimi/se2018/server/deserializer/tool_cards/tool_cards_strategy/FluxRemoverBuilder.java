package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.controller.tool_card_strategy.FluxRemover;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserializer the flux remover strategy card
 * extends ToolBuilder
 */
public class FluxRemoverBuilder extends ToolBuilder {

    /**
     * class constructor that inizialize the string and the strategy
     */
    public FluxRemoverBuilder(){
        super();
        ToolCardStrategy fr = new FluxRemover();
        this.setToBeCompared("FluxRemover");
        this.setStrategy(fr);
    }
}
