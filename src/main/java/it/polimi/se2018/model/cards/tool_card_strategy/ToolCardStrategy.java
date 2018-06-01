package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * a generic private tool card
 * @author Anton Ghobryal
 */

public abstract class ToolCardStrategy implements Serializable {

    /**
     * controls if the dice that the player chose is really on the map or not
     * @param map player's map
     * @param dice chose dice
     * @param row where the dice is
     * @param column where the dice is
     * @return if the map contains the dice or not
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    protected boolean mapContainsDice(Map map, Dice dice, int row, int column) throws notValidCellException {
        for(int i=0; i<map.numRow(); i++)
            for(int j=0; j<map.numColumn(); j++)
                if(map.getCell(i, j).getDice()!=null && map.getCell(i, j).getDice().getValue()==dice.getValue()
                            && map.getCell(i, j).getDice().getColor().equalsColor(dice.getColor())) {
                        row = i;
                        column = j;
                        return true;
                    }
        row = 0;
        column = 0;
        return false;
    }

    public abstract boolean useTool(Player player, Dice dice, int row1, int column1, ArrayList<Dice> stock
        , boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
        , ArrayList<Player> turns, int posDice1, String errorMessage) throws notValidCellException;

    public abstract void requestMessage(VirtualView view);

}
