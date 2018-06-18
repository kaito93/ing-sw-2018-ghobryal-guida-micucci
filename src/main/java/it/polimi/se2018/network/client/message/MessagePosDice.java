package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

public class MessagePosDice implements MessageVC {

    private static final long serialVersionUID = 6624068114762037735L;
    private Dice dice;
    private int row;
    private int column;
    /**
     * method that set the final column of the dice
     * @param column an integer
     */
    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * method that set the Dice
     * @param dice a dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }
    /**
     * method that set the row
     * @param row an integer
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * method that return the column choose
     * @return an integer
     */
    public int getColumn() {
        return column;
    }

    /**
     * method that return the row choose
     * @return an integer
     */
    public int getRow() {
        return row;
    }
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.setPos(dice,row,column);
    }
}
