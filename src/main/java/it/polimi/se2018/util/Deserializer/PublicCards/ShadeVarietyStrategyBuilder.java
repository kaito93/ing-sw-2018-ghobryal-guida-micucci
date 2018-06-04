package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.ShadeVarietyStrategy;
import it.polimi.se2018.util.Deserializer.PublicCards.Builder;

/**
 * class to deserialize the shade variety strategy cards
 * extends Builder
 */
public class ShadeVarietyStrategyBuilder extends Builder {
    ShadeVarietyStrategy svs;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public ShadeVarietyStrategyBuilder(){
        super();
        svs = new ShadeVarietyStrategy();
        this.setStrategy(svs);
        this.setToBeCompared("ShadeVarietyStrategy");
    }
}
