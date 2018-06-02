package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.RowShadeVarietyStrategy;
import it.polimi.se2018.util.Deserializer.PublicCards.Builder;

/**
 * class that is used to deserialize the Row Shade variety strategy
 * extends Builder
 */
public class RowShadeVarietyStrategyBuilder extends Builder {
    RowShadeVarietyStrategy rsvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RowShadeVarietyStrategyBuilder(){
        super();
        this.setStrategy(rsvs);
        this.setToBeCompared("RowShadeVarietyStrategy");
    }
}
