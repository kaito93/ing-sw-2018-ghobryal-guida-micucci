package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

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
     * @param stock round's stock
     * @param a n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 a stock dice before the throw
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    //posiziono il dado nuovo e tolgo dalla riserva il dado vecchio
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> stock
            , boolean a, int t1, int t2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        if (player.getPosDice()<3){
            if(player.posDice(dice, row, column)) {
                stock.remove(t3);
                errorBool.setErrorMessage(null);
                errorBool.setErrBool(false);
                return;
            }
            errorBool.setErrorMessage("PosDice fail in FluxBrush Tool Card");
            errorBool.setErrBool(true);
        }
            else{
                errorBool.setErrorMessage("Hai già piazzato il massimo numero di dadi per questo round [2]");
                errorBool.setErrBool(true);
            }
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageFluxBrush(title, player);
    }
}
