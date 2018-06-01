package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

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
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that verifies if the player can use the card or not
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */

    public boolean useTool(Player player, Dice dice, int row, int column, ArrayList<Dice> stock
            , boolean a, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        int i = 0, j = 0;
        if (mapContainsDice(player.getMap(), dice, i, j)) {
            if (player.getMap().colorAlreadyExistInRow(row, dice.getColor())
                    || player.getMap().colorAlreadyExistInColumn(column, dice.getColor())
                    || (player.getMap().getCell(row, column).getColor() != null
                    && player.getMap().getCell(row, column).getColor().equalsColor(dice.getColor()))) {
                errorMessage = "Player doesn't respect color restrictions";
                return false;
            }
            player.getMap().getCell(row, column).setDice(dice);
            player.getMap().getCell(i, j).setDice(null);
            return true;
        }
        errorMessage = "The map doesn't contain the chosen dice";
        return false;
    }

    @Override
    public void requestMessage(VirtualView view) {
        view.createMessageCopper();
    }
}
