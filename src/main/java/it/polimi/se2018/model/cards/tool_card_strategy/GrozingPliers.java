package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Grozing Pliers Tool Card
 * @author Anton Ghobryal
 */

public class GrozingPliers extends ToolCardStrategy {

    private int firstValue; //+1
    private int secondValue; //-1


    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param dice the chosen dice
     * @param value dice's value that is needed to be increased or decreased
     * @param a n.a.
     * @param stock n.a.
     * @param posDice n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @return true :D
     */

    public boolean useTool(Player player, Dice dice, int value, int a, ArrayList<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6) throws notValidCellException {
        if(value>1 && value < 6){
            firstValue = value + 1;
            secondValue = value - 1;
        } else if(value==1){
            firstValue = value + 1;
            secondValue = 0;
        } else if(value==6){
            firstValue = 0;
            secondValue = value - 1;
        }
        return true;
    }

    /**
     * @return the value increased if it's possible (different from zero)
     */

    public int getFirstValue() {
        return firstValue;
    }

    /**
     * @return the value decreased if it's possible (different from zero)
     */

    public int getSecondValue() {
        return secondValue;
    }

    /**
     * forces the chosen value (the incremented one) into the dice's value
     * @param dice the chosen dice
     * @return if the operation was successful or not
     */

    public boolean setFirstValue(Dice dice){
        if(firstValue!=0)
            dice.setValue(firstValue);
        return firstValue!=0;
    }

    /**
     * forces the chosen value (the decremented one) into the dice's value
     * @param dice the chosen dice
     * @return a boolean that verifies if the operation was successful or not
     */

    public boolean setSecondValue(Dice dice){
        if(secondValue!=0)
            dice.setValue(secondValue);
        return secondValue!=0;
    }
}
