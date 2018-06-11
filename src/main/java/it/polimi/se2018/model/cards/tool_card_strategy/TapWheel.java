package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Tap Wheel Tool Card
 * @author Anton Ghobryal
 */

public class TapWheel extends ToolCardStrategy {
    private int row3=0; //poszione riga iniziale primo dado
    private int column3=0; //poszione colonna iniziale primo dado
    private int row4=0; //poszione riga iniziale secondo dado
    private int column4=0; //poszione colonna iniziale secondo dado

    /**
     * Read description of this card for further information
     * @param player player's map
     * @param roundSchemeMapDice a chosen dice from the Round Scheme
     * @param row1 row's coordinate on the map where the first chosen dice to be positioned
     * @param column1 column's coordinate on the map where the first chosen dice to be positioned
     * @param dicesToMove an array list of the dices to be moved
     * @param t1 n.a.
     * @param row2  row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2 column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param t2 n.a.
     * @param roundSchemeMap the Round Scheme
     * @param t3 n.a.
     * @param posDice which round contains the dice on the round scheme
     */
    //guarda il commento su Lathekin ma qui è variablile sul numero di dadi passato
    public void useTool(Player player, Dice roundSchemeMapDice, int row1, int column1, List<Dice> dicesToMove, boolean t1,
            int row2, int column2, Dice t2, RoundSchemeCell[] roundSchemeMap, List<Player> t3, int posDice){
        boolean d;
        if(roundSchemeMap[posDice].getRestOfStock().contains(roundSchemeMapDice)){
            if (dicesToMove.size() == 1) {
                if (roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor())) {
                    d = player.getMap().posDice(dicesToMove.get(0), row1, column1);
                    if (d)
                        player.getMap().posDice(null, row3, column3);
                    else {
                        errorBool.setErrorMessage("posDice method in TapWheel tool card");
                        errorBool.setErrBool(true);
                        return;
                    }
                }
            } else if (dicesToMove.size() == 2 && roundSchemeMapDice.getColor().equalsColor(dicesToMove.get(0).getColor())
                    && dicesToMove.get(1).getColor().equalsColor(dicesToMove.get(0).getColor())) {
                Lathekin x = new Lathekin();
                x.setRow3(row3);
                x.setRow4(row4);
                x.setColumn3(column3);
                x.setColumn4(column4);
                x.useTool(player, roundSchemeMapDice, row1, column1, dicesToMove, t1, row2, column2, t2,
                        roundSchemeMap, t3, posDice);
            }
        }
        errorBool.setErrorMessage("the round scheme doesn't contain the chosen dice from the round scheme");
        errorBool.setErrBool(true);
    }
    @Override
    public void setRow3(int row3) {
        this.row3 = row3;
    }
    @Override
    public void setRow4(int row4) {
        this.row4 = row4;
    }
    @Override
    public int getRow3() {
        return row3;
    }
    @Override
    public int getRow4() {
        return row4;
    }
    @Override
    public int getColumn3() {
        return column3;
    }
    @Override
    public void setColumn3(int column3) {
        this.column3 = column3;
    }
    @Override
    public int getColumn4() {
        return column4;
    }
    @Override
    public void setColumn4(int column4) {
        this.column4 = column4;
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageTap(title,player);
    }
}
