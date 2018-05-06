package it.polimi.se2018.Model.cards;

import it.polimi.se2018.Model.Map;
import it.polimi.se2018.Model.card.strategy.ObjectiveCardStrategy;

public class PublicObjectiveCard extends Card {
    private int score;
    private ObjectiveCardStrategy strategy;

    public PublicObjectiveCard(String title, String description, int score1, ObjectiveCardStrategy strategy1){
        super(title, description);
        score = score1;
        strategy = strategy1;
    }

    public int getScore() {
        return score;
    }

    public void search(Map map){
        strategy.search(map, score);
    }
}
