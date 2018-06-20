package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.shared.exception.notValidCellException;

import java.util.logging.Level;

/**
 * Row Color Variety Public Objective Card
 * @author Anton Ghobryal
 */

public class RowColorVarietyStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score) {
        int counter=0;  //counts how many times the player achieves this card
        boolean colorBool=false;    //indicates if there isn't 2 dices with the same color on the same row
        int colorCounter=1; //counts how many consecutive dices with different colors
        for(int i=0; i<map.numRow(); i++){    //iterates on rows
            for(int j=0; j<map.numColumn()-1; j++){    //iterates on column
                for(int k=j+1; k<map.numColumn(); k++){     //iterates on the next column
                    try {
                        if((!map.isEmptyCell(i, j))&&(!map.isEmptyCell(i, k))){    //controls if there is a dice or not
                            if(map.getCell(i,j).getDice().getColor().equals(map.getCell(i,k).getDice().getColor())){    //controls if there is two consecutive dices with the same color
                                colorBool = false;
                                break;
                            }else{
                                colorBool = true;
                            }
                        }else{
                            colorBool = false;
                            break;
                        }
                    } catch (notValidCellException e) {
                    LOGGER.log(Level.SEVERE, e.toString()+"\nsearch method in class RowColorVarietyStrategy", e);
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
                colorBool=false;
                colorCounter = 1;
            }
        }
        return counter*score;
    }
}
