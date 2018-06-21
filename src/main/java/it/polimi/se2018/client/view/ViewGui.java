package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.Cell;

import java.util.ArrayList;
import java.util.List;

public class ViewGui extends View {
    @Override
    public void addLog(String message) {

    }

    @Override
    public void myTurn() {

    }

    public ViewGui(int timer){
        super(timer);
    }

    public int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav){
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        return 0;
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
    public List<Object> manageCE() {
        // Dice, RowDest, ColumnDect, rowMit, ColumnMit
        return null;
    }

    @Override
    public Dice managefluxRemove() {
        //Dice dice da stock
        return null;
    }

    @Override
    public Dice manageGrozing1() {
        // return dice scelto dallo stock
        return null;
    }

    @Override
    public int manageGrozing2(int minus, int major) {
        // return minus o major scelto dall'utente
        return 0;
    }

    @Override
    public List<Integer> manageGrozing3() {
        // ritorna row e column di destinazione
        return null;
    }

    @Override
    public List<Object> manageGrinding() {
        return null;
    }

    public List<Object> manageLathekin() {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices

        return null;
    }

    @Override
    public List<Object> manageLens() {
        //Dice dicStock2,Dice diceRound, int numberRound,row,column
        return null;
    }

    @Override
    public List<Object> manageTap() {
        //Dice diceRound,  int row1, int column1, int row2, int column2,Arraylist Dice (dice1, Dice dice2), posizione dado
        // in roundscheme
        return null;
    }

    @Override
    public List<Object> manageCork() {
        // Dice, row, column

        return null;
    }

    @Override
    public List<Object> managefluxBrush() {
        // dice dicebefore, dice diceafter, int rowdest, int columndest
        return null;
    }

    @Override
    public List<Object> manageFluxRemove2(Dice dice) {
        // dice row column
        return null;
    }

    @Override
    public String reconnect() {
        return null;
    }

    @Override
    public void seeScore(List<Integer> scores) {

    }
}
