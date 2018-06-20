package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.exception.InvalidValueException;
import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageUpdate;
import it.polimi.se2018.shared.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public abstract class View {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    GameView gameStatus;
    protected ConnectionClient client;
    protected Timer timer = new Timer();
    protected int time;
    protected boolean a = false;

    public View(int time) {
        gameStatus = new GameView();
        this.time = time;
    }

    public abstract void startView();

    public Cell[][] chooseMap(List<Cell[][]> maps, String username, List<String> names, List<Integer> fav) {
        gameStatus.setMyUsername(username);
        return maps.get(chooseSingleMap(maps, names, fav));
    }

    abstract int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav);


    public void setPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTools,
                                     List<String> descriptionTools, List<Integer> scores) {
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

    private void updateUsers(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                             RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors) {
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

    public void turn() {
        TimerCount count = new TimerCount(); //inizializza il timer
        this.timer.schedule(count, 0, time / 20);
        myTurn();
    }

    public abstract void myTurn();

    public void accept(MessageUpdate message) {
        updateUsers(message.getUsers(), message.getCells(), message.getUseTools(), message.getRoundSchemeMap(),
                message.getStock(), message.getFavUsers());
        addLog(message.getMessage());
    }

    public abstract String askNewUsername();

    public void manageError(String message) {
        addError(message);
        myTurn();
    }

    public abstract void addError(String message);

    public abstract List<Object> manageCE();

    public abstract List<Object> managefluxBrush();

    public Dice managefluxRemove() {
        return manageGrozing1();
    }

    public abstract List<Object> manageFluxRemove2(Dice dice);

    public List<Object> manageGrinding() {
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
                default:
                    break;
            }
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        addLog("Il valore del dado scelto è " +dice.toString());
        List<Integer> obj2 = manageGrozing3();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        obj.add(diceBefore);
        return obj;
    }


    public List<Object> manageGrozing() {
        int minus = 0;
        int major = 0;
        Dice dice = manageGrozing1();
        if (dice.getValue() > 1 && dice.getValue() < 6) {
            major = dice.getValue() + 1;
            minus = dice.getValue() - 1;
        } else if (dice.getValue() == 1) {
            major = dice.getValue() + 1;
        } else {
            minus = dice.getValue() - 1;
        }
        try {
            dice.setValue(manageGrozing2(minus, major));
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        List<Integer> obj2 = manageGrozing3();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    public abstract Dice manageGrozing1();

    public abstract int manageGrozing2(int minus, int major);

    public abstract List<Integer> manageGrozing3();

    public abstract List<Object> manageLathekin();

    public abstract List<Object> manageLens();

    public abstract List<Object> manageTap();

    public abstract List<Object> manageCork();

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

