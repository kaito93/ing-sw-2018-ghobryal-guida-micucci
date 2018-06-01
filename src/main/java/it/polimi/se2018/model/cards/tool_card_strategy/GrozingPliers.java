package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

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
     * @param errorMessage an error message that indicates the cause of return false
     * @return true :D
     */

    public boolean useTool(Player player, Dice dice, int value, int a, ArrayList<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        if(value<1 || value > 6){
            errorMessage = "Invalid passed value";
            return false;
        }
        if(value>1 && value < 6){
            firstValue = value + 1;
            secondValue = value - 1;
        } else if(value==1){
            firstValue = value + 1;
            secondValue = 0;
        } else{
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
        setChosenValue(dice, firstValue);
        return firstValue != 0;
    }

    /**
     * forces the chosen value (the decremented one) into the dice's value
     * @param dice the chosen dice
     * @return a boolean that verifies if the operation was successful or not
     */

    public boolean setSecondValue(Dice dice){
       setChosenValue(dice, secondValue);
        return secondValue != 0;
    }

    public void setChosenValue(Dice dice, int value1){
        try {
            if (value1 != 0)
                dice.setValue(value1);
        } catch (InvalidValueException e){
            System.err.println("Invalid Value Exception on Grinding Stone");
        }
    }

    @Override
    public void requestMessage(VirtualView view) {
        view.createMessageGrozing();
    }
}
