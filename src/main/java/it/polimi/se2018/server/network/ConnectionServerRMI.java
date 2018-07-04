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


    /**
     * Class Constructor
     * @param server a reference of the server
     * @param username the possessor of this connection
     * @throws RemoteException catches this exception if the server is not up
     */
    public ConnectionServerRMI(Server server, String username) throws RemoteException {
        super();
        this.server=server;
        this.username=username;
    }

    /**
     * sets the boolean value that indicates if the player is doing his move before the timer is out
     */
    public synchronized void setPlayerOnline() {
        this.playerOnline = false;
    }

    /**
     * method that sends the maps to be chosen from the player
     *
     * @param maps   an array list of maps
     * @param player a player
     */
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

    /**
     * method that sends the private card information of a player
     *
     * @param card private objective card
     */
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

    /**
     * method that sends the public information of the game
     *
     * @param cards array list of public objective cards
     * @param tools array list of tool cards
     */
    @Override
    public void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools){
        // METODO PER INVIARE I TITOLI E LE DESCRIZIONI DELLE CARTE OBIETTIVO PUBBLICHE E DEI TOOLS
        List<String> titlePublic = cards.stream().map(Card::getTitle).collect(Collectors.toList());
        List<String> descriptionPublic = cards.stream().map(Card::getDescription).collect(Collectors.toList());
        List<String> titleTool = tools.stream().map(Card::getTitle).collect(Collectors.toList());
        List<String> descriptionTool = tools.stream().map(Card::getDescription).collect(Collectors.toList());
        List<Integer> publicScore = cards.stream().map(PublicObjectiveCard::getScore).collect(Collectors.toList());

        try {
            stub.viewPublicInformation(titlePublic, descriptionPublic, titleTool, descriptionTool, publicScore);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that sends the final score to all players
     *
     * @param players all active players
     */
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

    /**
     * method that sends the information of a turn for a player
     *
     * @param dice boolean True if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else False
     */
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

    /**
     * method that sends an error to a player at the username choice
     */
    @Override
    public void sendErrorUser() {
        // METODO CHE INFORMA IL GIOCATORE CHE IL SUO USERNAME ERA GIA' STATO SCELTO IN PRECEDENZA... DEVE SCEGLIERNE
        // UN ALTRO
        try {
            stub.requestNewUsername();
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that sends update of the client with a new news
     *
     * @param maps           an array list of maps of all players in game
     * @param users          an array list of string of username of all players in game
     * @param tools          an array list of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock          a matrix of dices
     * @param favors         an array list of integer
     */
    @Override
    public void sendUpdate(List<Map> maps, List<String> users, String message, List<Boolean> tools,
                           RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors) {
        // METODO CHE INVIA LE MATRICI DEI GIOCATORI, LA LISTA DEI GIOCATORI AGGIORNATA [Vedesi disconnessioni]
        // IL MESSAGGIO DA MOSTRARE A SCHERMO DI CHI TOCCA, ARRAYLIST DI BOOLEANI CHE SI RIFERISCONO ALLE CARTE TOOL,
        // LO SCHEMA DEI ROUND E LA RISERVA e l'elenco dei punti favore rimanenti ai giocatori
        List<Cell[][]> cells = maps.stream().map(Map::getCells).collect(Collectors.toList());
        try {
            stub.receiveUpdate(users, cells, tools, roundSchemeMap, stock, favors,message);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }

    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    /**
     * method that manages the transmission for the tool card "Tap Wheel"
     *
     * @param title the title of this card
     */
    @Override
    public void manageTap(String title) {
        try {
            stub.tapWheel(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Tap Wheel"
     * @param title title of the card
     * @param roundSchemeDice a chosen dice from the round scheme
     * @param dicesToMove the set of chosen dices of the map to be moved
     * @param row1Mit the current row position of the first dice on the map
     * @param row2Mit the current row position of the second dice on the map
     * @param column1Mit the current column position of the first dice on the map
     * @param column2Mit the current column position of the second dice on the map
     * @param row1Dest the row coordinate of the first dice where to be positioned on the map
     * @param row2Dest the row coordinate of the second dice where to be positioned on the map
     * @param column1Dest the column coordinate of the first dice where to be positioned on the map
     * @param column2Dest the column coordinate of the second dice where to be positioned on the map
     * @param roundSchemeDicePos the position of the chosen dice on the round scheme
     */
    public void tapSet(String title, Dice roundSchemeDice, List<Dice> dicesToMove, int row1Mit, int row2Mit,
                       int column1Mit, int column2Mit, int row1Dest, int row2Dest, int column1Dest,
                       int column2Dest , int roundSchemeDicePos){
        vView.getController().manageTap(title, row1Mit, row2Mit, column1Mit, column2Mit, roundSchemeDice, row1Dest,
                column1Dest,dicesToMove, row2Dest, column2Dest, roundSchemeDicePos);
    }

    /**
     * method that manages the transmission for the tool card "Running Pliers"
     *
     * @param title the title of this card
     */
    @Override
    public void manageCopper(String title) {
        try {
            stub.copperFoil(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Running Pliers"
     * @param title title of the card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param rowMit the current row position of the dice on the map
     * @param columnMit the current column position of the dice on the map
     */
    public void coppperSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit){
        vView.getController().manageCopper(title, dice, rowDest, columnDest, rowMit, columnMit);
    }

    /**
     * method that manages the transmission for the tool card "Cork-backed Straightedge"
     *
     * @param title the title of this card
     */
    @Override
    public void manageCork(String title) {
        try {
            stub.corkbacked(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Cork-backed Straightedge"
     * @param title the title of this card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     */
    public void corkSet(String title, Dice dice, int rowDest, int columnDest){
        vView.getController().manageCork(title, dice, rowDest, columnDest);
    }

    /**
     * method that manages the transmission for the tool card "Eglomise Brush"
     *
     * @param title the title of this card
     */
    @Override
    public void manageEglomise(String title) {
        try {
            stub.eglomise(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Eglomise Brush"
     * @param title the title of this card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param rowMit the current row position of the dice on the map
     * @param columnMit the current column position of the dice on the map
     */
    public void eglomiseSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit){
        vView.getController().manageEglomise(title, dice, rowDest, columnDest, rowMit, columnMit);
    }

    /**
     * method that manages the transmission for the tool card "Flux Brush"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxBrush(String title) {
        try {
            stub.fluxBrush(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Flux Brush"
     * @param title the title of this card
     * @param dice the chosen dice after the re-roll
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param diceBefore the dice chosen before the re-roll
     */
    public void fluxBrushSet(String title, Dice dice, int rowDest, int columnDest, Dice diceBefore){
        vView.getController().manageFluxBrush(title, dice, rowDest, columnDest, diceBefore);
    }

    /**
     * method that manages the first transmission for the tool card "Flux Remover"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover(String title) {
        try {
            vView.getController().manageFluxRemoverExtractDice(title, stub.fluxRemover());
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that manages the second transmission for the tool card "Flux Remover"
     *
     * @param title the title of this card
     */
    @Override
    public void manageFluxRemover2(Dice dice, String title) {
        try {
            stub.fluxRemover2(title, dice);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Flux Remover"
     * @param title the title of this card
     * @param dice the chosen dice after the re-roll
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     */
    public void fluxRemoverSet(String title, Dice dice, int row, int column){
        vView.getController().manageFluxRemover(title, dice, row, column);
    }

    /**
     * method that manages the transmission for the tool card "Grinding Stone"
     *
     * @param title the title of this card
     */
    @Override
    public void manageGrinding(String title) {
        try {
            stub.grinding(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Grinding Stone"
     * @param title the title of this card
     * @param dice the chosen dice after the usage of this card
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @param diceBefore the chosen dice in its original state
     */
    public void grindingSet(String title, Dice dice, int row, int column, Dice diceBefore){
        vView.getController().manageGrinding(title, dice, row, column, diceBefore);
    }

    /**
     * method that manages the transmission for the tool card "Grozing Pliers"
     *
     * @param title the title of this card
     */
    @Override
    public void manageGrozing(String title) {
        try {
            stub.grozing(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Grozing Pliers"
     * @param title the title of this card
     * @param dice the chosen dice after the usage of this card
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param colDest the column coordinate of the dice where to be positioned on the map
     */
    public void grozingSet(String title, Dice dice, int rowDest, int colDest){
        vView.getController().manageGrozing(title, dice, rowDest, colDest);
    }

    /**
     * method that manages the transmission for the tool card "Lathekin"
     *
     * @param title the title of this card
     */
    @Override
    public void manageLathekin(String title) {
        try {
            stub.lathekin(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Lathekin"
     * @param title title of the card
     * @param dicesToMove the set of chosen dices of the map to be moved
     * @param row1Mit the current row position of the first dice on the map
     * @param row2Mit the current row position of the second dice on the map
     * @param col1Mit the current column position of the first dice on the map
     * @param col2Mit the current column position of the second dice on the map
     * @param row1Dest the row coordinate of the first dice where to be positioned on the map
     * @param row2Dest the row coordinate of the second dice where to be positioned on the map
     * @param col1Dest the column coordinate of the first dice where to be positioned on the map
     * @param col2Dest the column coordinate of the second dice where to be positioned on the map
     */
    public void lathekinSet(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest,
                            int col1Dest, List<Dice> dicesToMove, int row2Dest, int col2Dest){
        vView.getController().manageLathekin(title, row1Mit, row2Mit, col1Mit, col2Mit, row1Dest, col1Dest,
                dicesToMove, row2Dest, col2Dest);
    }

    /**
     * method that manages the transmission for the tool card "Lens Cutter"
     *
     * @param title the title of this card
     */
    @Override
    public void manageLens(String title) {
        try {
            stub.lens(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Lathekin"
     * @param title title of the card
     * @param diceStock a chosen dice from the stock
     * @param numberRound the number of this round
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @param diceRound a round scheme dice
     */
    public void lensSet(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound){
        vView.getController().manageLens(title, diceStock, numberRound, row, column, diceRound);
    }

    /**
     * method that manages the transmission for the tool card "Running Pliers"
     *
     * @param title the title of this card
     */
    @Override
    public void manageRunning(String title) {
        try {
            stub.running(title);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages the usage of the tool card "Running Pliers"
     * @param title title of the card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param colDest the column coordinate of the dice where to be positioned on the map
     */
    public void runningSet(String title, Dice dice, int rowDest, int colDest){
        vView.getController().manageRunning(title, dice, rowDest, colDest);
    }

    /**
     * method that sends a generic error to the client
     *
     * @param error a string
     */
    @Override
    public void manageError(String error) {
        try {
            stub.handleError(error);
        } catch (RemoteException e) {
            connected=false;
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * manages dice position on the map
     * @param dice a chosen dice
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     */
    public void posDice(Dice dice, int row, int column){
        vView.getController().setPos(dice, row, column);
    }

    /**
     * manages the usage of the tool card
     * @param title the title of the tool card
     */
    public void useTool(String title){
        vView.getController().useTools(title);
    }

    /**
     * method that manages an empty move
     */
    public void passTurn(){
        vView.getController().fakeMove();
    }

    /**
     * only used by socket
     * the connection between RMI client and server is implicit. The connection closes after a short idle period of
     * time automatically.
     */
    @Override
    public void closeConnection() {
        //non esiste tale per rmi perché la connessione in tal caso si chiude automaticamente dopo un breve
        //periodo di time_out settato dal jvm il che è invisibile al programmatore
    }

    /**
     * only used by socket
     */
    @Override
    public void send(Object message) {
        //non si implementa per RMI
    }

    /**
     * sets the possessor of this server connection
     * @param username the possessor of this connection in a string format
     */
    @Override
    public void setUsername(String username) {
        this.username=username;
    }

    /**
     * gets the possessor of this server connection
     * @return the possessor of this connection in a string format
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * only used by socket
     */
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
            ((ConnectionServerRMI) temp).setvView(vView);
            server.rebind((ConnectionServerRMI) temp);
            stub.setSkeleton(temp);
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

    /**
     * method that sends a communication of victory with disconnection of others players
     */
    @Override
    public void sendVictoryAbbandon() {
        sendLostConnection("Hai vinto per abbandono degli altri giocatori. Congratulazioni (?)", 5);
    }

    /**
     * method that sends news: a player has been reconnected
     *
     * @param text a string of text
     */
    @Override
    public void sendGainConnection(String text){
        sendLostConnection(text, -1);
    }

    /**
     * method that sends a confirm of reconnection
     *
     * @param text  a string
     * @param index an integer for manage the new index of this player
     */
    @Override
    public void sendAcceptReconnection(String text, int index) {
        sendLostConnection(text, index);
    }

    /**
     * method that sends a warning to other players that a player has left the game for disconnection
     *
     * @param text  a string
     * @param index new index of player in the array
     */
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

    /**
     * method that listens request of reconnection of a disconnected player
     */
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
     * method that sets an instance of Virtual View
     * @param vView a Virtual View
     */
    public void setvView(VirtualView vView) {
        this.vView = vView;
    }

    /**
     * sets the stub and the possessor of the connection
     * @param stub the proxy of the client on server
     * @param username the possessor of this connection
     */
    public void setClientRMI(ConnectionClient stub, String username){
        server.connect(stub,username);
    }

    /**
     * controls if the player is connected or not
     * @return a boolean, true if the player is connected, else false
     */
    @Override
    public synchronized boolean receiveMessage() {
        return connected&&playerOnline;
    }

    /**
     * sets the stub
     * @param stub the proxy of the client on server
     */
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

    /**
     * sets if the player is connected or not
     * @param connected a boolean, true if the player is connected, else false
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
        this.playerOnline = connected;
    }
}
