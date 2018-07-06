package it.polimi.se2018.server.deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.shared.Deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * class to create the deck of all the private cards saved in json file
 * @author Andrea Micucci
 */
public class DeckOfPrivateCards extends Deserializer {


    private ArrayList<PrivateObjectiveCard> objectiveCards;

    /**
     * class constructor: create a deck of all the private cards
     * @param path of the file to deserializer
     */
    public DeckOfPrivateCards(String path){
        super(path);
    }
    /**
     * deserialize the object
     */
    @Override
    public void deserializing() {
        Type listPrivCards = new TypeToken<ArrayList<PrivateObjectiveCard>>(){}.getType();
        objectiveCards = getGson().fromJson(getBr(), listPrivCards);
    }

    /**
     * method that return all the private cards in the array list
     * @return an list of private objective cards
     */
    public List<PrivateObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

}
