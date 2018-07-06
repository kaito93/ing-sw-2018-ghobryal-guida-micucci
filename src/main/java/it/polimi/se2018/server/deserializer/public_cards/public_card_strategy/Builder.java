package it.polimi.se2018.server.deserializer.public_cards.public_card_strategy;

import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.server.deserializer.public_cards.PublicCardsTransfer;
import it.polimi.se2018.server.model.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * class that is used to make the cards constructor from the json deserialized file
 * implements observer interface
 * @author Andrea Micucci
 */
public class Builder implements Observer{
    private ArrayList<Card> poc;
    private ObjectiveCardStrategy strategy;
    private String toBeCompared;
    private PublicCardsTransfer jT;

    /**
     * class constructor: build the arraylist where created cards are stored
     */
    public Builder(){
        jT = new PublicCardsTransfer();
        poc = new ArrayList<>();
    }

    /**
     * update method that compare the type of cards, that are two strings, and return the card object
     * created, if the type is the same
     */
    @Override
    public void update(Observable o, Object arg) {
        jT = (PublicCardsTransfer) arg;
       if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
           poc.add(new PublicObjectiveCard(jT.getTitle(), jT.getDescription(), jT.getScores(), strategy));
       }
    }

    /**
     * setter method for the json transiction structure
     * @param jT that need to be modified
     */
    public void setjt(PublicCardsTransfer jT) {
        this.jT = jT;
    }

    /**
     * getter method to obtain the strategy
     * @return return the choosen strategy
     */
    public ObjectiveCardStrategy getStrategy() {
        return strategy;
    }

    /**
     * getter method to obtain the arraylist of created cards
     * @return the arraylist of cards
     */
    public List<Card> getPoc() {
        return poc;
    }

    /**
     *  setter method for the strategy (need to be used in the constructor of derived class)
     * @param strategy that has to be setted
     */
    public void setStrategy(ObjectiveCardStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * setter method for the string of the class(for the derived constructor class)
     * @param toBeCompared the string that has to be setted to this object, and need to be compared to determine strategy
     */
    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    /**
     * setter method for the deck of cards
     * @param deck that has to be setted
     */
    public void setDeck(List deck){
        int i;
        for(i=0; i<poc.size(); i++){
            deck.add(poc.get(i));
        }
    }
}
