package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.exception.InvalidValueException;
import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageUpdate;
import it.polimi.se2018.shared.Logger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * abstract class that connect UI with network
 *
 * @author Samuele Guida
 */
public abstract class View implements Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    GameView gameStatus;
    protected ConnectionClient client;
    protected int time;
    protected transient TimerCount timer;
    protected boolean a = false;

    /**
     * class constructor
     *
     * @param time integer, time for a single choose
     */
    public View(int time) {
        gameStatus = new GameView();
        this.time = time;
    }

    /**
     * method that update the information about the username of this player. Ask the map to the player
     *
     * @param maps     list of maps
     * @param username the confirm of the username
     * @param names    the names of maps
     * @param fav      the difficult of maps
     * @return che matrix of cell choosed
     */
    public Cell[][] chooseMap(List<Cell[][]> maps, String username, List<String> names, List<Integer> fav) {
        gameStatus.setMyUsername(username);
        return maps.get(chooseSingleMap(maps, names, fav));
    }

    /**
     * abstract method that ask the map to the player
     *
     * @param maps  list of maps
     * @param names names of maps
     * @param fav   the difficult of maps
     * @return the position in the list of map that client choose
     */
    abstract int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav);

    /**
     * method that update information about public objective card and tool card
     *
     * @param titlePublic       list of titles of public objective card
     * @param descriptionPublic list of descriptions of public objective card
     * @param titleTools        list of title of tool card
     * @param descriptionTools  list of description of tool card
     * @param scores            list of scores of public objective card
     */
    public void setPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTools,
                                     List<String> descriptionTools, List<Integer> scores) {
        gameStatus.setTitlePublicObjective(titlePublic);
        gameStatus.setDescriptionPublicObjective(descriptionPublic);
        gameStatus.setTitleTools(titleTools);
        gameStatus.setDescriptionTools(descriptionTools);
        gameStatus.setScorePublicObjective(scores);

        addLog("Ho aggiornato le informazioni relative alle carte obiettivo pubbliche");

    }

    /**
     * method that update information about private objective card
     *
     * @param titlePrivate       list of titles of private objective card
     * @param descriptionPrivate list of description of private objective card
     */
    public void setPrivateInformation(String titlePrivate, String descriptionPrivate) {
        gameStatus.setTitlePrivateObjective(titlePrivate);
        gameStatus.setDescriptionPrivateObjective(descriptionPrivate);
        addLog("Ho aggiornato le informazioni relative alla tua carta obiettivo privata");
    }

    /**
     * method that update information about username of users, maps of all player, the use of tool cards, .
     * round scheme, stock and favors remaining
     *
     * @param users          list of username
     * @param cells          list of matrix of cells
     * @param useTools       list of boolean of tool card
     * @param roundSchemeMap round scheme map
     * @param stock          the stock
     * @param favors         list of boolean
     */
    public void updateUsers(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                            RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors, String username) {
        gameStatus.setUsers(users);
        gameStatus.setCells(cells);
        gameStatus.setUseTools(useTools);
        gameStatus.setRoundSchemeMap(roundSchemeMap);
        gameStatus.setStock(stock);
        gameStatus.setFavUser(favors);
        addLog("Ho aggiornato le informazioni relative alle carte schema di tutti i giocatori");
        if (!username.equalsIgnoreCase(gameStatus.getUsers().get(gameStatus.getYourIndex()))){
            printOtherStatus();
            printPublicStatus();
            printYourStatus();
        }
    }

    /**
     * method that update the options of step
     *
     * @param pos  manage a position of dice
     * @param tool manage an use of tool card
     */
    public void updateFavor(boolean pos, boolean tool) {

        gameStatus.setUseTool(tool);
        gameStatus.setPosDice(pos);

    }

    /**
     * method that set the connection with network
     *
     * @param client connection with the network
     */
    public void setClient(ConnectionClient client) {
        this.client = client;
    }

    /**
     * abstract method that print a string
     *
     * @param message the string printed
     */
    public abstract void addLog(String message);

    /**
     * method that manage the step of this player and start the timer
     */
    public void turn() {
        setTimer();
        myTurn();
        cancelTimer();
    }

    /**
     * method that start the timer
     */
    void setTimer() {
      timer = new TimerCount();
      timer.start();
    }

    /**
     * method that stop the timer
     */
    void cancelTimer() {
        timer.stopTimer();
    }

    /**
     * abstract method that manage the turn of this player
     */
    protected abstract void myTurn();

    /**
     * method that accept the message update
     *
     * @param message message received
     */
    public void accept(MessageUpdate message) {
        updateUsers(message.getUsers(), message.getCells(), message.getUseTools(), message.getRoundSchemeMap(),
                message.getStock(), message.getFavUsers(),message.getUsername());
        addLog(message.getMessage());
    }

    /**
     * abstract method that ask the username
     *
     * @return a string
     */
    public abstract String askNewUsername();

    /**
     * method that print an error and re ask a move
     *
     * @param message a string
     */
    public void manageError(String message) {
        addError(message);
        turn();
    }

    /**
     * abstract method that print an error
     *
     * @param message the string printed
     */
    public abstract void addError(String message);

    /**
     * abstract method that manage the tool cards "copper foil burnisher" and "Eglomise Brush"
     *
     * @return a list of object: Dice, RowDest, ColumnDest, rowMit, ColumnMit
     */
    public abstract List<Object> manageCE();

    /**
     * abstract method that manage the tool card "Flush Brush"
     *
     * @return a list of object: dice dicebefore, dice diceafter, int rowdest, int columndest
     */
    public abstract List<Object> managefluxBrush();

    /**
     * method that manage the first part of tool card "Flux Remover"
     *
     * @return a dice choose
     */
    public Dice managefluxRemove() {
        return manageGrozing1();
    }

    /**
     * abstract method that manage the second part of tool card "Flux Remover"
     *
     * @param dice dice choose
     * @return a list of object: dice row column
     */
    public abstract List<Object> manageFluxRemove2(Dice dice);

    /**
     * method that manage the tool card "Grinding Stone"
     *
     * @return a list of object: dice, row, column
     */
    public List<Object> manageGrinding() {
        setTimer();
        Dice dice = manageGrozing1();
        Dice diceBefore = null;
        try {
            diceBefore = dice.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        try {
            dice.setValue(7 - dice.getValue());
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        addLog("Il valore del dado scelto è " + dice.toString());
        List<Integer> obj2 = manageGrozing3();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        obj.add(diceBefore);
        cancelTimer();
        return obj;
    }

    /**
     * method that manage the tool card "Grozing Pliers"
     *
     * @return a list of object: dice, row,column
     */
    public List<Object> manageGrozing() {
        setTimer();
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
        cancelTimer();
        return obj;
    }

    /**
     * abstract method that manage the first part of tool card "Grozing pliers"
     *
     * @return the dice choose
     */
    public abstract Dice manageGrozing1();

    /**
     * abstract method that manage the second part of tool card "Grozing Pliers"
     *
     * @param minus the value-1
     * @param major the value+1
     * @return the value choose
     */
    public abstract int manageGrozing2(int minus, int major);

    /**
     * abstract method that manage the third part of tool card "Grozing Pliers"
     *
     * @return a list of integer: row, column
     */
    public abstract List<Integer> manageGrozing3();

    /**
     * abstract method that manage the tool card "Lathekin"
     *
     * @return a list of object: int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList dices
     */
    public abstract List<Object> manageLathekin();

    /**
     * abstract method that manage the tool card "Lens Cutter"
     *
     * @return a list of object: Dice dicStock2,Dice diceRound, int numberRound,row,column
     */
    public abstract List<Object> manageLens();

    /**
     * abstract method that manage the tool card "Tap Wheel"
     *
     * @return a list of object: Dice diceRound,  int row1, int column1, int row2, int column2,
     * Arraylist Dice (dice1, Dice dice2), posizione dado in round scheme
     */
    public abstract List<Object> manageTap();

    /**
     * abstract method that manage the tool card "Corkbacked Straightedge"
     *
     * @return a list of object: Dice, row, column
     */
    public abstract List<Object> manageCork();

    /**
     * internal class that manage the timer for a step
     */
    class TimerCount extends Thread {

        boolean valid;
        boolean terminated;
        int counter;

        /**
         * class constructor
         */
        TimerCount() {
            valid = true;
            terminated = true;
            counter=0;
        }

        /**
         * method that stop the timer
         */
        void stopTimer() {
            valid = false;
        }

        /**
         * method that manage the timer
         */
        @Override
        public void run() {
            try {
                Thread.sleep(100);
                addLog("Hai ancora "+ ((time/1000)-((time/4000)*counter)) + " secondi disponibili per effettuare la tua scelta");
                while (valid) {
                    Thread.sleep(time / 4);
                    counter++;
                    if (valid){
                        if (counter==4){
                            addError("Hai impiegato troppo tempo a scegliere una mossa.\n Sei stato disconnesso.");
                            this.stopTimer();
                            reconn();
                        }
                        else{
                            addLog("Hai ancora "+ ((time/1000)-((time/4000)*counter)) + " secondi disponibili per effettuare la tua scelta");
                        }

                    }
                }
            }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                Thread.currentThread().interrupt();
            }
        }

        /**
         * method that manage the disconnection and reconnection of the client
         */
        private void reconn(){
            boolean verit=false;
            try {
                client.sendDisconnect();
                while (!verit) {
                    String str = reconnect();
                    if (str != null) {
                        client.sendReconnect();
                        verit = true;
                    } else
                        verit = false;
                }
            }catch (RemoteException e){
                LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
            }
        }

    }

    /**
     * abstract method that manage the reconnection of a player
     *
     * @return a not null string
     */
    public abstract String reconnect();

    /**
     * method that return if the player is disconnected
     *
     * @return a boolean
     */
    public boolean isA() {
        return a;
    }

    /**
     * method that update your index
     *
     * @param newIndex an integer
     */
    public boolean updateIndex(int newIndex) {
        if (newIndex!=-1)
            gameStatus.setYourIndex(newIndex);
        return newIndex <= 4;
    }

    /**
     * abstract method that print the scores of all player at the end of the game
     *
     * @param scores list of scores
     */
    public abstract void seeScore(List<Integer> scores);

    public abstract void printYourStatus();

    public abstract void printPublicStatus();

    public abstract void printOtherStatus();
}

