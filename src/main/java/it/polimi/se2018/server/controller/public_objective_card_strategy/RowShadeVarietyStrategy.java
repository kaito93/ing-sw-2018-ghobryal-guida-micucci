package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;

/**
 * Row Shade Variety Public Objective Card
 *
 * @author Anton Ghobryal
 */

public class RowShadeVarietyStrategy extends ObjectiveCardStrategy {

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
        boolean numBool;    //indicates if there isn't 2 dices with the same color on the same row
        int numCounter = 1;    //counts how many consecutive dices with different colors
        for (int i = 0; i < map.numRow(); i++) {  //iterates on rows
            for (int j = 0; j < map.numColumn() - 1; j++) { //iterates on column

                numBool = check(map, i, j, false, map.numColumn(), j + 1);
                //conta quanti numeri diversi sono venuti fuori fino ad ora
                if (numBool)
                    numCounter++;
                else {
                    numCounter = 1;
                    break;
                }
            }
            //conta quante volte il giocatore ha soddisfatto i requisiti di questa carta
            if (numCounter == map.numColumn()) {
                counter++;
                numCounter = 1;
            }
        }
        return counter * score;
    }
}
