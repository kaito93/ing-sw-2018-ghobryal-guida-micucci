package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.view.View;

public class PlayerMovePosDice {

    private Player player;
    private View view;
    private int row;
    private int column;
    private Dice dice;

    public PlayerMovePosDice(Player player1, View view1, int row1, int column1, Dice dice1){
        player = player1;
        view = view1;
        row = row1;
        column = column1;
        dice = dice1;
    }

    public Dice getDice() {
        return dice;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
