package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.public_objective_card_strategy.*;

import java.util.Observer;
import java.util.Observable;

import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.jsonTransiction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * abstract class that will use to make the deserializer of the cards
 */
public abstract class StrategyCardDeserializer extends Observable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    private Gson gson = new Gson();
    private File json;
    private BufferedReader br;


    /**
     * class constructor that inizialize the reading of the json file
     *
     * @param pathname path of the json file
     * @throws FileNotFoundException when  the file doesn't exist
     */
    public StrategyCardDeserializer(String pathname) {
        json = new File(pathname);

        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
    }

    /**
     * method that deserialize the json file to an json transiction data structure, that is used to create the card objects
     */
    public abstract void Deserializing();


    /**
     * getter method to return the arraylist of json transiction data structure
     * @return
     */


    /**
     * method that extract a cell from the arraylist of jsontransiction
     *
     * @param index of cell that need to be extract
     * @return the cell of the arraylist
     */
    public abstract jsonTransiction getSingleTransiction(int index);

    /**
     * abstract method to set-up the observers of this class
     */
    public abstract void SetUpObserver();

    /**
     * method to notify to all the observers that a cell of arraylist is readen, and need to create the cards
     * @param publCardsingle single cell of json transiction arraylist
     */

    /**
     * getter method that return json deserializer from file
     *
     * @return gson deserializer
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * getter method of the bufferedreader
     *
     * @return the bufferedreader of the file
     */
    public BufferedReader getBr() {
        return br;
    }
}
