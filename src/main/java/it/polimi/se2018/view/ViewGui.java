package it.polimi.se2018.view;

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
}
