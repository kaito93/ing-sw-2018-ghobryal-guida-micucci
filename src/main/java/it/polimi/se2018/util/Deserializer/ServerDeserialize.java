package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class ServerDeserialize {

    Gson gson;
    File file;
    BufferedReader br;
    ServerStructure ss;

    public ServerDeserialize(String path){
        gson = new Gson();
        file = new File(path);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato");
        }
    }

    public void Deserializing(){
        Type server = new TypeToken<ServerStructure>(){}.getType();
        ss = gson.fromJson(br, server);
    }
}
