package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

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
     */
    public void useTool(Player player, Dice dice, int row1, int column1, List<Dice> dicesToMove,
            boolean temp, int row2, int column2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        int row3=0;
        int column3=0;
        int row4=0;
        int column4=0;
        boolean a;
        boolean b;
        try {
            if(mapContainsDice(player.getMap(), dicesToMove.get(0), row3, column3)
                && mapContainsDice(player.getMap(), dicesToMove.get(1), row4, column4)
                    && dicesToMove.size()==2){
                a = player.getMap().posDice(dicesToMove.get(0), row1, column1);
                if (!a) {
                    errorBool.setErrorMessage("first dice not valid");
                    return;
                }
                player.getMap().posDice(null, row3, column3);
                b = player.getMap().posDice(dicesToMove.get(1), row2, column2);
                if (!b) {
                    player.getMap().posDice(dicesToMove.get(0), row3, column3);
                    player.getMap().posDice(null, row1, column1);
                    errorBool.setErrorMessage("second dice not valid");
                    return;
                }
                player.getMap().posDice(null, row4, column4);
            } else {
                errorBool.setErrorMessage("Map may not contains a passed dice");
                errorBool.setErrBool(true);
            }
        } catch (notValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in Lathekin tool card", e);
        }
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageLathekin(title,player);
    }
}
