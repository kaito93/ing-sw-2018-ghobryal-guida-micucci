package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;
import java.util.ArrayList;

/**
 * Lens Cutter Tool Card
 * @author Anton Ghobryal
 */

public class LensCutter extends ToolCardStrategy {

    private Dice dice;
    /**
     * Read description of this card for further information
     * @param player player on turn
     * @param stockDice a chosen dice from the stock
     * @param posDice which round contains the dice on the round scheme
     * @param t1 n.a.
     * @param stock round's stock
     * @param row row's coordinate on the map where the chosen dice to be positioned
     * @param column column's coordinate on the map where the chosen dice to be positioned
     * @param t5 n.a.
     * @param t6 n.a.
     * @param roundSchemeDice a chosen dice from the Round Scheme
     * @param roundSchemeMap the Round Scheme
     */

    public void useTool(Player player, Dice stockDice, int posDice, int t1, List<Dice> stock, int row, int column,
                        Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap, List<Player> t5, int t6){
        List<Dice> temp = new ArrayList<>();
        //contralla se i dadi sono veramente nelle loro collezioni
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeDice) && stock.contains(stockDice)){
            temp.add(stockDice);
            stock.remove(stockDice); //rimuove il dado dalla riserva
            roundSchemeMap[posDice].removeDice(roundSchemeDice); //rimuove il dado dal tracciato dei round
            roundSchemeMap[posDice].setDices(temp); //setta il dado della riserva sul tracciato dei round
            dice = roundSchemeDice;
            posDiceControl(player, dice, row, column); //posizionamento normale del dado
            return;
        }
        dice = null;
        errorBool.setErrorMessage("Qualche dado non appartiene al suo contenitore");
        errorBool.setErrBool(true);
    }

    /**
     * the round scheme chosen dice
     * @return the chosen dice
     */
    public Dice getDice() {
        return dice;
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageLens(title,player);
    }
}
