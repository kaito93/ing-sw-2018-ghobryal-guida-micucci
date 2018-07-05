package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;

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
       if (checkExists(player,dice,row0,column0)) //true se non esiste un dado nella posizione specificata
           return;
       setDice(player, row0, column0); //svuota la posizione attuale
       if (((player.getMap().isBorderEmpty() && (column>0 && row>0) && row<player.getMap().numRow()-1 && column<player.getMap().numColumn()-1)
               || (!player.getMap().isBorderEmpty() && !player.getMap().isAdjacentDice(row, column)))
               && (player.getMap().isAdjacentColor(row, column, dice.getColor())
               || !player.getMap().diceCompatibleColorCell(row, column, dice.getColor()))) {
           setErrPos(player, row0, column0, dice); //rimette il dado alla sua posizione iniziale se c'è un errore
           return;
       }
       setBool(player, row, column, dice); //setta il dado nella nuova posizione
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageCopper(title, player);
    }
}
