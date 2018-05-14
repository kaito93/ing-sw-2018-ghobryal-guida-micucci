package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Row Shade Variety Public Objective Card
 * @author Anton Ghobryal
 */

public class RowShadeVarietyStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score) {
        int counter=0;  //counts how many times the player achieves this card
        boolean colorBool=false;    //indicates if there isn't 2 dices with the same color on the same row
        int colorCounter=1;    //counts how many consecutive dices with different colors
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for(int j=0; j<map.numColumn()-1; j++){ //iterates on column
                for(int k=j+1; k<map.numColumn(); k++){ try {
                    //iterates on the next column
                    if(map.getCell(i,j).getDice()!=null&&map.getCell(i,k).getDice()!=null){ //controls if there is a dice or not
                        if(map.getCell(i,j).getDice().getValue()==map.getCell(i,k).getDice().getValue()){   //controls if there is two consecutive dices with the same color
                            colorBool = false;
                            break;
                        }else colorBool = true;
                    }else break;
                    } catch (notValidCellException ex) {
                        System.out.println("RowShadeVarietyStrategy");
                    }
                }
                if(colorBool)
                    colorCounter++;
                else {
                    colorCounter = 1;
                    break;
                }
            }
            if(colorCounter==map.numColumn()) {
                counter++;
                colorCounter = 1;
            }
        }
        return counter*score;
    }
}
