package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;

/**
 * Glazing Hammer Tool Card
 * @author Anton Ghobryal
 */

public class GlazingHammer extends ToolCardStrategy {


    /**
     * Read description of this card for further information
     * @param player player on turn
     * @param t0 n.a.
     * @param turn player's turn number
     * @param t7 n.a.
     * @param stock Round's Stock
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */

    //non posiziono nessun dado perché non c'entra con la descrizione della carta
    public void useTool(Player player, Dice t0, int turn, int t7, List<Dice> stock, int t1, int t2, Dice t3,
                        RoundSchemeCell[] t4, List<Player> t5, int t6){
        if(turn==2)
            for (Dice dice : stock)
                dice.throwDice();
        else{
            errorBool.setErrorMessage("Il giocatore non rispetta la restrizione colore");
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


}
