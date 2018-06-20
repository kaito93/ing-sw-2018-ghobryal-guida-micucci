package it.polimi.se2018.server.deserializer;

/**
 * data structure to make the parsing of the cards stored in json files
 */
public class JsonTransition {

    private String title=null;
    private String description=null;

    /**
     * getter method for the description of the cards
     * @return a string that represent the method of the card
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter method to the title of the cards
     * @return a string that represent the title of the card
     */
    public String getTitle() {
        return title;
    }
}
