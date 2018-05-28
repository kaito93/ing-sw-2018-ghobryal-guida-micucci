package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

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
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that verifies if the operation of positioning the dice was successful or not
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player player, Dice roundSchemeMapDice, int row1, int column1, ArrayList<Dice> dicesToMove, boolean t1,
            int row2, int column2, Dice t2, RoundSchemeCell[] roundSchemeMap, ArrayList<Player> t3, int posDice, String errorMessage) throws notValidCellException {
        int b=0, c=0, f=0, g=0;
        boolean d, e;
        String temp = null;
        errorMessage = "";
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeMapDice)){
            if(dicesToMove.size()==1){
                if(roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor()) && mapContainsDice(player.getMap(), dicesToMove.get(0), b, c)){
                    d = player.getMap().posDice(dicesToMove.get(0), row1, column1, errorMessage);
                    if(d)
                        player.getMap().posDice(null, b, c, errorMessage);
                    return d;
                }
            }else if(dicesToMove.size()==2){
                if(roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor()) && mapContainsDice(player.getMap(), dicesToMove.get(0), b, c)
                    && mapContainsDice(player.getMap(), dicesToMove.get(1), f, g) && dicesToMove.get(1).getColor().equalsColor(dicesToMove.get(0).getColor())){
                    d = player.getMap().posDice(dicesToMove.get(0), row1, column1, temp);
                    if(temp!=null) errorMessage = errorMessage.concat("\n"+temp);
                    e = player.getMap().posDice(dicesToMove.get(1), row2, column2, temp);
                    if(temp!=null) errorMessage = errorMessage.concat("\n"+temp);
                    if(d) {
                        player.getMap().posDice(null, b, c, temp);
                        if(temp!=null) errorMessage = errorMessage.concat("\n"+temp);
                    }
                    if(e) {
                        player.getMap().posDice(null, f, g, temp);
                        if(temp!=null) errorMessage = errorMessage.concat("\n"+temp);
                    }
                    return d && e;
                }
            }
        }
        errorMessage = "the round scheme doesn't contain the chosen dice from the round scheme";
        return false;
    }
}
