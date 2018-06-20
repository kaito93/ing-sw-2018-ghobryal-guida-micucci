package it.polimi.se2018.util.deserializer.tool_cards;

import it.polimi.se2018.model.cards.tool_card_strategy.Lathekin;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to deserialize Lathekin strategy card
 * extends ToolBuilder
 */
public class LathekinBuilder extends ToolBuilder {
    private ToolCardStrategy lath;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LathekinBuilder(){
        super();
        lath = new Lathekin();
        this.setStrategy(lath);
        this.setToBeCompared("Lathekin");
    }
}
