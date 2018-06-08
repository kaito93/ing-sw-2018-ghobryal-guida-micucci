package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;

public class ViewGui extends View {
    @Override
    public void addLog(String message) {

    }

    @Override
    public void myTurn(boolean posDice, boolean useTools) {

    }

    public Cell[][] chooseMap(ArrayList<Cell[][]> maps, String username){
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        gameStatus.setMyUsername(username);
        return (maps.get(0));
    }

    @Override
    public void startView() {

    }

    @Override
    public String askNewUsername() {
        return null;
    }

    @Override
    public void addError(String Message) {

    }

    @Override
    public ArrayList<Object> manageCCEFR() {
        // Dice, Row, Column
        return null;
    }

    @Override
    public ArrayList<Object> managefluxRemove() {
        //Dice dice, int value
        return null;
    }

    @Override
    public Dice manageGG() {
        //Dice
        return null;
    }


    public ArrayList<Object> manageLathekin() {
        //int row1,int column1, int row2, int column2, ArrayList<Dice> dices
        return null;
    }

    @Override
    public ArrayList<Object> manageLens() {
        //Dice diceRound,Dice diceStock, int numberRound
        return null;
    }

    @Override
    public ArrayList<Object> manageTap() {
        //Dice diceRound, Arraylist Dice (dice1, Dice dice2), int row1, int row2, int column1, int column2
        return null;
    }
}
