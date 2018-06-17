package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.network.client.message.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.logging.Level;

/**
 * class used to deserialize the Server information
 */
public class ServerDeserialize {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    Gson gson;
    File file;
    BufferedReader br;
    ServerStructure ss;

    /**
     * class constructor: return an object inizialized to deserialize the json file given by the path
     * @param path string that refer to the json file
     */
    public ServerDeserialize(String path){
        gson = new Gson();
        file = new File(path);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
    }

    /**
     * method that make the deserializing of the json file with the servers information.
     * it put it in the ss data structure
     */
    public void Deserializing(){
        Type server = new TypeToken<ServerStructure>(){}.getType();
        ss = gson.fromJson(br, server);
    }

    public ServerStructure getSs() {
        return ss;
    }
}
