package it.polimi.se2018.shared;

import com.google.gson.Gson;

import java.io.*;
import java.util.Observable;
import java.util.logging.Level;

/**
 * class that help to deserialize a json file
 * @author Samuele Guida
 */
public abstract class Deserializer extends Observable {
    private Gson gson;
    private BufferedReader br;
    public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * public constructor that set the variables gson and buffer reader
     * @param path the path of the file
     */
    public Deserializer(String path){
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

        gson = new Gson();
    }

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
    /**
     * deserialize the object
     */
    public abstract void deserializing();
}
