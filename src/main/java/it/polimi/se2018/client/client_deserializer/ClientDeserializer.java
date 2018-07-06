package it.polimi.se2018.client.client_deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Deserializer;

import java.lang.reflect.Type;

/**
 * class to deserializer the client information in json file
 * @author Andrea Micucci
 */
public class ClientDeserializer extends Deserializer {


    private ClientStructure cs;

    /**
     * class constructor, initialize the BufferedReader for the json file
     * @param path of the json file
     */
    public ClientDeserializer(String path){
        super(path);
    }

    /**
     * method that make the deserializing of the info in json file and save it in a support data structure
     * that is ClientStructure
     */
    public void deserializing(){
        Type client = new TypeToken<ClientStructure>(){}.getType();
        cs = getGson().fromJson(getBr(), client);
    }

    /**
     * getter method for the client structure
     * @return a ClientStructure object with the info of the client like integers
     */
    public ClientStructure getCs() {
        return cs;
    }
}

