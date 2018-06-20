package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;

/**
 * Flux Brush Tool Card
 * @author Anton Ghobryal
 */

public class FluxBrush extends ToolCardStrategy {



    /**
     * Read description of this card for further information
     * @param player player on turn
     * @param dice a stock dice
     * @param row row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     * @param t n.a.
     * @param t0 n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    //posiziono il dado nuovo e tolgo dalla riserva il dado vecchio
    //perché si passa t3 come parametro?
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> t, int t1, int t2, Dice t3,
                        RoundSchemeCell[] t4, List<Player> t5, int t6){
        posDiceControl(player, dice, row, column);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageFluxBrush(title, player);
    }
}
