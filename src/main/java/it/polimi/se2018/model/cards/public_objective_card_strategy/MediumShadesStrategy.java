package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.logging.Level;

/**
 * Medium Shades Public Objective Card
 * @author Anton Ghobryal
 */

public class MediumShadesStrategy extends ObjectiveCardStrategy {

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){
        int counter3=0;
        int counter4=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for(int j=0; j<map.numColumn(); j++){   try {
                //iterates on columns
                if(!map.isEmptyCell(i, j)){   //contols if there is a dice
                    if(map.getCell(i,j).getDice().getValue()==3)
                        counter3++;
                    else if(map.getCell(i,j).getDice().getValue()==4)
                        counter4++;
                }
                } catch (notValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nsearch method in class MediumShadesStrategy", e);
                }
            }
        }
        // the minimum of the counters is how many times the player achieved this card
        counter3=Math.min(counter3, counter4);
        return counter3*score;
    }
}
