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
     * @param t1 n.a.
     * @param stock Round's Stock
     * @param t2 n.a.
     * @param row row's coordinate on the map where the dice should be placed
     * @param column column's coordinate on the map where the dice should be placed
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param turns players in order
     */
    //posiziono solo il dado che viene scelto al posto del secondo turno
    public void useTool(Player playerCurr, Dice dice, int turn, int t1, List<Dice> stock, boolean t2
            , int row, int column, Dice t3, RoundSchemeCell[] t4, List<Player> turns, int t5){
        boolean a;
        if(turn==1){
            a = playerCurr.getMap().posDice(dice, row, column);
            if(a) {
                playerCurr.setRunningPliers(true);
                errorBool.setErrorMessage(null);
                errorBool.setErrBool(false);
            }
            else {
                playerCurr.setRunningPliers(false);
                errorBool.setErrorMessage("Il dado non è stato posizionato correttamente");
                errorBool.setErrBool(true);
            }
        }
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageRunning(title,player);
    }
}