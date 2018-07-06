package it.polimi.se2018.server.deserializer.server_deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Deserializer;

import java.lang.reflect.Type;

/**
 * class used to deserializer the Server information
 * @author Andrea Micucci
 */
public class ServerDeserialize extends Deserializer {

    private ServerStructure ss;

    /**
     * class constructor: return an object inizialized to deserializer the json file given by the path
     * @param path string that refer to the json file
     */
    public ServerDeserialize(String path){
        super(path);
    }

    /**
     * method that make the deserializing of the json file with the servers information.
     * it put it in the ss data structure
     */
    public void deserializing(){
        Type server = new TypeToken<ServerStructure>(){}.getType();
        ss = getGson().fromJson(getBr(), server);
    }

    public ServerStructure getSs() {
        return ss;
    }
}
