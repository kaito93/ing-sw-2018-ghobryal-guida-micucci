package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Flux Remover Tool Card
 * @author Anton Ghobryal
 */

public class FluxRemover extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param stockDice a chosen dice from the stock
     * @param value the value should be forced into the chosen dice from the bag
     * @param a n.a.
     * @param bag the container of all dices
     * @param b n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that verifies if the operation of positioning the dice was successful or not
     */
    public boolean useTool(Player player, Dice stockDice, int value, int a, ArrayList<Dice> bag
            , boolean b, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage)throws notValidCellException {
        bag.add(stockDice);
        Collections.shuffle(bag);
        Dice temp = bag.get(1);
        bag.remove(temp);
        temp.setValue(value);
        return true;
    }
}
