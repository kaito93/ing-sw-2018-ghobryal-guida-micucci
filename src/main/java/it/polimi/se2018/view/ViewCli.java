package it.polimi.se2018.view;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.Message;

import java.util.ArrayList;
import java.util.logging.Level;

public class ViewCli extends View {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    @Override
    public void addLog(String message) {
        LOGGER.log(Level.INFO,message);
    }

    @Override
    public void myTurn(boolean posDice, boolean useTools) {
        addLog("E' il tuo turno. Scegli che mossa fare");
    }

    public Cell[][] chooseMap(ArrayList<Cell[][]> maps){
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        return (maps.get(0));
    }
}
