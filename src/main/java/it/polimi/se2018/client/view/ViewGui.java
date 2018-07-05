package it.polimi.se2018.client.view;

import it.polimi.se2018.client.LauncherClient;
import it.polimi.se2018.client.view.ViewGuiPack.FxmlOpener;
import it.polimi.se2018.client.view.ViewGuiPack.pathFXML;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.Cell;

import java.util.List;

import javafx.application.Platform;
import javafx.stage.Stage;

import static it.polimi.se2018.client.view.ViewGuiPack.MapChoiceScene.assignMap;
import static it.polimi.se2018.client.view.ViewGuiPack.MapChoiceScene.getChosenMap;
import static it.polimi.se2018.client.view.ViewGuiPack.MapChoiceScene.toChoiceMapStage;
import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import static javafx.application.Platform.isFxApplicationThread;

public class ViewGui extends View {

    private static Stage stageOfGame;
    pathFXML paths = new pathFXML();
    private LauncherClient launcher;

    public ViewGui(int timer, LauncherClient launch){
        super(timer);
        launcher=launch;
        ModelFX.getInstance().getLogin().setPort(launcher.getPort());
        ModelFX.getInstance().getLogin().setiP(launcher.getIp());
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

    public ViewGui(int timer){
        super(timer);
    }

    public int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav){
        assignMap(maps, names, fav);
        FxmlOpener.getInstance().openFX("/FXML/SceltaMappe.fxml");
        return getChosenMap();
    }


    @Override
    public String askNewUsername() {
        System.out.println("ciao popolo");
        FxmlOpener.getInstance().openFX("/FXML/wrongUsername.fxml");
        return ModelFX.getInstance().getWrong().getNewUsername();
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

    public void launchLoginMain(){
        FxmlOpener.getInstance().openFX("/FXML/LoginPageFXML.fxml");
        boolean cond=true;
        while(cond){
            try{
                if(!ModelFX.getInstance().getLogin().getUsername().isEmpty())
                {
                    String user = ModelFX.getInstance().getLogin().getUsername();
                    String ipad = ModelFX.getInstance().getLogin().getiP();
                    int ported = ModelFX.getInstance().getLogin().getPort();
                    String conn = ModelFX.getInstance().getLogin().getConnectivity();
                    System.out.println(conn);
                    cond=false;
                    launcher.setConnection(ported, ipad, user, this, conn);

                }
            }
            catch(NullPointerException e){
                // waitta
            }

        }

    }

    public static Stage getStage(){
        return stageOfGame;
    }
}
