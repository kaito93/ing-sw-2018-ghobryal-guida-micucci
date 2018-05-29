package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Lens Cutter Tool Card
 * @author Anton Ghobryal
 */

public class LensCutter extends ToolCardStrategy {

    private Dice dice;
    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param stockDice a chosen dice from the stock
     * @param posDice which round contains the dice on the round scheme
     * @param t1 n.a.
     * @param stock round's stock
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @param roundSchemeDice a chosen dice from the Round Scheme
     * @param roundSchemeMap the Round Scheme
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that verifies if the chosen dice from the Round Scheme
     */

    //non ho posizionato il dado
    public boolean useTool(Player player, Dice stockDice, int posDice, int t1, ArrayList<Dice> stock, boolean t2
            , int t3, int t4, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        ArrayList<Dice> temp = new ArrayList<>();
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeDice) && stockDice!=null
                && roundSchemeDice!=null && stock.contains(stockDice)){
            temp.add(stockDice);
            stock.remove(stockDice);
            roundSchemeMap[posDice].removeDice(roundSchemeDice);
            roundSchemeMap[posDice].setDices(temp);
            dice = roundSchemeDice;
            return true;
        }
        dice = null;
        errorMessage = "Invalid Passed Parameters";
        return false;
    }
}
