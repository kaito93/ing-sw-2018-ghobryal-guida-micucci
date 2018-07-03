package it.polimi.se2018.server.network;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.server.Server;
import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * class ConnectionServerRMI
 * @author Anton Ghobryal
 */

public class ConnectionServerRMI extends UnicastRemoteObject implements ConnectionServer,Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String REMOTEERROR = "Errore di connessione: {0} !";

    private ConnectionClient stub;

    private transient VirtualView vView=null;
    private String username;
    private boolean connected=true;
    private boolean playerOnline=true;
    private transient Server server;



    public ConnectionServerRMI(Server server, String username) throws RemoteException {
        super();
        this.server=server;
        this.username=username;
    }

    public void setPlayerOnline(boolean playerOnline) {
        this.playerOnline = playerOnline;
    }

    public boolean isPlayerOnline(){
        return playerOnline;
    }

    @Override
    public void sendMapConn(List<Map> maps, Player player) {
        // METODO PER INVIARE LA SCELTA DELLE MAPPE AI GIOCATORI
        try {
            List<Cell[][]> cells = maps.stream().map(Map::getCells).collect(Collectors.toList());
            List<String> names = maps.stream().map(Map::getName).collect(Collectors.toList());
            List<Integer> fav = maps.stream().map(Map::getDifficultyLevel).collect(Collectors.toList());
            vView.getController().map(player.getName(), maps.get(stub.receiveMapConn(cells, names, fav)));
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }


    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        // METODO PER INVIARE LA CARTA OBIETTIVO PRIVATA
        try {
            stub.viewPrivateCard(card);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools){
        // METODO PER INVIARE I TITOLI E LE DESCRIZIONI DELLE CARTE OBIETTIVO PUBBLICHE E DEI TOOLS
        List<String> titlePublic = cards.stream().map(Card::getTitle).collect(Collectors.toList());
        List<String> descriptionPublic = cards.stream().map(Card::getDescription).collect(Collectors.toList());
        List<String> titleTool = tools.stream().map(Card::getTitle).collect(Collectors.toList());
        List<String> descriptionTool = tools.stream().map(Card::getTitle).collect(Collectors.toList());
        List<Integer> publicScore = cards.stream().map(PublicObjectiveCard::getScore).collect(Collectors.toList());

        try {
            stub.viewPublicInformation(titlePublic, descriptionPublic, titleTool, descriptionTool, publicScore);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendFinalPlayers(List<Player> players) {
        // METODO CHE INVIA TUTTI I GIOCATORI A TUTTI I GIOCATORE
        List<Integer> finalScore = players.stream().map(Player::getScore).collect(Collectors.toList());
        try {
            stub.viewScore(finalScore);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendIsYourTurn(boolean dice, boolean tool) {
        // METODO CHE INVIA ALL'UTENTE I BOOLEANI SE PUO' FARE O MENO UNA DETERMINATA
        // MOSSA. INOLTRE IL GIOCATORE COMINCIA A GESTIRE IL PROPRIO TURNO
        try {
            stub.isTurn(dice, tool);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendErrorUser() {
        // METODO CHE INFORMA IL GIOCATORE CHE IL SUO USERNAME ERA GIA' STATO SCELTO IN PRECEDENZA... DEVE SCEGLIERNE
        // UN ALTRO
        try {
            stub.requestNewUsernameRMI();
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }


    @Override
    public void sendUpdate(List<Map> maps, List<String> users, String message, List<Boolean> tools,
                           RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors) {
        // METODO CHE INVIA LE MATRICI DEI GIOCATORI, LA LISTA DEI GIOCATORI AGGIORNATA [Vedesi disconnessioni]
        // IL MESSAGGIO DA MOSTRARE A SCHERMO DI CHI TOCCA, ARRAYLIST DI BOOLEANI CHE SI RIFERISCONO ALLE CARTE TOOL,
        // LO SCHEMA DEI ROUND E LA RISERVA e l'elenco dei punti favore rimanenti ai giocatori
        List<Cell[][]> cells = maps.stream().map(Map::getCells).collect(Collectors.toList());
        try {
            stub.receiveUpdate(users, cells, tools, roundSchemeMap, stock, favors);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }

    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    @Override
    public void manageTap(String title) {
        try {
            stub.tapWheel(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void tapSet(String title, Dice roundSchemeDice, List<Dice> dicesToMove, int row1Mit, int row2Mit,
                       int column1Mit, int column2Mit, int row1Dest, int row2Dest, int column1Dest,
                       int column2Dest , int roundSchemeDicePos){
        vView.getController().manageTap(title, row1Mit, row2Mit, column1Mit, column2Mit, roundSchemeDice, row1Dest,
                column1Dest,dicesToMove, row2Dest, column2Dest, roundSchemeDicePos);
    }

    @Override
    public void manageCopper(String title) {
        try {
            stub.copperFoil(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void coppperSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit){
        vView.getController().manageCopper(title, dice, rowDest, columnDest, rowMit, columnMit);
    }

    @Override
    public void manageCork(String title) {
        try {
            stub.corkbacked(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void corkSet(String title, Dice dice, int rowDest, int columnDest){
        vView.getController().manageCork(title, dice, rowDest, columnDest);
    }

    @Override
    public void manageEglomise(String title) {
        try {
            stub.eglomise(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void eglomiseSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit){
        vView.getController().manageEglomise(title, dice, rowDest, columnDest, rowMit, columnMit);
    }

    @Override
    public void manageFluxBrush(String title) {
        try {
            stub.fluxBrush(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void fluxBrushSet(String title, Dice dice, int rowDest, int cloumnDest, Dice diceBefore){
        vView.getController().manageFluxBrush(title, dice, rowDest, cloumnDest, diceBefore);
    }

    @Override
    public void manageFluxRemover(String title) {
        try {
            vView.getController().manageFluxRemoverExtractDice(title, stub.fluxRemover());
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void manageFluxRemover2(Dice dice, String title) {
        try {
            stub.fluxRemover2(title, dice);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void fluxRemoverSet(String title, Dice dice, int row, int column){
        vView.getController().manageFluxRemover(title, dice, row, column);
    }

    @Override
    public void manageGrinding(String title) {
        try {
            stub.grinding(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void grindingSet(String title, Dice dice, int row, int column, Dice diceBefore){
        vView.getController().manageGrinding(title, dice, row, column, diceBefore);
    }

    @Override
    public void manageGrozing(String title) {
        try {
            stub.grozing(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void grozingSet(String title, Dice dice, int rowDest, int colDest){
        vView.getController().manageGrozing(title, dice, rowDest, colDest);
    }

    @Override
    public void manageLathekin(String title) {
        try {
            stub.lathekin(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void lathekinSet(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest,
                            int col1Dest, List<Dice> dicesToMove, int row2Dest, int col2Dest){
        vView.getController().manageLathekin(title, row1Mit, row2Mit, col1Mit, col2Mit, row1Dest, col1Dest,
                dicesToMove, row2Dest, col2Dest);
    }

    @Override
    public void manageLens(String title) {
        try {
            stub.lens(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void lensSet(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound){
        vView.getController().manageLens(title, diceStock, numberRound, row, column, diceRound);
    }

    @Override
    public void manageRunning(String title) {
        try {
            stub.running(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void runningSet(String title, Dice dice, int rowDest, int colDest){
        vView.getController().manageRunning(title, dice, rowDest, colDest);
    }

    @Override
    public void manageError(String error) {
        try {
            stub.handleError(error);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void posDice(Dice dice, int row, int column){
        vView.getController().setPos(dice, row, column);
    }

    public void useTool(String title){
        vView.getController().useTools(title);
    }

    public void passTurn(){
        vView.getController().fakeMove();
    }

    @Override
    public void closeConnection() {
        //non esiste tale per rmi perché la connessione in tal caso si chiude automaticamente dopo un breve
        //periodo di time_out settato dal jvm il che è invisibile al programmatore
    }

    @Override
    public void send(Object message) {
        //non si implementa per RMI
    }

    @Override
    public void setUsername(String username) {
        this.username=username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public ObjectInputStream getInput() {
        return null;
    }

    public void sendMap(Player player) {
        ArrayList<Map> mapsToPlayer= new ArrayList<>();
        for (int j=0; j<4;j++){ // sceglie 4 carte schema
            mapsToPlayer.add(vView.getController().getGame().randomMap()); // aggiunge la mappa estratta al messaggio da inviare
        }
        sendMapConn(mapsToPlayer,player);
    }
    /**
     * method to cloneObj a network
     * @return the cloned network
     */
    @Override
    public ConnectionServer cloneObj() {
        try {
            ConnectionServer temp = new ConnectionServerRMI(server, stub.getUsername());
            ((ConnectionServerRMI) temp).setStub(stub);
            return temp;
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
        return null;
    }

    /**
     * method that set the status of network
     */
    public void setDisconnected() {
        connected = false;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void sendVictoryAbbandon() {
        sendLostConnection("Hai vinto per abbandono degli altri giocatori. Congratulazioni (?)", -1);
    }

    @Override
    public void sendGainConnection(String text){
        sendLostConnection(text, -1);
    }

    @Override
    public void sendAcceptReconnection(String text, int index) {
        sendLostConnection(text, index);
    }

    @Override
    public void sendLostConnection(String text,int index) {
        // METODO PER INVIARE UNA STRINGA DI COMUNICAZIONE AI CLIENT CHE UN GIOCATORE SI E' DISCONNESSO.
        try {
            stub.receiveLostConnection(text, index);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void tryReconnect() {
        // METODO CHE CONTROLLA SE IL GIOCATORE HA INVIATO UNA RICHIESTA PER RICONNETTERSI ALLA PARTITA
        try {
            stub.run();
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }

    }


    /**
     * method that set an instance of Virtual View
     * @param vView a Virtual View
     */
    public void setvView(VirtualView vView) {
        this.vView = vView;
    }

    public void setClientRMI(ConnectionClient stub, String username){
        server.connect(stub,username);
    }

    @Override
    public boolean receiveMessage() {
        return connected;
    }

    public void setStub(ConnectionClient stub) {
        this.stub = stub;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
