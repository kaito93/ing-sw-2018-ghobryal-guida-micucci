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
     * @return a boolean that is "true" if it is possible to put the dices in the player's map
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player player, Dice dice, int row1, int column1, ArrayList<Dice> dicesToMove,
            boolean temp, int row2, int column2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6) throws notValidCellException {
        int row3=0, column3=0, row4=0, column4=0;
        boolean a, b, c, d;
        if(mapContainsDice(player.getMap(), dicesToMove.get(0), row3, column3)
            && mapContainsDice(player.getMap(), dicesToMove.get(1), row4, column4)
                && dicesToMove.size()==2){
            a = player.posDice(dicesToMove.get(0), row1, column1);
            b = player.posDice(null, row3, column3);
            c = player.posDice(dicesToMove.get(1), row2, column2);
            d = player.posDice(null, row4, column4);
            if(!(a&&b)){
                System.out.println("first dice not valid");
            }
            if(!(c&&d)){
                System.out.println("second dice not valid");
            }
            return a && b && c && d;
        }
        return false;
    }
}
