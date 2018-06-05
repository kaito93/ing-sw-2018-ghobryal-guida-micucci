package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class ClientDeserializer {

    Gson gson;
    BufferedReader br;
    File json;
    ClientStructure cs;

    public ClientDeserializer(String path){
        json = new File(path);
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato");
        }

        gson = new Gson();
    }

    public void Deserializing(){
        Type client = new TypeToken<ClientStructure>(){}.getType();
        cs = gson.fromJson(br, client);
    }
}
