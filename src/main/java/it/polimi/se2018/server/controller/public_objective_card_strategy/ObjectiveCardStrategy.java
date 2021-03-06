package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.shared.Logger;

import java.io.Serializable;


/**
 * a generic objective card implementation
 *
 * @author Anton Ghobryal
 */

public abstract class ObjectiveCardStrategy implements Serializable {

    protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * implements the card's description
     *
     * @param map   player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */
    public abstract int search(Map map, int score);

    /**
     * controls if there's a color duplicated in the specified coordinates
     * @param map player's map
     * @param coordinate1 a specific coordinate
     * @param coordinate2 a specific coordinate
     * @param coordinate3 a specific coordinate
     * @return a boolean, true if the color is the same for the two specified coordinates
     */
    //coordinate1=i, coordinate2=j, coordinate3=k
    //se false break
    protected boolean isColorDuplicated(Map map, int coordinate1, int coordinate2, int coordinate3) {
        if ((!map.isEmptyCell(coordinate2, coordinate1)) && (!map.isEmptyCell(coordinate3, coordinate1))) {    //controls if there is a dice or not
                //controls if there is two consecutive dices with the same color
                return map.getCell(coordinate2, coordinate1).getDice().getColor().equals(map.getCell(coordinate3, coordinate1).getDice().getColor());
        }
        return true;
    }

    /**
     * indicates if there isn't 2 dices with the same value on the same column/row
     * @param map player's map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @param rowTWO indicates if it itarates on rows or columns
     * @param numberMax the number of rows or columns dipending on which the loop iterates
     * @param initialK where the loop starts to iterate
     * @return a boolean, true if there isn't 2 dices with the same value on the same column else false
     */
    boolean check(Map map, int row, int column, boolean rowTWO, int numberMax, int initialK) {

        boolean condition = true;
        boolean numBoolean = false;  //indicates if there isn't 2 dices with the same value on the same column
        int k = initialK;
        while (k < numberMax && condition) {
            int row2;
            int column2;
            if (rowTWO) {
                row2 = k;
                column2=column;
            }else {
                column2 = k;
                row2=row;
            }
            if ((!map.isEmptyCell(row, column)) && (!map.isEmptyCell(row2, column2))) {    //controls if there is a dice or not
                    if (map.getCell(row, column).getDice().getValue() == map.getCell(row2, column2).getDice().getValue()) {   //controls if there is two consecutive dices with the same value
                        numBoolean = false;
                        condition = false;
                    } else
                        numBoolean = true;

            } else
                condition = false;
            k++;
        }
        return numBoolean;
    }
}
