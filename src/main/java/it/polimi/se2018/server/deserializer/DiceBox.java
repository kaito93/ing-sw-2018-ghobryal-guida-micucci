package it.polimi.se2018.server.deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.shared.Deserializer;
import it.polimi.se2018.shared.model_shared.Dice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** class DiceBox
 * contains all method to generate the DiceBox from the Json file
 * @author Andrea
 */
public class DiceBox extends Deserializer {

    private ArrayList<Dice> box;

    /**
     * class constructor
     * @param path of the file that have to deserializer
     */
    public DiceBox(String path) {

        super(path);

    }

    /**
     * deserialize the object
     */
    @Override
    public void deserializing() {
        Type listType = new TypeToken<ArrayList<Dice>>(){}.getType();
        if (getBr() != null)
            box = getGson().fromJson(getBr(), listType);

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
