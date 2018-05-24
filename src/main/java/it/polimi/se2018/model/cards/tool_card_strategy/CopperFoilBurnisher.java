package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cell.ColoredCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Copper Foil Burnisher Tool Card
 * @author Anton Ghobryal
 */

public class CopperFoilBurnisher extends ToolCardStrategy {


    /**
     * Read description of this card for further information
     * @param player player on turn
     * @param dice dice needed to be repositioned
     * @param row row's coordinate on the map where the chosen dice to be positioned
     * @param column column's coordinate on the map where the chosen dice to be positioned
     * @param stock n.a.
     * @param a n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @return a boolean that verifies if the player can use the card or not
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player player, Dice dice, int row, int column, ArrayList<Dice> stock
            , boolean a, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6) throws notValidCellException {
        int i=0, j=0;
        if(mapContainsDice(player.getMap(), dice, i, j))
            if(validPosition(dice, player.getMap(), row, column)) {
                player.getMap().getCell(row, column).setDice(dice);
                player.getMap().getCell(i, j).setDice(null);
            }
        return mapContainsDice(player.getMap(), dice, i, j) && validPosition(dice, player.getMap(), row, column);
    }



    /**
     * method that verify if a color exists already in a column of the matrix
     * @param map player's map
     * @param column column's coordinate on the map where to search the equivalent numbers
     * @param color the color of the chosen dice to be positioned on the map
     * @return a boolean that is true if the color already exist in the column of matrix, else false
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */

    private boolean alreadyExistInColumn(Map map, int column, Color color) throws notValidCellException {
        int index;
        for(index = 0; index < map.numRow(); index++)
        {
            if(map.getCell(index, column).getColor().equalsColor(color))
                return true;
        }
        return false;
    }

    /**
     * method that verify if a color exists already in a row of the matrix
     * @param map player's map
     * @param row row's coordinate on the map where to search the equivalent numbers
     * @param color the color of the chosen dice to be positioned on the map
     * @return a boolean that is true if the color already exist in the row of matrix, else false
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */

    private boolean alreadyExistInRow(Map map ,int row, Color color) throws notValidCellException{
        int index;
        for(index = 0; index < map.numRow(); index++)
        {
            if(map.getCell(row, index).getColor().equalsColor(color))
                return true;
        }
        return false;
    }

    /**
     * a method that verify is a position is right to put a dice inside it
     * @param dice chose dice
     * @param map player's map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean that is "true" if it is possible to put a dice in it
     * @throws notValidCellException when the indexes of the row and the row not respect the interval number of matrix.
     */
    private boolean validPosition(Dice dice, Map map, int row, int column) throws notValidCellException{
        if ((map.getCell(row, column).getDice() != null)
                || ((map.getCell(row, column) instanceof ColoredCell)
                && !(map.getCell(row, column).getColor().equalsColor(dice.getColor())))) {
            return false;
        } else if ((map.getCell(row, column).getDice() == null)
                && (map.getCell(row, column).getColor().equalsColor(dice.getColor())))
        {
            return !alreadyExistInColumn(map, column, dice.getColor()) || !alreadyExistInRow(map, row, dice.getColor());
        }
        return true;
    }
}
