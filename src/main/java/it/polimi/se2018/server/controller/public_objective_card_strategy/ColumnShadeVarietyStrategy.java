package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;

/**
 * Column Shade Variety
 *
 * @author Anton Ghobryal
 */

public class ColumnShadeVarietyStrategy extends ObjectiveCardStrategy {

    /**
     * Read description of this card for further information
     *
     * @param map   player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score) {
        int counter = 0;  //counts how many times the player achieves this card
        boolean numBoolean = false;  //indicates if there isn't 2 dices with the same value on the same column
        int numCounter = 1;   //counts how many consecutive dices with different colors
        for (int i = 0; i < map.numColumn(); i++) {   //iterates on columns
            for (int j = 0; j < map.numRow() - 1; j++) {//iterates on row
                numBoolean=check(map,j,i,true,map.numRow(),j+1);
                //conta quanti numeri diversi sono venuti fuori fino ad ora
                if (numBoolean)
                    numCounter++;
                else {
                    numCounter = 1;
                    break;
                }
            }
            //conta quante volte il giocatore ha soddisfatto i requisiti di questa carta
            if (numCounter == map.numRow()) {
                counter++;
                numCounter = 1;
            }
        }
        return counter * score;
    }
}
