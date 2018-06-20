package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Copper Foil Burnisher Tool Card
 * @author Anton Ghobryal
 */

public class CopperFoilBurnisher extends ToolCardStrategy {


    /**
     * Read description of this card for further information
     * @param player player on turn
     * @param dice dice needed to be repositioned
     * @param row row's coordinate on the map where the chosen dice to be positioned
     * @param column column's coordinate on the map where the chosen dice to be positioned
     * @param stock n.a.
     * @param row0 the row's coordinate of the dice to be repositioned
     * @param column0 the column's coordinate of the dice to be repositioned
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    //posioziono io il dado
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> stock, int row0, int column0, Dice t3,
                        RoundSchemeCell[] t4, List<Player> t5, int t6){
        try {
            if(!diceExistOnCell(player.getMap(), dice, row0, column0)){
                return;
            }
        }catch (NullPointerException e){
            return;
        }
        try {
            player.getMap().getCell(row0, column0).setDice(null);
            if (((player.getMap().isBorderEmpty() && column>0 && row>0 && row<player.getMap().numRow()-1 && column<player.getMap().numColumn()-1)
                    || (!player.getMap().isBorderEmpty() && !player.getMap().isAdjacentDice(row, column)))
                    && (player.getMap().isAdjacentColor(row, column, dice.getColor())
                    || !player.getMap().diceCompatibleColorCell(row, column, dice.getColor()))) {
                player.getMap().getCell(row0, column0).setDice(dice);
                errorBool.setErrorMessage("il giocatore non rispetta le restrizioni di posizionamento");
                errorBool.setErrBool(true);
                return;
            }
            player.getMap().getCell(row, column).setDice(dice);
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
        } catch (notValidCellException e) {
            try {
                player.getMap().getCell(row0, column0).setDice(dice);
            } catch (notValidCellException e1) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in class CopperFoilBurnisher Tool Card", e);
            }
            LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in class CopperFoilBurnisher Tool Card", e);
        }
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageCopper(title, player);
    }
}
