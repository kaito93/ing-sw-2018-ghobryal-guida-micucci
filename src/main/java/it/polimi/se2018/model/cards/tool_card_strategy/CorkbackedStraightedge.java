package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Cork-backed Straightedge Tool Card
 * @author Anton Ghobryal
 */

public class CorkbackedStraightedge extends ToolCardStrategy {


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
        if(player.getMap().getCell(row, column).getDice()!=null)
            return false;
        else if(row<1 && column<1) { //up left control
            if (player.getMap().getCell(row, column + 1).getDice() != null || player.getMap().getCell(row + 1, column).getDice() != null
                    || player.getMap().getCell(row + 1, column + 1).getDice() != null)
                return false;
        }else if(row > 0 && row<player.getMap().numRow()-1 && column<1){ //centre left control
            if (player.getMap().getCell(row, column + 1).getDice() != null || player.getMap().getCell(row + 1, column).getDice() != null
                    || player.getMap().getCell(row + 1, column + 1).getDice() != null
                    || player.getMap().getCell(row-1, column).getDice()!=null || player.getMap().getCell(row-1, column+1).getDice()!=null)
                return false;
        }else if(row>player.getMap().numRow()-2 && column<1){ // down left control
            if(player.getMap().getCell(row-1, column).getDice()!=null || player.getMap().getCell(row-1, column+1).getDice()!=null
                    || player.getMap().getCell(row, column+1).getDice()!=null)
                return false;
        }else if(row<1 && column>0 && column<player.getMap().numColumn()-1){ //centre up control
            if(player.getMap().getCell(row, column-1).getDice()!=null ||  player.getMap().getCell(row, column+1).getDice()!=null
                    || player.getMap().getCell(row+1, column-1).getDice()!=null || player.getMap().getCell(row+1, column).getDice()!=null
                    || player.getMap().getCell(row+1, column+1).getDice()!=null)
                return false;
        }else if(row>0 && column>0 && row<player.getMap().numRow()-1 && column<player.getMap().numColumn()-1){ //centre control
            if(player.getMap().getCell(row, column-1).getDice()!=null ||  player.getMap().getCell(row, column+1).getDice()!=null
                    || player.getMap().getCell(row+1, column-1).getDice()!=null || player.getMap().getCell(row+1, column).getDice()!=null
                    || player.getMap().getCell(row+1, column+1).getDice()!=null || player.getMap().getCell(row-1, column-1).getDice()!=null
                    || player.getMap().getCell(row-1, column).getDice()!=null || player.getMap().getCell(row-1, column+1).getDice()!=null)
                return false;
        }else if(row>player.getMap().numRow()-2 && column>0 && column<player.getMap().numColumn()-1){ //centre down control
            if(player.getMap().getCell(row-1, column).getDice()!=null || player.getMap().getCell(row-1, column+1).getDice()!=null
                    || player.getMap().getCell(row, column+1).getDice()!=null || player.getMap().getCell(row-1, column-1).getDice()!=null
                    || player.getMap().getCell(row, column-1).getDice()!=null)
                return false;
        }else if(column>player.getMap().numColumn()-2 && row<1){ //up right control
            if(player.getMap().getCell(row, column-1).getDice()!=null || player.getMap().getCell(row+1, column-1).getDice()!=null
                    || player.getMap().getCell(row+1, column).getDice()!=null)
                return false;
        }else if(row>0 && row<player.getMap().numRow()-2 && column>player.getMap().numColumn()-2){ //centre right control
            if(player.getMap().getCell(row, column-1).getDice()!=null || player.getMap().getCell(row+1, column-1).getDice()!=null
                    || player.getMap().getCell(row+1, column).getDice()!=null || player.getMap().getCell(row-1, column-1).getDice()!=null
                    || player.getMap().getCell(row-1, column).getDice()!=null)
                return false;
        }else if(row>player.getMap().numRow()-2 && column>player.getMap().numColumn()-2){ //down right
            if(player.getMap().getCell(row-1, column).getDice()!=null || player.getMap().getCell(row-1, column-1).getDice()!=null
                    || player.getMap().getCell(row, column-1).getDice()!=null)
                return false;
        }
        return player.getMap().setCell(dice, row, column);
    }
}
