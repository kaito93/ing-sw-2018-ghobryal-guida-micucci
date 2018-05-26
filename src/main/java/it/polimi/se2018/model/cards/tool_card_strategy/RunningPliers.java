package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Glazing Hammer Tool Card
 * @author Anton Ghobryal
 */

public class RunningPliers extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param playerCurr current player (this)
     * @param dice chose dice
     * @param turn the player's turn number
     * @param t1 n.a.
     * @param stock Round's Stock
     * @param t2 n.a.
     * @param row row's coordinate on the map where the dice should be placed
     * @param column column's coordinate on the map where the dice should be placed
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param turns players in order
     * @return a boolean that is "true" if it is possible to put a dice in it and if this card can be used
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player playerCurr, Dice dice, int turn, int t1, ArrayList<Dice> stock, boolean t2
            , int row, int column, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> turns, int t5) throws notValidCellException {
        boolean a = false;
        if(turns.indexOf(playerCurr)==0 && stock.contains(dice) && turn==1){
            a = playerCurr.getMap().setCell(dice, row, column);
            turns.remove(0);
            if(a)
                stock.remove(dice);
        }
        return a;
    }
}