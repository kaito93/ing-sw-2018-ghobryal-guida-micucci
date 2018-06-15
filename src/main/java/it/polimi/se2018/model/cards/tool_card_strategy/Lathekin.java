package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Lathekin Tool Card
 * @author Anton Ghobryal
 */

public class Lathekin extends ToolCardStrategy {
    private int row3=0; //poszione riga iniziale primo dado
    private int column3=0; //poszione colonna iniziale primo dado
    private int row4=0; //poszione riga iniziale secondo dado
    private int column4=0; //poszione colonna iniziale secondo dado

    /**
     * Read description of this card for further information
     * @param player who plays this turn
     * @param dice n.a.
     * @param row1 row's coordinate on the map where the first dice needed to be repositioned
     * @param column1 column's coordinate on the map where the first dice needed to be repositioned
     * @param dicesToMove an array list with the dices to move
     * @param temp n.a.
     * @param row2 row's coordinate on the map where the second dice needed to be repositioned
     * @param column2 column's coordinate on the map where the second dice needed to be repositioned
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    //qui ti metto 4 attributi in questa classe con i loro set e get perché non c'è voglia di aggiungere 4 parametri in più
    //a tutti i metodi di tutte le carte, già superiamo il limite di un bel po'
    //in ogni caso ti posiziono i dadi
    public void useTool(Player player, Dice dice, int row1, int column1, List<Dice> dicesToMove,
            boolean temp, int row2, int column2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        boolean a;
        boolean b;
        if(dicesToMove.size()==2){
            a = player.getMap().posDice(dicesToMove.get(0), row1, column1);
            if (!a) {
                errorBool.setErrorMessage("first dice not valid");
                return;
            }
            player.getMap().removeDiceMap(row3, column3);
            b = player.getMap().posDice(dicesToMove.get(1), row2, column2);
            if (!b) {
                player.getMap().posDice(dicesToMove.get(0), row3, column3);
                player.getMap().removeDiceMap(row1, column1);
                errorBool.setErrorMessage("second dice not valid");
                return;
            }
            player.getMap().removeDiceMap(row4, column4);
        } else {
            errorBool.setErrorMessage("passed dices aren't exactly two");
            errorBool.setErrBool(true);
        }
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
        view.createMessageLathekin(title,player);
    }
}
