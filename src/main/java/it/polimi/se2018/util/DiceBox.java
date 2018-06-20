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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/** class DiceBox
 * contains all method to generate the DiceBox from the Json file
 * @author Andrea
 */
public class DiceBox {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private ArrayList<Dice> box;

    /**
     * class constructor
     * @param path of the file that have to deserialize
     */
    public DiceBox(String path) {

        BufferedReader br=null;
        try{
            File json = new File(path);
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
        Type listType = new TypeToken<ArrayList<Dice>>(){}.getType();
        Gson gson = new Gson();
        if (br != null)
            box = gson.fromJson(br, listType);

        for (Dice aBox : box) {
            aBox.throwDice();
        }
        initDiceBox();
    }

    /**
     * get method that return the ArrayList of dices
     * @return ArrayList of dices from Json file
     */
    public List<Dice> getBox(){
        return this.box;
    }

    /**
     * method that extract dices from the DiceBox to another arraylist
     * @param dicesNumber number of dices that need to be extract
     * @return arraylist of dices that contain all the dices extracted
     */
    public List<Dice> extractDice(int dicesNumber){
        ArrayList<Dice> toBeReturned = new ArrayList<>();
        for(int i = 0; i < dicesNumber; i++)
        {
            toBeReturned.add(this.getBox().get(i));
            this.box.remove(i);
        }

        return toBeReturned;
    }

    /**
     * method that shuffle all the dice in the arraylist
     */
    private void initDiceBox(){
        Collections.shuffle(box);
    }

}
