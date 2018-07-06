package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.server.network.VirtualView;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.util.List;

/**
 * Tap Wheel Tool Card
 * @author Anton Ghobryal
 */

public class TapWheel extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     * @param player player's map
     * @param roundSchemeMapDice a chosen dice from the Round Scheme
     * @param row1 row's coordinate on the map where the first chosen dice to be positioned
     * @param column1 column's coordinate on the map where the first chosen dice to be positioned
     * @param dicesToMove an array list of the dices to be moved
     * @param row2  row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2 column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param t2 n.a.
     * @param roundSchemeMap the Round Scheme
     * @param t3 n.a.
     * @param posDice which round contains the dice on the round scheme
     */
    //guarda il commento su Lathekin ma qui è variablile sul numero di dadi passati
    public void useTool(Player player, Dice roundSchemeMapDice, int row1, int column1, List<Dice> dicesToMove,
                        int row2, int column2, Dice t2, RoundSchemeCell[] roundSchemeMap, List<Player> t3, int posDice){
        boolean d;
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeMapDice) && !dicesToMove.isEmpty()
                && roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor())){ //se il dado passato è dentro il tracciato dei round
            if (dicesToMove.size() == 1) { //se sposto solo un dado e il suo colore è uguale a quello del tracciato dei round
                try {
                    if(!diceExistOnCell(player.getMap(), dicesToMove.get(0), row3, column3)){ //se la cella da cui parto è vuota
                        return;
                    }
                }catch (NullPointerException e){
                    return;
                }
                player.getMap().removeDiceMap(row3, column3);//svuoto la posizione attuale
                d = player.getMap().posDice(dicesToMove.get(0), row1, column1);
                if (!d){
                    player.getMap().getCell(row3, column3).setDice(dicesToMove.get(0)); //reset a com'era prima
                    errorBool.setErrorMessage("Errore nel posizionamento del dado per la carta Tap Wheel");
                    errorBool.setErrBool(true);
                    return;
                }
                errorBool.setErrorMessage(null);
                errorBool.setErrBool(false);
                return;
        } else if (dicesToMove.size() == 2 && dicesToMove.get(1).getColor().equalsColor(dicesToMove.get(0).getColor())) {
                //se invece ci sono 2 dadi da spostare diventa uguale alla carta lathekin
                Lathekin x = new Lathekin();
                x.setRow3(row3);
                x.setRow4(row4);
                x.setColumn3(column3);
                x.setColumn4(column4);
                x.useTool(player, roundSchemeMapDice, row1, column1, dicesToMove, row2, column2, t2,
                        roundSchemeMap, t3, posDice);
                return;
            }
        }
        errorBool.setErrorMessage("Lo schema dei round non contiene il dado passato");
        errorBool.setErrBool(true);
    }



    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageTap(title,player);
    }
}
