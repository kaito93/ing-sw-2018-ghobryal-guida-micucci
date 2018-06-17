package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

public class MessagePosDice implements MessageVC {

    private static final long serialVersionUID = 6624068114762037735L;
    private Dice diceChoosed;
    private int row;
    private int column;

    public void setColumn(int column) {
        this.column = column;
    }

    public void setDiceChoosed(Dice diceChoosed) {
        this.diceChoosed = diceChoosed;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public void accept(Controller controller) {
        controller.setPos(diceChoosed,row,column);
    }
}
