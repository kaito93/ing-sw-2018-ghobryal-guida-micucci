package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.RowColorVarietyStrategy;
import it.polimi.se2018.util.Deserializer.PublicCards.Builder;

/**
 * class to deserialize the row color variety strategy
 * extends Builder
 */
public class RowColorVarietyStrategyBuilder extends Builder {
    ObjectiveCardStrategy rcvs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public RowColorVarietyStrategyBuilder(){
        super();
        rcvs = new RowColorVarietyStrategy();
        this.setStrategy(rcvs);
        this.setToBeCompared("RowColorVarietyStrategy");
    }
}
