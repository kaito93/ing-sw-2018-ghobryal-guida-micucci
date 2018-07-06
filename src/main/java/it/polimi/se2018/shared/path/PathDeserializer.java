package it.polimi.se2018.shared.path;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Deserializer;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * class that is used to deserializer the path of the file json to search
 * @author Andrea Micucci
 */
public class PathDeserializer extends Deserializer implements Serializable {

    private ArrayList<PathJsonStructure> pathjson;


    /**
     * constructor of the class, that inizialize the buffered reader of the json file
     * which contain all the path of the other json files with all the info about the
     * game
     */
    public PathDeserializer(String path){
        super(path);
        pathjson = new ArrayList<>();
    }

    /**
     * method that deserializer the json path file and save all the info in an
     * PathJsonStructure
     */
    public void deserializing(){
        Type list = new TypeToken<ArrayList<PathJsonStructure>>(){}.getType();
        pathjson = getGson().fromJson(getBr(), list);
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
