package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Grozing Pliers Tool Card
 * @author Anton Ghobryal
 */

public class GrozingPliers extends ToolCardStrategy {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

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
     */

    //qui non posso posizonarti il dado perché ci sono 2 valori diversi, quindi mandi un nuovo messaggio di tipo
    // posizionamento dado con le coordinate e il dado da mettere e hai i metodi qui che i servono
    public void useTool(Player player, Dice dice, int value, int a, List<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4,  List<Player> t5, int t6){
        if(value<1 || value > 6){
            errorBool.setErrorMessage("Invalid passed dice value");
            errorBool.setErrBool(true);
            return;
        } else if(value>1 && value < 6){
            firstValue = value + 1;
            secondValue = value - 1;
        } else if(value==1){
            firstValue = value + 1;
            secondValue = 0;
        } else{
            firstValue = 0;
            secondValue = value - 1;
        }
        errorBool.setErrorMessage(null);
        errorBool.setErrBool(false);
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
     * forces the value into the dice
     * @param dice the chosen dice
     * @param value1 the chosen value
     */
    public void setChosenValue(Dice dice, int value1){

            if (value1 != 0) {
                try {
                    dice.setValue(value1);
                } catch (InvalidValueException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }

    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGrozing(title,player);
    }
}
