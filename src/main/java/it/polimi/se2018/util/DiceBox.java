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
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

/** class DiceBox
 * contains all method to generate the DiceBox from the Json file
 * @author Andrea
 */
public class DiceBox {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private Gson gson = new Gson();
    private File json;
    private BufferedReader br = null;
    private ArrayList<Dice> box;

    /**
     * class constructor
     * @throws FileNotFoundException if the json file that contains all the info about the dices don't exists
     */
    public DiceBox() {

        try{
            json = new File("src/main/java/it/polimi/se2018/JsonFiles/Dices.json");
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
        Type listType = new TypeToken<ArrayList<Dice>>(){}.getType();
        box = gson.fromJson(br, listType);

        for (int index = 0; index<box.size(); index++){
            box.get(index).throwDice();
        }
        initDiceBox();
    }

    /**
     * get method that return the ArrayList of dices
     * @return ArrayList of dices from Json file
     */
    public ArrayList<Dice> getBox(){
        return this.box;
    }

    /**
     * method that erase all the dice until a choosen index
     * @param index number of dice that has to been deleted
     */
    public void eraseDices(int index){
        int i=0;
        while (i<index){
            box.remove(0);
            i++;
        }

    }

    /**
     * method that extract dices from the DiceBox to another arraylist
     * @param dicesNumber number of dices that need to be extract
     * @return arraylist of dices that contain all the dices extracted
     */
    public ArrayList<Dice> extractDice(int dicesNumber){
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
    public void initDiceBox(){
        Collections.shuffle(box);
    }

}
