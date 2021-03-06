package it.polimi.se2018.server.deserializer.tool_cards;

import it.polimi.se2018.server.deserializer.JsonTransition;
import it.polimi.se2018.shared.model_shared.Color;

/**
 * class to make the data structure to deserializer the toolcards
 * derives from jsontransiction
 * @author Andrea Micucci
 */
public class ToolCardTransfer extends JsonTransition {

    private String strategy=null;
    private String color=null;
    private String id=null;

    /**
     * getter method to obtain the strategy
     * @return a string that represent the strategy
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * getter method to get the id of the cards
     * @return an integer that is the id of the card
     */
    public int getId() {
        return Integer.decode(id);
    }

    /**
     * getter method for the color associated to the tool card
     * @return the color of the cards
     */
    public Color getColor() {
        return Color.parseInput(color);
    }



}
