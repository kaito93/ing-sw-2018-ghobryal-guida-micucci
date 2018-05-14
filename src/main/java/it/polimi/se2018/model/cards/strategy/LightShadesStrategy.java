package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.notValidCellException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Light Shades Public Objective Card
 * @author Anton Ghobryal
 */

public class LightShadesStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){
        int counter1=0;
        int counter2=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for(int j=0; j<map.numColumn(); j++){   try {
                //iterates on columns
                if(map.getCell(i,j).getDice()!=null){   //contols if there is a dice
                    if(map.getCell(i,j).getDice().getValue()==1)
                        counter1++;
                    else if(map.getCell(i,j).getDice().getValue()==2)
                        counter2++;
                }
                } catch (notValidCellException ex) {
                    System.out.println("lightShadeStrategy");
                }
            }
        }
        // the minimum of the counters is how many times the player achieved this card
        counter1=Math.min(counter1, counter2);
        return counter1*score;
    }
}
