package it.polimi.se2018.server.deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.shared.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * class to create the deck of all the private cards saved in json file
 */
public class DeckOfPrivateCards {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private BufferedReader br;
    private ArrayList<PrivateObjectiveCard> objectiveCards;

    /**
     * class constructor: create a deck of all the private cards
     * @param path of the file to deserializer
     */
    public DeckOfPrivateCards(String path){
        File json = new File(path);
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
        Type listPrivCards = new TypeToken<ArrayList<PrivateObjectiveCard>>(){}.getType();
        Gson gson = new Gson();
        objectiveCards = gson.fromJson(br, listPrivCards);
    }

    /**
     * method that return all the private cards in the array list
     * @return an list of private objective cards
     */
    public List<PrivateObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

}
