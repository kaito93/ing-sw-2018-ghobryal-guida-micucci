package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.logging.Level;

/**
 * Column Shade Variety
 * @author Anton Ghobryal
 */

public class ColumnShadeVarietyStrategy extends ObjectiveCardStrategy {

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score) {
        int counter=0;  //counts how many times the player achieves this card
        boolean numBool=false;  //indicates if there isn't 2 dices with the same value on the same column
        int numCounter=1;   //counts how many consecutive dices with different colors
        for(int i=0; i<map.numColumn(); i++){   //iterates on columns
            for(int j=0; j<map.numRow()-1; j++){    //iterates on row
                for(int k=j+1; k<map.numRow(); k++){    try {
                    //iterates on the next row
                    if((!map.isEmptyCell(j, i))&&(!map.isEmptyCell(k, i))){    //controls if there is a dice or not
                        if(map.getCell(j,i).getDice().getValue()==map.getCell(k,i).getDice().getValue()){   //controls if there is two consecutive dices with the same value
                            numBool = false;
                            break;
                        }else numBool = true;
                    }else break;
                    } catch (notValidCellException e) {
                    LOGGER.log(Level.SEVERE, e.toString()+"\nsearch method in class ColumnShadeVarietyStrategy", e);
                    }
                }
                if(numBool)
                    numCounter++;
                else{
                    numCounter = 1;
                    break;
                }
            }
            if(numCounter==map.numColumn()) {
                counter++;
                numCounter = 1;
            }
        }
        return counter*score;
    }
}
