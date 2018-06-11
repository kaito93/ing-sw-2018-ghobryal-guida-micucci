package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Grinding Stone Tool Card
 *
 * @author Anton Ghobryal
 */

public class GrindingStone extends ToolCardStrategy {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    /**
     * Read description of this card for further information
     *
     * @param player  n.a.
     * @param dice    the chosen dice
     * @param turn    n.a.
     * @param t1       n.a.
     * @param stock   n.a.
     * @param posDice n.a.
     * @param row     row's coordinate where to position the dice
     * @param column  column's coordinate where to position the dice
     * @param t3      n.a.
     * @param t4      n.a.
     * @param t5      n.a.
     * @param t6      n.a.
     */
    //posiziono io il dado per facilitarti la vita, aggiungi nel messaggio le coordinate
    public void useTool(Player player, Dice dice, int turn, int t1, List<Dice> stock
            , boolean posDice, int row, int column, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6) {
        if (dice == null) {
            errorBool.setErrorMessage("There's no passed dice");
            errorBool.setErrBool(true);
        }else if(player.posDice(dice, row, column)) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
        }
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGrinding(title, player);
    }
}
