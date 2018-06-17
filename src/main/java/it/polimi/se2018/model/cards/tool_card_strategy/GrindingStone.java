package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;

/**
 * Grinding Stone Tool Card
 *
 * @author Anton Ghobryal
 */

public class GrindingStone extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     *
     * @param player player on turn
     * @param dice a chosen dice from the stock
     * @param row  row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     * @param t     n.a.
     * @param t0    n.a.
     * @param t1    n.a.
     * @param t2    n.a.
     * @param t3    n.a.
     * @param t4    n.a.
     * @param t5    n.a.
     * @param t6    n.a.
     */
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> t
            , boolean t0, int t1, int t2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6) {
        posDiceControl(player, dice, row, column);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGrinding(title, player);
    }
}
