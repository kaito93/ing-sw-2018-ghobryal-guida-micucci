package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;

/**
 * Flux Remover Tool Card
 *
 * @author Anton Ghobryal
 */

public class FluxRemover extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     *
     * @param player player on turn
     * @param dice a chosen dice from the stock
     * @param row  row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     * @param t     n.a.
     * @param t1    n.a.
     * @param t2    n.a.
     * @param t3    n.a.
     * @param t4    n.a.
     * @param t5    n.a.
     * @param t6    n.a.
     */
    //posiziono io il dado
    //se il giocatore non riesce a posizionare il dado perde comunque i punti perché ha scambiato un
    //dado dalla riserva con un'altro del DiceBox e perciò gli tolgo i punti a prescindere
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> t, int t1, int t2, Dice t3,
                        RoundSchemeCell[] t4, List<Player> t5, int t6) {
        //il resto della logica della carta è stato spostato nel controller nel manager della carta
        posDiceControl(player, dice, row, column); //posizionamento normale del dado scelto
        ToolCardStrategy.errorBool.setErrBool(false);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageFluxRemover(title, player);
    }
}
