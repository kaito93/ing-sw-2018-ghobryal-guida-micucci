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
import java.util.logging.Logger;

/**
 * class to deserialize the client information in json file
 */
public class ClientDeserializer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    Gson gson;
    BufferedReader br;
    File json;
    ClientStructure cs;

    /**
     * class constructor, initialize the BufferedReader for the json file
     * @param path of the json file
     */
    public ClientDeserializer(String path){
        json = new File(path);
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }

        gson = new Gson();
    }

    /**
     * method that make the deserializing of the info in json file and save it in a support data structure
     * that is ClientStructure
     */
    public void Deserializing(){
        Type client = new TypeToken<ClientStructure>(){}.getType();
        cs = gson.fromJson(br, client);
    }

    /**
     * getter method for the client structure
     * @return a ClientStructure object with the info of the client like integers
     */
    public ClientStructure getCs() {
        return cs;
    }
}

