package it.polimi.se2018.shared.path;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * class that is used to deserializer the path of the file json to search
 */
public class PathDeserializer {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private ArrayList<PathJsonStructure> pathjson;
    private BufferedReader br;
    private Gson gson;

    /**
     * constructor of the class, that inizialize the buffered reader of the json file
     * which contain all the path of the other json files with all the info about the
     * game
     */
    public PathDeserializer(String path){

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
        pathjson = new ArrayList<>();
    }

    /**
     * method that deserializer the json path file and save all the info in an
     * PathJsonStructure
     */
    public void deserializing(){
        Type list = new TypeToken<ArrayList<PathJsonStructure>>(){}.getType();
        pathjson = gson.fromJson(br, list);
    }

    /**
     * method that get the path of a given type of json file
     * @param type that the json file has to refers to
     * @return a string with the path of the json file, or error
     */
    public String getPathFromType(String type){
        String error = "errore";
        for (PathJsonStructure aPathjson : pathjson) {
            if (aPathjson.getType().equalsIgnoreCase(type))
                return aPathjson.getPath();
        }
        return error;
    }
}
