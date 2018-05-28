package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Lathekin Tool Card
 * @author Anton Ghobryal
 */

public class Lathekin extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param player who plays this turn
     * @param dice n.a.
     * @param row1 row's coordinate on the map where the first dice needed to be repositioned
     * @param column1 column's coordinate on the map where the first dice needed to be repositioned
     * @param dicesToMove an array list with the dices to move
     * @param temp n.a.
     * @param row2 row's coordinate on the map where the second dice needed to be repositioned
     * @param column2 column's coordinate on the map where the second dice needed to be repositioned
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that is "true" if it is possible to put the dices in the player's map
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player player, Dice dice, int row1, int column1, ArrayList<Dice> dicesToMove,
            boolean temp, int row2, int column2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        int row3=0, column3=0, row4=0, column4=0;
        errorMessage = "";
        boolean a, b, c, d;
        if(mapContainsDice(player.getMap(), dicesToMove.get(0), row3, column3)
            && mapContainsDice(player.getMap(), dicesToMove.get(1), row4, column4)
                && dicesToMove.size()==2){
            a = player.getMap().posDice(dicesToMove.get(0), row1, column1, errorMessage);
            b = player.getMap().posDice(null, row3, column3, errorMessage);
            c = player.getMap().posDice(dicesToMove.get(1), row2, column2, errorMessage);
            d = player.getMap().posDice(null, row4, column4, errorMessage);
            if(!(a&&b)){
                errorMessage = errorMessage.concat("\nfirst dice not valid");
            }
            if(!(c&&d)){
                errorMessage = errorMessage.concat("\nsecond dice not valid");
            }
            return a && b && c && d;
        }
        errorMessage = errorMessage.concat("\nMap may not contains a passed dice");
        return false;
    }
}
