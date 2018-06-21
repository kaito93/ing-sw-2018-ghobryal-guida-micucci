package it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy;

import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.server.deserializer.JsonTransition;
import it.polimi.se2018.server.deserializer.tool_cards.ToolCardTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * class to be used to deserializer all tool cards
 * implements Observer Interface of java lang library
 */
public class ToolBuilder implements Observer {
    private String toBeCompared;
    private ToolCardTransfer jT;
    private ArrayList<Card> toolCardStrategic;
    private ToolCardStrategy strategy;

    /**
     * class constructor: create an arraylist and a json transiction data structure
     */
    public ToolBuilder() {
        jT = new ToolCardTransfer();
        toolCardStrategic = new ArrayList<>();
    }

    /**
     * method that create the card object from a compare between to strings
     */
    @Override
    public void update(Observable o, Object arg) {
        jT = (ToolCardTransfer) arg;
        if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
            toolCardStrategic.add(new ToolCard(jT.getTitle(), jT.getDescription(), jT.getId(), jT.getColor(), strategy));
        }

    }

    /**
     * setter method to set the tool card transiction data structure
     *
     * @param jT to be setted
     */
    public void setjT(ToolCardTransfer jT) {
        this.jT = jT;
    }

    /**
     * getter method to get the json transiction object
     *
     * @return json transiction object
     */
    public JsonTransition getjT() {
        return jT;
    }

    /**
     * method to set the string that has to be compared (used in the constructor of derived class)
     *
     * @param toBeCompared string that has to be setted
     */
    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    /**
     * setter method to set the strategy
     *
     * @param strategy to be setted
     */
    public void setStrategy(ToolCardStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * method to create the deck of all the created cards
     *
     * @param deck that has to be created
     */
    public void setDeck(List deck) {
        int i;
        for (i = 0; i < toolCardStrategic.size(); i++) {

            deck.add(toolCardStrategic.get(i));

        }
    }
}
