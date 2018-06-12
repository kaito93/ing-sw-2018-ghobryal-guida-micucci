package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.Collections;
import java.util.logging.Level;

/**
 * Flux Remover Tool Card
 *
 * @author Anton Ghobryal
 */

public class FluxRemover extends ToolCardStrategy {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    /**
     * Read description of this card for further information
     *
     * @param player    n.a.
     * @param stockDice a chosen dice from the stock
     * @param value     n.a
     * @param t1        n.a.
     * @param bag       the container of all dices
     * @param b         n.a.
     * @param row       row's coordinate where to position the dice
     * @param column    column's coordinate where to position the dice
     * @param t3        n.a.
     * @param t4        n.a.
     * @param t5        n.a.
     * @param t6        n.a.
     */
    //posiziono io il dado
    //se il giocatore non riesce a posizionare il dado perde comunque i punti perché ha scambiato un
    //dado dalla riserva con un'altro del DiceBox e perciò gli tolgo i punti a prescindere
    public void useTool(Player player, Dice stockDice, int value, int t1, List<Dice> bag
            , boolean b, int row, int column, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6) {

        if (player.posDice(stockDice, row, column)) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
        } else {
            errorBool.setErrorMessage("posDice method in FluxRemover tool card");
            errorBool.setErrBool(true);
        }

    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageFluxRemover(title, player);
    }
}
