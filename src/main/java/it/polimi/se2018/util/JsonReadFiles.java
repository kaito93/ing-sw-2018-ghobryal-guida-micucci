package it.polimi.se2018.util;

import com.google.gson.Gson;
//import it.polimi.se2018.JsonFiles.*;
import it.polimi.se2018.model.Dice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class JsonReadFiles {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/it/polimi/se/2018/JsonFiles/Dices.json"));
            System.out.println("yo vengo de cuba");
            Dice dices = gson.fromJson(br, Dice.class);
            System.out.println("ciao popolo");
            System.out.println(dices.getValue());
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }
}
