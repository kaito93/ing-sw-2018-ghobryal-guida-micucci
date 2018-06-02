package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.public_objective_card_strategy.*;
import java.util.Observer;
import java.util.Observable;
import it.polimi.se2018.util.jsonTransiction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * abstract class that will use to make the deserializer of the cards
 */
public abstract class StrategyCardDeserializer extends Observable{
        private Gson gson = new Gson();
        private File json;
        private BufferedReader br;
        ArrayList<jsonTransiction>jsontrans;


    /**
     * class constructor that inizialize the reading of the json file
     * @param pathname path of the json file
     * @throws FileNotFoundException when  the file doesn't exist
     */
    public StrategyCardDeserializer(String pathname) {
            json = new File(pathname);

            try {
                br = new BufferedReader(new FileReader(json));
            } catch (FileNotFoundException e) {
                System.out.println("file di lettura json non trovato");
            }
        }

    /**
     * method that deserialize the json file to an json transiction data structure, that is used to create the card objects
     */
    public void Deserializing(){
        Type trans = new TypeToken<ArrayList<jsonTransiction>>(){}.getType();
        jsontrans = this.getGson().fromJson(this.getBr(), trans);
    }


    /**
     * getter method to return the arraylist of json transiction data structure
     * @return
     */
    public ArrayList<jsonTransiction> getJsontrans() {
        return jsontrans;
    }


    /**
     * method that extract a cell from the arraylist of jsontransiction
     * @param index of cell that need to be extract
     * @return the cell of the arraylist
     */
    public jsonTransiction getSingleTransiction(int index){
            return jsontrans.get(index);
        }

    /**
     * abstract method to set-up the observers of this class
     */
    public abstract void SetUpObserver();

    /**
     * method to notify to all the observers that a cell of arraylist is readen, and need to create the cards
     * @param publCardsingle single cell of json transiction arraylist
     */
    public void StartBuilding(jsonTransiction publCardsingle){
            setChanged();
            notifyObservers(publCardsingle);
        }

    /**
     * getter method that return json deserializer from file
     * @return gson deserializer
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * getter method of the bufferedreader
     * @return the bufferedreader of the file
     */
    public BufferedReader getBr() {
        return br;
    }
}
