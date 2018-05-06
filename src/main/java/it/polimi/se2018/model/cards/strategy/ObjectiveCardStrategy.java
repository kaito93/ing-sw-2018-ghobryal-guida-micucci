package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Map;

public abstract class ObjectiveCardStrategy {
    public abstract int search(Map map, int score);
}
