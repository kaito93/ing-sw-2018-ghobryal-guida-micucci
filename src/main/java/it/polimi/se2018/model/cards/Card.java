package it.polimi.se2018.model.cards;

/**
 * Class Card
 * describes a generic card
 * @author Anton Ghobryal
 */

public class Card {
    private String title;
    private String description;

    /**
     * Class Constructor
     * @param titleT the title of this card
     * @param descriptionD the description of the card rules
     */

    public Card(String titleT, String descriptionD){
        title=titleT;
        description=descriptionD;
    }

    /**
     * @return the title of the card
     */

    public String getTitle() {
        return title;
    }

    /**
     * @return the description of the card
     */

    public String getDescription() {
        return description;
    }
}
