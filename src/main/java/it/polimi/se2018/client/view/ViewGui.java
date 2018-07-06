package it.polimi.se2018.client.view;

import it.polimi.se2018.client.LauncherClient;
import it.polimi.se2018.client.view.ViewGuiPack.FxmlOpener;
import it.polimi.se2018.client.view.ViewGuiPack.pathFXML;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.Cell;

import java.util.List;
import java.util.logging.Level;

import javafx.stage.Stage;
import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;

import javax.jws.WebParam;

public class ViewGui extends View {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    private static Stage stageOfGame;
    pathFXML paths = new pathFXML();
    Object lock = new Object();

    String connect;

    public ViewGui(int timer, LauncherClient launch){
        super(timer,launch);
        ModelFX.getInstance().getLogin().setPort(launch.getPort());
        ModelFX.getInstance().getLogin().setiP(launch.getIp());
    }

    @Override
    public void addLog(String message) {

    }

    @Override
    public void myTurn() {

/*      PosDice: client.sendPosDice(gameStatus.getStock().get(chooseDice), chooseColumn, chooseRow);

        chooseDice= posizione nell'arraylist del dado scelto
        chooseColumn = colonna di destinazione
        chooseRow = riga di destinazione

        UseTool: client.sendUseTool(gameStatus.getTitleTools().get(chooseTool));
        chooseTool = posizione nell'arraylist della carta utensile

        passMove: client.sendPassMove();*/


    }
    public int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav){
        ModelFX.getInstance().getChoice().setUpMap(maps, names, fav);
        FxmlOpener.getInstance().openFX("/SceltaMappe.fxml");
        try {
            synchronized (lock){
                lock.wait(100);
            }
        } catch (InterruptedException | IllegalMonitorStateException e) {
            // va bene
        }
        int index=-1;
        boolean cond=true;
        while(cond){
            index=ModelFX.getInstance().getChoice().getIndexOfDefinitiveMap();
            if (index!=-1)
                cond=false;
        }
        System.out.println(index);
        return ModelFX.getInstance().getChoice().getIndexOfDefinitiveMap();
    }


    @Override
    public String askNewUsername() {
        ModelFX.getInstance().getWrong().setNewUsername(null);
        FxmlOpener.getInstance().openFX("/wrongUsername.fxml");
        try {
            synchronized (lock){
                lock.wait(100);
            }
        } catch (InterruptedException | IllegalMonitorStateException e) {
            // va bene
        }
        String username=null;
        boolean cond=true;
        while(cond){
            try{
                try {
                    synchronized (lock){
                        lock.wait(100);
                    }
                } catch (InterruptedException | IllegalMonitorStateException e) {
                    // va bene
                }
                username=ModelFX.getInstance().getWrong().getNewUsername();
                if (!username.isEmpty())
                    cond = false;
            }
            catch (NullPointerException e){
                // waiita
            }
        }

        return username;
    }

    @Override
    public void addError(String message) {
        // mostra una alert box contenente l'errore dentro la stringa, non nella schermata di log, proprio un alerto box gigantesco
    }

    @Override
    public List<Object> manageCE() {
        // Dice scelto, RowDestinazione, ColumnDestinazione, rowMittente, ColumnMittente
        setTimer();
        // fai cose
        // prima di ritornare si chiude il timer
        cancelTimer();
        // solo dopo aver chiuso il timer si ritorna
        return null;
    }

    @Override
    public Dice managefluxRemove() {
        //Dice dice da riserva
        setTimer();
        // fai cose
        cancelTimer();
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

    public List<Object> manageLathekin() {
        //int row1mit,int column1mit, row1dest, column1dest, int row2mit, int column2mit, row2dest, column2dest, ArrayList<Dice> dices
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public List<Object> manageLens() {
        //Dice dicStock2,Dice diceRound, int numberRound,row,column
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public List<Object> manageTap() {
        //Dice diceRound,  int row1Mit, int column1Mit, int row1Dest, int column1Dest, int row2Mit, int column2Mit, int row2Dest, int column2Dest,
        // Arraylist Dice (dice1, Dice dice2), posizione dado in roundscheme
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public List<Object> manageCork() {
        // Dice, row, column
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public List<Object> managefluxBrush() {
        // dice dicebefore, dice diceafter, int rowdest, int columndest
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public List<Object> manageFluxRemove2(Dice dice) {
        // dice row column
        setTimer();
        // fai cose
        cancelTimer();
        return null;
    }

    @Override
    public String reconnect() {
        // chiedi all'utente di dare un qualsiasi segnale di vita, se preme un pulsante crea una stringa non nulla e ritornala
        return null;
    }

    @Override
    public void seeScore(List<Integer> scores) {
        // devi mostrare i punteggi di tutti i giocatori.
        // la partita termina con questo metodo
    }

    @Override
    public void printYourStatus() {
        FxmlOpener.getInstance().openFX("/gamingBoard.fxml");
        ModelFX.getInstance().getGbp().setDefinitiveMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
        ModelFX.getInstance().getGbp().setFav(gameStatus.getFavUser().get(gameStatus.getYourIndex()));
        ModelFX.getInstance().getGbp().setPrivateCardTitle(gameStatus.getTitlePrivateObjective());
        ModelFX.getInstance().getGbp().setPrivateCardDescription(gameStatus.getDescriptionPrivateObjective());
        ModelFX.getInstance().getGbp().setTitlePub(gameStatus.getTitlePublicObjective());
        ModelFX.getInstance().getGbp().setDescriptionPub(gameStatus.getDescriptionPublicObjective());
        ModelFX.getInstance().getGbp().setScorePub(gameStatus.getScorePublicObjective());
        ModelFX.getInstance().getGbp().setTitleTools(gameStatus.getTitleTools());
        ModelFX.getInstance().getGbp().setDescriptionTool(gameStatus.getDescriptionTools());
        ModelFX.getInstance().getGbp().setUsageTool(gameStatus.getUseTools());
        ModelFX.getInstance().getGbp().setStock(gameStatus.getStock());
        ModelFX.getInstance().getGbp().setSchemeRound(gameStatus.getRoundSchemeMap());
    }

    @Override
    public void printPublicStatus() {

    }

    @Override
    public void printOtherStatus() {

    }

    @Override
    public void startView() {
        launchLoginMain();
    }

    private void launchLoginMain(){
        FxmlOpener.getInstance().openFX("/LoginPageFXML.fxml");
        String user=null;
        String ipad=null;
        int ported=0;
        String choice=null;
        try {
            synchronized (lock){
                lock.wait(100);
            }
        } catch (InterruptedException | IllegalMonitorStateException e) {
            // va bene
        }
        boolean cond=true;
        while(cond){
            try {
                synchronized (lock){
                    lock.wait(100);
                }
            } catch (InterruptedException | IllegalMonitorStateException e) {
                // va bene
            }
            try{
                if(!ModelFX.getInstance().getLogin().getUsername().isEmpty())
                {
                    user = ModelFX.getInstance().getLogin().getUsername();
                    ipad = ModelFX.getInstance().getLogin().getiP();
                    ported = ModelFX.getInstance().getLogin().getPort();
                    choice=  ModelFX.getInstance().getLogin().getConnectivity();
                    if(!choice.isEmpty()) {
                        cond = false;
                    }

                }
            }
            catch(NullPointerException e){
                // ok
            }

        }
        launche.setConnection(ported, ipad, user, this, choice);
    }

    public static Stage getStage(){
        return stageOfGame;
    }
}
