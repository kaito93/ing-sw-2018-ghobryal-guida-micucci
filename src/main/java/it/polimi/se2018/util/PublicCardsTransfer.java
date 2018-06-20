package it.polimi.se2018.util;


/**
 * class to create data structure to deserialize the json file of the public cards
 */
public class PublicCardsTransfer extends JsonTransition {

    private String score=null;
    private String strategy=null;


    /**
     * getter method for the strategy of the cards
     * @return a string represents the strategy of the card deserializes
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * getter method for the points of the public cards
     * @return the points that assign this cards
     */
    public int getScores() {
        return Integer.decode(score);
    }
}
