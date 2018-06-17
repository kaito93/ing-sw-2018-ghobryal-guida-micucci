package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.network.client.message.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * class that is used to deserialize the path of the file json to search
 */
public class PathDeserializer {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    ArrayList<PathJsonStructure> pathjson;
    BufferedReader br;
    File file;
    Gson gson;

    /**
     * constructor of the class, that inizialize the buffered reader of the json file
     * which contain all the path of the other json files with all the info about the
     * game
     */
    public PathDeserializer(){
        file = new File("src/main/java/it/polimi/se2018/JsonFiles/Pathname.json");
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e){
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
        gson = new Gson();
        pathjson = new ArrayList<>();
    }

    /**
     * method that deserialize the json path file and save all the info in an
     * PathJsonStructure
     */
    public void Deserializing(){
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
        for(int index=0; index<pathjson.size(); index++){
            if (pathjson.get(index).getType().equalsIgnoreCase(type))
                return pathjson.get(index).getPath();
        }
        return error;
    }
}
