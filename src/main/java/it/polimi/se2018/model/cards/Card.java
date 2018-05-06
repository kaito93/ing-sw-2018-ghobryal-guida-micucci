package it.polimi.se2018.model.cards;

public class Card {
    private String title;
    private String description;

    public Card(String titleT, String descriptionD){
        title=titleT;
        description=descriptionD;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
