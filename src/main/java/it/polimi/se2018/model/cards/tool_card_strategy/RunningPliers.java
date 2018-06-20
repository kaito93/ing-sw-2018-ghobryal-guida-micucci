package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;

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
     * @param t0 n.a.
     * @param t1 n.a.
     * @param row row's coordinate on the map where the dice should be placed
     * @param column column's coordinate on the map where the dice should be placed
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    //posiziono solo il dado che viene scelto al posto del secondo turno
    public void useTool(Player playerCurr, Dice dice, int turn, int t0, List<Dice> t1, int row, int column,
                        Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        if(turn==1) {
            posDiceControl(playerCurr, dice, row, column);
            return;
        }
        errorBool.setErrorMessage("Questa carta si può usare solo al primo turno");
        errorBool.setErrBool(true);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageRunning(title,player);
    }
}