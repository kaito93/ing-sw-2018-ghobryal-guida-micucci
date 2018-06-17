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
    private int row3=-1; //poszione riga iniziale primo dado
    private int column3=-1; //poszione colonna iniziale primo dado
    private int row4=-1; //poszione riga iniziale secondo dado
    private int column4=-1; //poszione colonna iniziale secondo dado


    /**
     * Read description of this card for further information
     * @param player who plays this turn
     * @param t1 n.a.
     * @param row1 row's coordinate on the map where the first dice needed to be repositioned
     * @param column1 column's coordinate on the map where the first dice needed to be repositioned
     * @param dicesToMove an array list with the dices to move
     * @param t2 n.a.
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
    public void useTool(Player player, Dice t1, int row1, int column1, List<Dice> dicesToMove,
            boolean t2, int row2, int column2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        boolean a;
        boolean b;
        if(dicesToMove.size()==2){
            try {
                if(!diceExistOnCell(player.getMap(), dicesToMove.get(0), row3, column3) || !diceExistOnCell(player.getMap(), dicesToMove.get(1), row4, column4)){
                    return;
                }
            }catch (NullPointerException e){
                errorBool.setErrorMessage("non c'è un dado nelle prime o nelle seconde coordinate");
                errorBool.setErrBool(true);
                return;
            }
            player.getMap().removeDiceMap(row3, column3);
            player.getMap().removeDiceMap(row4, column4);
            a = player.getMap().posDice(dicesToMove.get(0), row1, column1);
            b = player.getMap().posDice(dicesToMove.get(1), row2, column2);
            try {
                if (!a) {
                    if (b) {
                        player.getMap().removeDiceMap(row2, column2);
                        player.getMap().getCell(row4, column4).setDice(dicesToMove.get(1));
                    }
                    player.getMap().getCell(row3, column3).setDice(dicesToMove.get(0));
                    errorBool.setErrorMessage("posizionamento non valido del primo dado");
                    errorBool.setErrBool(true);
                }
                if (!b) {
                    if (a) {
                        player.getMap().removeDiceMap(row1, column1);
                        player.getMap().getCell(row3, column3).setDice(dicesToMove.get(0));
                    }
                    player.getMap().getCell(row4, column4).setDice(dicesToMove.get(1));
                    errorBool.setErrorMessage("posizionamento non valido del secondo dado");
                    errorBool.setErrBool(true);
                }
            }catch (notValidCellException e){
                LOGGER.log(Level.SEVERE, e.toString()+"useTool method in Lathekin class", e);
            }
            if(a&&b){
                errorBool.setErrorMessage(null);
                errorBool.setErrBool(false);
            }
        } else {
            errorBool.setErrorMessage("I dadi passati non sono esattamente "+ dicesToMove.size() +" dadi");
            errorBool.setErrBool(true);
        }
    }

    /**
     * sets the row's first position of the first dice
     * @param row3 row's coordinate initially of the first dice
     */
    @Override
    public void setRow3(int row3) {
        this.row3 = row3;
    }

    /**
     * sets the row's first position of the second dice
     * @param row4 row's coordinate initially of the second dice
     */
    @Override
    public void setRow4(int row4) {
        this.row4 = row4;
    }

    /**
     * gets the row's first position of the first dice
     * @return row's coordinate initially of the first dice
     */
    @Override
    public int getRow3() {
        return row3;
    }

    /**
     * gets the row's first position of the second dice
     * @return row's coordinate initially of the second dice
     */
    @Override
    public int getRow4() {
        return row4;
    }

    /**
     * gets the column's first position of the first dice
     * @return column's coordinate initially of the first dice
     */
    @Override
    public int getColumn3() {
        return column3;
    }

    /**
     * sets the column's first position of the first dice
     * @param column3 column's coordinate initially of the first dice
     */
    @Override
    public void setColumn3(int column3) {
        this.column3 = column3;
    }

    /**
     * gets the column's first position of the second dice
     * @return column's coordinate initially of the second dice
     */
    @Override
    public int getColumn4() {
        return column4;
    }

    /**
     * sets the column's first position of the second dice
     * @param column4 column's coordinate initially of the second dice
     */
    @Override
    public void setColumn4(int column4) {
        this.column4 = column4;
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageLathekin(title,player);
    }
}
