package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.server.message.MessageUpdate;
import it.polimi.se2018.util.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public abstract class View {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    GameView gameStatus;
    ConnectionClient client;


    public View(){
        gameStatus= new GameView();
    }

    public abstract void startView();

    public abstract Cell[][] chooseMap(ArrayList<Cell[][]> maps, String username);


    public void setPublicInformation(ArrayList<String> titlePublic, ArrayList<String> descriptionPublic, ArrayList<String> titleTools,
                                     ArrayList<String> descriptionTools){
        gameStatus.setTitlePublicObjective(titlePublic);
        gameStatus.setDescriptionPublicObjective(descriptionPublic);
        gameStatus.setTitleTools(titleTools);
        gameStatus.setDescriptionTools(descriptionTools);

        addLog("Ho aggiornato le informazioni relative alle carte obiettivo pubbliche");

    }

    public void setPrivateInformation (String titlePrivate, String descriptionPrivate){
        gameStatus.setTitlePrivateObjective(titlePrivate);
        gameStatus.setDescriptionPrivateObjective(descriptionPrivate);
        addLog("Ho aggiornato le informazioni relative alla tua carta obiettivo privata");
    }

    public void updateUsers(ArrayList<String> users, ArrayList<Cell[][]> cells, ArrayList<Boolean> useTools,
                            RoundSchemeCell roundSchemeMap[], ArrayList<Dice> stock ){
        gameStatus.setUsers(users);
        gameStatus.setCells(cells);
        gameStatus.setUseTools(useTools);
        gameStatus.setRoundSchemeMap(roundSchemeMap);
        gameStatus.setStock(stock);
        addLog("Ho aggiornato le informazioni relative alle carte schema di tutti i giocatori");
    }

    public void updateFavor (int favor){
        if (gameStatus.getFavor()!=favor){
            gameStatus.setFavor(favor);
            addLog("Ho aggiornato le informazioni relative ai tuoi punti favore rimanenti");
        }

    }

    public void setClient(ConnectionClient client) {
        this.client = client;
    }

    public abstract void addLog(String message);

    public abstract void myTurn(boolean posDice, boolean useTools);

    public void accept(MessageUpdate message) {
        updateUsers(message.getUsers(),message.getCells(), message.getUseTools(),message.getRoundSchemeMap(), message.getStock());
        addLog(message.getMessage());
    }

    public abstract String askNewUsername();

}
