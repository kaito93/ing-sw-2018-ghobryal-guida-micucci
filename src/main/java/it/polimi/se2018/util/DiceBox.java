package it.polimi.se2018.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.Dice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/** class DiceBox
 * contains all method to generate the DiceBox from the Json file
 * @author Andrea
 */
public class DiceBox {

    Gson gson = new Gson();
    File json;
    BufferedReader br = null;
    ArrayList<Dice> box;

    /**
     * class constructor
     * @throws FileNotFoundException if the json file that contains all the info about the dices don't exists
     */
    public DiceBox() throws FileNotFoundException{

        try{
            json = new File("src/main/java/it/polimi/se2018/JsonFiles/Dices.json");
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        Type listType = new TypeToken<ArrayList<Dice>>(){}.getType();
        box = gson.fromJson(br, listType);
    }

    /**
     * get method that return the ArrayList of dices
     * @return ArrayList of dices from Json file
     */
    public ArrayList<Dice> getBox(){
        return this.box;
    }
}
