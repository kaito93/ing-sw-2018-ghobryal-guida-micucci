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

public class ClientDeserializer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    Gson gson;
    BufferedReader br;
    File json;
    ClientStructure cs;

    public ClientDeserializer(String path){
        json = new File(path);
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }

        gson = new Gson();
    }

    public void Deserializing(){
        Type client = new TypeToken<ClientStructure>(){}.getType();
        cs = gson.fromJson(br, client);
    }
}
