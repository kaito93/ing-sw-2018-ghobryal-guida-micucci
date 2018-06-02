package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.ArrayList;

/**
 * Glazing Hammer Tool Card
 * @author Anton Ghobryal
 */

public class GlazingHammer extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param dice1 n.a.
     * @param turn player's turn number
     * @param a n.a.
     * @param stock Round's Stock
     * @param posDice a boolean verifies if the player has chosen a dice from the stock or not on his second turn
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     * @param errorMessage an error message that indicates the cause of return false
     * @return a boolean that verifies if the player can use the card or not
     */

    //posDice ok!
    public boolean useTool(Player player, Dice dice1, int turn, int a, ArrayList<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4, ArrayList<Player> t5, int t6, String errorMessage) throws notValidCellException {
        if(turn==2 && !posDice)
            for (Dice dice : stock)
                dice.throwDice();
        else
            errorMessage = "Player doesn't respect card restrictions";
        return turn==2 && !posDice;
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGlazing(title);
    }
}
