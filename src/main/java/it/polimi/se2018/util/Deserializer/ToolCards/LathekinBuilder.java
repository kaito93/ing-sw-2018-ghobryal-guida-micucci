package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.tool_card_strategy.Lathekin;

public class LathekinBuilder extends ToolBuilder {
    private Lathekin lath;

    public LathekinBuilder(){
        super();
        this.setStrategy(lath);
        this.setToBeCompared("Lathekin");
    }
}
