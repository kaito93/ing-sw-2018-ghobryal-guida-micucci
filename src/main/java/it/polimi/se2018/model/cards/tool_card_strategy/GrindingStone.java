package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Grinding Stone Tool Card
 * @author Anton Ghobryal
 */

public class GrindingStone extends ToolCardStrategy {


    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param dice the chosen dice
     * @param turn n.a.
     * @param a n.a.
     * @param stock n.a.
     * @param posDice n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @param errorMessage an error message that indicates the cause of return false
     * @return true :D
     */
    public boolean useTool(Player player, Dice dice, int turn, int a, ArrayList<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        if(dice==null){
            errorMessage = "There's no passed dice";
            return false;
        }
        switch (dice.getValue()){
            case 1:
                dice.setValue(6);
                break;
            case 2:
                dice.setValue(5);
                break;
            case 3:
                dice.setValue(4);
                break;
            case 4:
                dice.setValue(3);
                break;
            case 5:
                dice.setValue(2);
                break;
            case 6:
                dice.setValue(1);
                break;
        }
        return true;
    }
}
