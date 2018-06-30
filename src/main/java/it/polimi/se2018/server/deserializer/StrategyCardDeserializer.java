package it.polimi.se2018.server.deserializer;

import com.google.gson.Gson;

import java.io.*;
import java.util.Observable;

import it.polimi.se2018.server.deserializer.JsonTransition;
import it.polimi.se2018.shared.Logger;

import java.util.logging.Level;

/**
 * abstract class that will use to make the deserializer of the cards
 */
public abstract class StrategyCardDeserializer extends Observable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private Gson gson = new Gson();
    private BufferedReader br;


    /**
     * class constructor that inizialize the reading of the json file
     *
     * @param path path of the json file
     */
    public StrategyCardDeserializer(String path) {
        String[] tokens = path.split("/");
        if (tokens[0].equalsIgnoreCase("src")){
            File file;
            file = new File(path);
            try{
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e){
                LOGGER.log(Level.SEVERE, "File non trovato", e);
            }
        }
        else{
            try{
                br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            }
            catch (NullPointerException e){
                LOGGER.log(Level.SEVERE, "File non trovato nel path " +path, e);
            }
        }
    }

    /**
     * method that deserializer the json file to an json transiction data structure, that is used to create the card objects
     */
    public abstract void deserializing();

    /**
     * method that extract a cell from the arraylist of jsontransiction
     *
     * @param index of cell that need to be extract
     * @return the cell of the arraylist
     */
    public abstract JsonTransition getSingleTransiction(int index);

    /**
     * abstract method to set-up the observers of this class
     */
    public abstract void setUpObserver();

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
