package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.Lathekin;

/**
 * class to deserialize Lathekin strategy card
 * extends ToolBuilder
 */
public class LathekinBuilder extends ToolBuilder {
    private Lathekin lath;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LathekinBuilder(){
        super();
        this.setStrategy(lath);
        this.setToBeCompared("Lathekin");
    }
}
