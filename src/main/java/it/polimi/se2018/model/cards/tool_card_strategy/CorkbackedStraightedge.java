package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

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
     */

    public void useTool(Player player, Dice dice, int row, int column, List<Dice> stock
            , boolean a, int t1, int t2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        if(player.getMap().isAdjacentDice(row, column)) {
            errorBoolTool.setErrorMessage("There's an Adjacent dice to the chosen coordinates");
            errorBoolTool.setErrBool(true);
            return;
        }
        try {
            player.getMap().getCell(row, column).setDice(dice);
        } catch (notValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in CorkbackedStraightedge tool card", e);
        }
        errorBoolTool.setErrorMessage(null);
        errorBoolTool.setErrBool(false);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageCork(title, player);
    }
}
