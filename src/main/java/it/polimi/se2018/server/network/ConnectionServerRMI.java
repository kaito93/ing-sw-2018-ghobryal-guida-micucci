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

public class ConnectionServerRMI extends UnicastRemoteObject implements ConnectionServer,Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String REMOTEERROR = "Errore di connessione: {0} !";

    private ConnectionClient stub;

    private transient VirtualView vView=null;
    private String username;
    private boolean connected=true;
    private transient Server server;



    public ConnectionServerRMI(Server server, String username) throws RemoteException {
        super();
        this.server=server;
        this.username=username;
    }

    public void resetServer(){
        server=null;
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
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }


    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        // METODO PER INVIARE LA CARTA OBIETTIVO PRIVATA
        try {
            stub.viewPrivateCard(card);
        } catch (RemoteException e) {
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
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendLostConnection(String text,int index) {
        // METODO PER INVIARE UNA STRINGA DI COMUNICAZIONE AI CLIENT CHE UN GIOCATORE SI E' DISCONNESSO.
    }

    @Override
    public void tryReconnect() {
        // METODO CHE CONTROLLA SE IL GIOCATORE HA INVIATO UNA RICHIESTA PER RICONNETTERSI ALLA PARTITA
    }

    @Override
    public void sendFinalPlayers(List<Player> players) {
        // METODO CHE INVIA TUTTI I GIOCATORI A TUTTI I GIOCATORE
        List<Integer> finalScore = players.stream().map(Player::getScore).collect(Collectors.toList());
        try {
            stub.viewScore(finalScore);
        } catch (RemoteException e) {
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
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }

    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    @Override
    public void manageTap(String title) {

    }

    @Override
    public void manageCopper(String title) {

    }

    @Override
    public void manageCork(String title) {

    }

    @Override
    public void manageEglomise(String title) {

    }

    @Override
    public void manageFluxBrush(String title) {

    }

    @Override
    public void manageFluxRemover(String title) {

    }

    @Override
    public void manageGrinding(String title) {

    }

    @Override
    public void manageGrozing(String title) {

    }

    @Override
    public void manageLathekin(String title) {

    }

    @Override
    public void manageLens(String title) {

    }

    @Override
    public void manageRunning(String title) {

    }

    @Override
    public void manageError(String error) {

    }

    @Override
    public void manageFluxRemover2(Dice dice, String title) {

    }

    @Override
    public void sendVictoryAbbandon() {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void sendGainConnection(String text){

    }

    @Override
    public void sendAcceptReconnection(String text, int index) {

    }

    @Override
    public void send(Object message) {

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
            //da gestire
        }
        return null;
    }

    /**
     * method that set the status of network
     */
    public void setDisconnected() {
        this.connected = false;
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
    public void receiveMessage() {
        //usato solo da socket
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


}
