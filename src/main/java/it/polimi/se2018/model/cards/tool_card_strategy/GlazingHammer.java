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

public class GlazingHammer extends ToolCardStrategy {

    protected int firstValue; //+1
    protected int secondValue; //-1

    /**
     * Read description of this card for further information
     * @param t n.a.
     * @param t0 n.a.
     * @param turn player's turn number
     * @param t7 n.a.
     * @param stock Round's Stock
     * @param posDice a boolean verifies if the player has chosen a dice from the stock or not on his second turn
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */

    //non posiziono nessun dado perché non c'entra con la descrizione della carta
    public void useTool(Player t, Dice t0, int turn, int t7, List<Dice> stock, boolean posDice, int t1, int t2
            , Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        if(turn==2 && !posDice)
            for (Dice dice : stock)
                dice.throwDice();
        else{
            errorBool.setErrorMessage("Player doesn't respect color restrictions");
            errorBool.setErrBool(true);
            return;
        }
        errorBool.setErrorMessage(null);
        errorBool.setErrBool(false);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGlazing(title);
    }

    /**
     * @return the value increased if it's possible (different from zero)
     */

    @Override
    public int getFirstValue() {
        return firstValue;
    }

    /**
     * @return the value decreased if it's possible (different from zero)
     */

    @Override
    public int getSecondValue() {
        return secondValue;
    }
}
