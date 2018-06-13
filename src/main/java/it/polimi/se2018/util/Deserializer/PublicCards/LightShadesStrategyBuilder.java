package it.polimi.se2018.util.Deserializer.PublicCards;

import it.polimi.se2018.model.cards.public_objective_card_strategy.LightShadesStrategy;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;

/**
 * class to deserialize the light shades strategy
 * extends Builder
 */
public class LightShadesStrategyBuilder extends Builder{
    ObjectiveCardStrategy lss;

    /**
     * class constructor that inizialize the string and the strategy
     */
    public LightShadesStrategyBuilder(){
        super();
        lss = new LightShadesStrategy();
        this.setStrategy(lss);
        this.setToBeCompared("LightShadesStrategy");
    }
}
