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

/** class DiceBox
 * contains all method to generate the DiceBox from the Json file
 * @author Andrea
 */
public class DiceBox {

    private Gson gson = new Gson();
    private File json;
    private BufferedReader br = null;
    private ArrayList<Dice> box;

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

        box.get(0).throwDice();
        System.out.println(box.get(0).getValue());
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
            box.remove(i);
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

    public void initDiceBox(){
        Collections.shuffle(box);
    }

}
