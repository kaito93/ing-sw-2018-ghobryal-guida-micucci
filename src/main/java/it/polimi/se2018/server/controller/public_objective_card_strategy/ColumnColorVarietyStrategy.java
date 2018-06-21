package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;

/**
 * Column Color Variety Public Objective Card
 * @author Anton Ghobryal
 */

public class ColumnColorVarietyStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score) {
        int counter=0;  //counts how many times the player achieves this card
        boolean colorBool=true;    //indicates if there is 2 dices with the same color on the same column
        int colorCounter=1;    //counts how many consecutive dices with different colors
        for(int i=0; i<map.numColumn(); i++){   //iterates on columns
            for(int j=0; j<map.numRow()-1; j++) {    //iterates on row
                for (int k = j + 1; k < map.numRow(); k++) {
                    colorBool = isColorDuplicated(map, i, j, k);
                    //iterates on the next row
                    if (colorBool){
                        break;
                    }
                }
                if (!colorBool) {
                    colorCounter++;
                } else {
                    colorCounter = 1;
                    break;
                }
            }
            if(colorCounter==map.numRow()) {
                counter++;
                colorBool=true;
                colorCounter = 1;
            }
        }
        return counter*score;
    }
}
