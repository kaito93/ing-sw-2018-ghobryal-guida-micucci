package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapsDeserializer {

    private Gson gson;
    private File json;
    private BufferedReader br;
    private ArrayList<TransitionForMaps> mapsJsonJava;

    public MapsDeserializer(){
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/Maps.json");
        try{
            br = new BufferedReader(new FileReader(json));
        } catch(FileNotFoundException e){
            System.out.println("non ho trovato il file");
        }
    }

    public void Deserializing(){
        Type listJson = new TypeToken<ArrayList<TransitionForMaps>>(){}.getType();
        mapsJsonJava = gson.fromJson(br, listJson);
    }
}
