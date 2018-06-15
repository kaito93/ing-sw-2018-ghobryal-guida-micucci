package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.server.message.MessageUpdate;
import it.polimi.se2018.util.Logger;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public abstract class View {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    GameView gameStatus;
    ConnectionClient client;
    Timer timer = new Timer();
    int time;
    boolean a = false;

    public View(int time) {
        gameStatus = new GameView();
        this.time = time;
    }

    public abstract void startView();

    public Cell[][] chooseMap(ArrayList<Cell[][]> maps, String username, ArrayList<String> names, ArrayList<Integer> fav) {
        gameStatus.setMyUsername(username);
        return maps.get(chooseSingleMap(maps, names, fav));
    }

    public abstract int chooseSingleMap(ArrayList<Cell[][]> maps, ArrayList<String> names, ArrayList<Integer> fav);


    public void setPublicInformation(ArrayList<String> titlePublic, ArrayList<String> descriptionPublic, ArrayList<String> titleTools,
                                     ArrayList<String> descriptionTools, ArrayList<Integer> scores) {
        gameStatus.setTitlePublicObjective(titlePublic);
        gameStatus.setDescriptionPublicObjective(descriptionPublic);
        gameStatus.setTitleTools(titleTools);
        gameStatus.setDescriptionTools(descriptionTools);
        gameStatus.setScorePublicObjective(scores);

        addLog("Ho aggiornato le informazioni relative alle carte obiettivo pubbliche");

    }

    public void setPrivateInformation(String titlePrivate, String descriptionPrivate) {
        gameStatus.setTitlePrivateObjective(titlePrivate);
        gameStatus.setDescriptionPrivateObjective(descriptionPrivate);
        addLog("Ho aggiornato le informazioni relative alla tua carta obiettivo privata");
    }

    public void updateUsers(ArrayList<String> users, ArrayList<Cell[][]> cells, ArrayList<Boolean> useTools,
                            RoundSchemeCell roundSchemeMap[], ArrayList<Dice> stock, ArrayList<Integer> favors) {
        gameStatus.setUsers(users);
        gameStatus.setCells(cells);
        gameStatus.setUseTools(useTools);
        gameStatus.setRoundSchemeMap(roundSchemeMap);
        gameStatus.setStock(stock);
        gameStatus.setFavUser(favors);
        addLog("Ho aggiornato le informazioni relative alle carte schema di tutti i giocatori");
    }

    public void updateFavor(boolean pos, boolean tool) {

        gameStatus.setUseTool(tool);
        gameStatus.setPosDice(pos);

    }

    public void setClient(ConnectionClient client) {
        this.client = client;
    }

    public abstract void addLog(String message);

    public void turn(boolean posDice, boolean useTools) {
        TimerCount count = new TimerCount(); //inizializza il timer
        this.timer.schedule(count, 0, time / 20);
        myTurn(posDice, useTools);
    }

    public abstract void myTurn(boolean posDice, boolean useTools);

    public void accept(MessageUpdate message) {
        updateUsers(message.getUsers(), message.getCells(), message.getUseTools(), message.getRoundSchemeMap(),
                message.getStock(), message.getFavUsers());
        addLog(message.getMessage());
    }

    public abstract String askNewUsername();

    public void manageError(String message) {
        addError(message);
        myTurn(gameStatus.isPosDice(), gameStatus.isUseTool());
    }

    public abstract void addError(String Message);

    public abstract ArrayList<Object> manageCE();

    public abstract ArrayList<Object> managefluxBrush();

    public Dice managefluxRemove() {
        return manageGrozing1();
    }

    public abstract ArrayList<Object> manageFluxRemove2(Dice dice);

    public ArrayList<Object> manageGrinding() {
        // dice, row, column
        Dice dice = manageGrozing1();
        Dice diceBefore = null;
        try {
            diceBefore = dice.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        try {
            switch (dice.getValue()) {
                case 1:
                    dice.setValue(6);

                    break;
                case 2:
                    dice.setValue(5);
                    break;
                case 3:
                    dice.setValue(4);
                    break;
                case 4:
                    dice.setValue(3);
                    break;
                case 5:
                    dice.setValue(2);
                    break;
                case 6:
                    dice.setValue(1);
                    break;
            }
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        ArrayList<Integer> obj2 = manageGrozing3();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        obj.add(diceBefore);
        return obj;
    }


    public ArrayList<Object> manageGrozing() {
        int minus = 0, major = 0;
        Dice dice = manageGrozing1();
        if (dice.getValue() > 1 && dice.getValue() < 6) {
            major = dice.getValue() + 1;
            minus = dice.getValue() - 1;
        } else if (dice.getValue() == 1) {
            major = dice.getValue() + 1;
            minus = 0;
        } else {
            major = 0;
            minus = dice.getValue() - 1;
        }
        try {
            dice.setValue(manageGrozing2(minus, major));
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        ArrayList<Integer> obj2 = manageGrozing3();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    public abstract Dice manageGrozing1();

    public abstract int manageGrozing2(int minus, int major);

    public abstract ArrayList<Integer> manageGrozing3();

    public abstract ArrayList<Object> manageLathekin();

    public abstract ArrayList<Object> manageLens();

    public abstract ArrayList<Object> manageTap();

    public abstract ArrayList<Object> manageCork();

    class TimerCount extends TimerTask {

        int counter;

        @Override
        public void run() {

            if (a)
                this.cancel();
            else {
                if (counter == 20) {
                    this.cancel();
                    a=true;
                    addError("Hai impiegato troppo tempo a scegliere una mossa.\n Sei stato disconnesso.");
                    boolean verit=false;
                    client.sendDisconnect();
                    while (!verit){
                        String str= reconnect();
                        if (str!=null)
                        {
                            client.sendReconnect();
                            verit=true;
                        }
                        else
                            verit=false;
                    }

                } else
                    counter++;
            }

        }

    }

    public abstract String reconnect();

    public boolean isA() {
        return a;
    }

    public void updateIndex(int newIndex){
        gameStatus.setYourIndex(newIndex);
    }
}

