package it.polimi.se2018.server.deserializer.server_deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.logging.Level;

/**
 * class used to deserializer the Server information
 */
public class ServerDeserialize {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    private Gson gson;
    private BufferedReader br;
    private ServerStructure ss;

    /**
     * class constructor: return an object inizialized to deserializer the json file given by the path
     * @param path string that refer to the json file
     */
    public ServerDeserialize(String path){
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
     * method that make the deserializing of the json file with the servers information.
     * it put it in the ss data structure
     */
    public void deserializing(){
        Type server = new TypeToken<ServerStructure>(){}.getType();
        ss = gson.fromJson(br, server);
    }

    public ServerStructure getSs() {
        return ss;
    }
}
