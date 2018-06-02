package it.polimi.se2018.util;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

/**
 * class to make the data structure to deserialize the toolcards
 * derives from jsontransiction
 */
public class toolCardTransfer extends jsonTransiction {

    private String strategic;
    private Color color;
    private int id;

    /**
     * getter method to obtain the strategy
     * @return a string that represent the strategy
     */
    public String getStrategy() {
        return strategic;
    }

    /**
     * getter method to get the id of the cards
     * @return an integer that is the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * getter method for the color associated to the tool card
     * @return the color of the cards
     */
    public Color getColor() {
        return color;
    }

    /**
     * setter method for the color of the cards
     * @param color to be setted
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
