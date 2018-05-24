package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

public class ToolCard extends Card{
    private int id;
    private Color color;
    private boolean used;
    private ToolCardStrategy strategy;

    /**
     * class constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param id1 card's number
     * @param color1 color associated to the card
     */

    public ToolCard(String title, String description, int id1, Color color1, ToolCardStrategy strategy1){
        super(title, description);
        id = id1;
        color = color1;
        used = false;
        strategy = strategy1;
    }

    /**
     * @return if the card was used till now or not
     */

    public boolean isUsed(){
        return used;
    }

    /**
     * @return card's id
     */

    public int getId() {
        return id;
    }

    /**
     * @return card's associated color
     */

    public Color getColor() {
        return color;
    }

    /**
     * sets the boolean if the card is being used
     * @param used1 pass true if it's used
     */

    public void setUsed(boolean used1) {
        used = used1;
    }

}
