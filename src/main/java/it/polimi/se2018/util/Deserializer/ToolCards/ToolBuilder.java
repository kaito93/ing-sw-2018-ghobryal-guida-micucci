package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.util.jsonTransiction;
import it.polimi.se2018.util.toolCardTransfer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * class to be used to deserialize all tool cards
 * implements Observer Interface of java lang library
 */
public class ToolBuilder implements Observer {
    private String toBeCompared;
    private toolCardTransfer jT = null;
    private ArrayList<ToolCard> toolCardStrategic;
    private ToolCardStrategy strategy;

    /**
     * class constructor: create an arraylist and a json transiction data structure
     */
    public ToolBuilder(){
        jT = new toolCardTransfer();
        toolCardStrategic = new ArrayList<>();
    }

    @Override
    /**
     * method that create the card object from a compare between to strings
     */
    public void update(Observable o, Object arg) {
        jT = (toolCardTransfer) arg;
        if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
            toolCardStrategic.add(new ToolCard(jT.getTitle(), jT.getDescription(), jT.getId(), (Color) jT.getColor(), strategy));
        }
        else
            System.out.println("sono fuori dall'IF");
    }

    /**
     * setter method to set the tool card transiction data structure
     * @param jT to be setted
     */
    public void setjT(toolCardTransfer jT) {
        this.jT = jT;
    }

    /**
     * getter method to get the json transiction object
     * @return json transiction object
     */
    public jsonTransiction getjT() {
        return jT;
    }

    /**
     * method to set the string that has to be compared (used in the constructor of derived class)
     * @param toBeCompared string that has to be setted
     */
    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    /**
     * setter method to set the strategy
     * @param strategy to be setted
     */
    public void setStrategy(ToolCardStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * method to create the deck of all the created cards
     * @param deck that has to be created
     */
    public void setDeck(ArrayList deck){
        int i;
        for(i=0; i<=toolCardStrategic.size(); i++){
            if (toolCardStrategic == null)
                break;
            else
                deck.add(toolCardStrategic.get(i));
        }
    }
}
