package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.server.model.*;
import it.polimi.se2018.server.network.VirtualView;
import it.polimi.se2018.server.util.ErrorBool;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.Serializable;
import java.util.List;

/**
 * a generic private tool card
 * @author Anton Ghobryal
 */

public abstract class ToolCardStrategy implements Serializable {

    protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final int ZERO = 0;
    protected static ErrorBool errorBool = new ErrorBool(null, false);
    int column3;
    int column4;
    int row3;
    int row4;



    /**
     * gets an ErrorBoolTool status
     * @return a data structure within an error message_socket and an error boolean
     */
    public static ErrorBool getErrorBool() {
        return errorBool;
    }

    public abstract void useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
        , int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
        , List<Player> turns, int posDice1);

    public abstract void requestMessage(VirtualView view, String title, int player);

    /**
     * @return the value increased if it's possible (different from zero)
     */

    public int getFirstValue() {
        return ZERO;
    }

    /**
     * @return the value decreased if it's possible (different from zero)
     */

    public int getSecondValue() {
        return getFirstValue();
    }

    /**
     * verifies if the dice is positioned on the passed coordinates or not
     * @param map player's map
     * @param dice a dice that is supposed to be on the map
     * @param row row's coordinate on the map where there should be the passed dice
     * @param column column's coordinate on the map where there should be the passed dice
     * @return a boolean, true if the dice is really positioned on the map, else false
     */
    protected boolean diceExistOnCell(Map map, Dice dice, int row, int column){
        try {
            if(!map.existDice(dice, row, column)){
                errorBool.setErrorMessage("Il dado passato non c'è nelle coordinate ("+row+","+column+")");
                errorBool.setErrBool(true);
                return false;
            }
        }catch (NullPointerException e){
            errorBool.setErrorMessage("non c'è un dado nelle coordinate ("+row+","+column+")");
            errorBool.setErrBool(true);
            return false;
        }
        return true;
    }

    /**
     * controls the dice positioning con error assignment
     * @param player the current player
     * @param dice the chosen dice to be positioned
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     */
    protected void posDiceControl(Player player, Dice dice, int row, int column){
        if(player.posDice(dice, row, column)) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return;
        }
        errorBool.setErrorMessage("Il dado non è stato posizionato correttamente");
        errorBool.setErrBool(true);
    }

    /**
     * gets the column's first position of the first dice
     * @return column's coordinate initially of the first dice
     */
    protected int getColumn3() {
        return column3;
    }

    /**
     * sets the column's first position of the first dice
     * @param column3 column's coordinate initially of the first dice
     */
    public void setColumn3(int column3) {
        this.column3 = column3;
    }

    /**
     * sets the row's first position of the first dice
     * @param row3 row's coordinate initially of the first dice
     */

    public void setRow3(int row3) {
        this.row3 = row3;
    }

    /**
     * sets the row's first position of the second dice
     * @param row4 row's coordinate initially of the second dice
     */

    public void setRow4(int row4) {
        this.row4 = row4;
    }

    /**
     * gets the row's first position of the first dice
     * @return row's coordinate initially of the first dice
     */
    public int getRow3() {
        return row3;
    }

    /**
     * gets the row's first position of the second dice
     * @return row's coordinate initially of the second dice
     */
    public int getRow4() {
        return row4;
    }

    /**
     * gets the column's first position of the second dice
     * @return column's coordinate initially of the second dice
     */
    public int getColumn4() {
        return column4;
    }

    /**
     * sets the column's first position of the second dice
     * @param column4 column's coordinate initially of the second dice
     */
    public void setColumn4(int column4) {
        this.column4 = column4;
    }

    /**
     * verifies if there's a dice on the map or not
     * @param player the player
     * @param dice the chosen dice
     * @param row the current row coordinate
     * @param column the current column coordinate
     * @return a boolean, true if there's no dice on this position, else false
     */
    boolean checkExists(Player player, Dice dice, int row, int column){
        try {
            if(!diceExistOnCell(player.getMap(), dice, row, column)){
                return true;
            }
        }catch (NullPointerException e){
            return true;
        }
        return false;
    }

    /**
     * forces the dice into the cell
     * @param player the current player
     * @param row the current row coordinate
     * @param column the current column coordinate
     * @param dice the chosen dice
     */
    void setBool(Player player, int row, int column, Dice dice){
        player.getMap().getCell(row, column).setDice(dice);
        errorBool.setErrorMessage(null);
        errorBool.setErrBool(false);
    }

    /**
     * forces the dice out of the passed coordinates
     * @param player the current player
     * @param row0 row's coordinate on the map where to remove the dice
     * @param column0 column's coordinate on the map where to remove the dice
     */
    void setDice(Player player, int row0, int column0){
            player.getMap().getCell(row0, column0).setDice(null);
    }

    /**
     * rewinds the status of the current cell
     * @param player the current player
     * @param row0 row's coordinate where to reset the removed dice in the process
     * @param column0 column's coordinate where to reset the removed dice in the process
     * @param dice the removed dice initially before process
     */
    void setErrPos(Player player, int row0, int column0, Dice dice){
        player.getMap().getCell(row0, column0).setDice(dice);
        errorBool.setErrorMessage("il giocatore non rispetta le restrizioni di posizionamento");
        errorBool.setErrBool(true);
    }
}
