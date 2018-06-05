package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Tap Wheel Tool Card
 * @author Anton Ghobryal
 */

public class TapWheel extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param player player's map
     * @param roundSchemeMapDice a chosen dice from the Round Scheme
     * @param row1 row's coordinate on the map where the first chosen dice to be positioned
     * @param column1 column's coordinate on the map where the first chosen dice to be positioned
     * @param dicesToMove an array list of the dices to be moved
     * @param t1 n.a.
     * @param row2  row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2 column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param t2 n.a.
     * @param roundSchemeMap the Round Scheme
     * @param t3 n.a.
     * @param posDice which round contains the dice on the round scheme
     */
    public void useTool(Player player, Dice roundSchemeMapDice, int row1, int column1, List<Dice> dicesToMove, boolean t1,
            int row2, int column2, Dice t2, RoundSchemeCell[] roundSchemeMap, List<Player> t3, int posDice){
        int b=0;
        int c=0;
        int f=0;
        int g=0;
        boolean d;
        String errorMessage = "";
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeMapDice)){
            try {
                if (dicesToMove.size() == 1) {
                    if (roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor()) && mapContainsDice(player.getMap(), dicesToMove.get(0), b, c)) {
                        d = player.getMap().posDice(dicesToMove.get(0), row1, column1, errorMessage);
                        if (d)
                            player.getMap().posDice(null, b, c, errorMessage);
                        else {
                            errorBoolTool.setErrorMessage(errorMessage);
                            errorBoolTool.setErrBool(true);
                            return;
                        }
                    }
                } else if (dicesToMove.size() == 2 && roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor())
                        && mapContainsDice(player.getMap(), dicesToMove.get(0), b, c)
                        && mapContainsDice(player.getMap(), dicesToMove.get(1), f, g)
                        && dicesToMove.get(1).getColor().equalsColor(dicesToMove.get(0).getColor())) {
                    Lathekin x = new Lathekin();
                    x.useTool(player, roundSchemeMapDice, row1, column1, dicesToMove, t1, row2, column2, t2,
                            roundSchemeMap, t3, posDice);
                }
            }
            catch(notValidCellException e){
                LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in TapWheel tool card", e);
            }
        }
        errorBoolTool.setErrorMessage("the round scheme doesn't contain the chosen dice from the round scheme");
        errorBoolTool.setErrBool(true);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageTap(title,player);
    }
}
