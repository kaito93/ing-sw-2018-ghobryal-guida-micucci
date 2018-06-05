package it.polimi.se2018.network.server.VirtualView;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Deserializer.MapsDeserializer;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class VirtualView extends Observable<MessageVC> implements Observer<MessageMV> {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    Controller controller;
    ArrayList<ConnectionServer> connections;
    ArrayList<Player> playersActive = new ArrayList<>();
    ArrayList<PlayerPlay> playersPlay = new ArrayList<>();
    PlayerPlay currentPlayer;

    ArrayList <Player> playersSuspend = new ArrayList<>();
    ArrayList <ConnectionServer> connectionsSuspend = new ArrayList<>();
    ArrayList<PlayerPlay> playerNotPlay = new ArrayList<>();




    public VirtualView (){

    }

    public ArrayList<Player> setClients(ArrayList<ConnectionServer> connect){
        for (int i=0; i<connect.size(); i++){ // per ogni connessione creata
            PlayerPlay player = new PlayerPlay(connect.get(i));// crea un thread per il giocatore
            playersPlay.add(player); // aggiungi il thread all'elenco
            playersActive.add(new Player(connect.get(i).getUsername())); // crea un giocatore e aggiungilo all'elenco dei giocatori attivi

        }
        this.connections=connect;
        return playersActive;

    }

    public void start()  {

        // TO DO MIK: Carica carte schema
        ArrayList<Map> maps;
        maps = loadMaps();

        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            connections.get(i).sendMap(maps,playersActive.get(i));
        }
        LOGGER.log(Level.INFO,"Tutto è pronto! Si cominciaaaaaaaa");
    }

    public void startServer(){
        for (int i=0; i<this.playersPlay.size();i++)
            this.playersPlay.get(i).start(); // avvia i thread ascoltatori dei giocatori
    }

    public void startGame(){
        // invia le informazioni al singolo giocatore delle SUE caratteristiche
        for (int i=0; i<playersActive.size();i++){ // per ogni giocatore
            connections.get(i).sendPrivateInformation(playersActive.get(i).getCardPrivateObj());
        }
    }

    public void publicInformation(ArrayList<PublicObjectiveCard> publicCards){
        // invia le informazioni a tutti i giocatori delle informazioni GENERALI della partita.

        ArrayList<ToolCard> tools = controller.getGame().getToolCards();
        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            connections.get(i).sendPublicInformation(publicCards,tools);
        }
    }



    public ArrayList<Map> loadMaps() {
        // TO DO MIK: Carica le carte schema da file qui

        MapsDeserializer maps = new MapsDeserializer();
        return maps.totalDeserialize();
        // richiama l'arraylist


        // CODICE PER TEST
/*
        ArrayList<Map> maps = new ArrayList<>();
        for (int i=0; i<8; i++){
            try {Map map = new Map("ciao",1,3,3);
                maps.add(map);
            }
            catch (notValidMatrixException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }


        }*/
    }


    class PlayerPlay extends Thread{

        ConnectionServer client;
        boolean connected;

        public PlayerPlay (ConnectionServer player) {
            this.client=player;
            this.connected=true;

        }

        public boolean isConnected() {
            return connected;
        }

        @Override
        public void run() { // metodo sempre in esecuzione che controlla se il giocatore è ancora connesso
            boolean connect = true;
            while (connect){
                try{
                    MessageVC message = (MessageVC) client.getInput().readObject();
                    notifyObservers(message);
                } catch (IOException e) {
                    connect=false;
                    LOGGER.log(Level.OFF, "Il player " + client.getUsername()+" si è disconnesso. Non ho ricevuto nulla", e);

                } catch (ClassNotFoundException e) {
                    connect=false;
                    LOGGER.log(Level.OFF, "Il player " + client.getUsername()+" si è disconnesso. Non manda dati corretti", e);

                }
            }

            connectionLost();
            String text= "Il giocatore "+client.getUsername()+ " si è disconnesso. Il giocatore è stato sospeso.";

            for (int i=0; i<connections.size();i++){ // per ogni giocatore
              connections.get(i).sendLostConnection(text);
            }

            if (currentPlayer==this){ // se toccava al giocatore sospeso
                controller.fakeMove();
            }

            // GESTIONE DELLA RICONNESSIONE [Ipotesi]

            this.client.tryReconnect();

            int ind2= playerNotPlay.indexOf(this);
            playersPlay.add(this); // aggiungi il thread del giocatore tra quelli attivi
            playerNotPlay.remove(this); // rimuovi il thread del giocatore tra quelli sospesi
            playersActive.add(playersSuspend.get(ind2)); // aggiungi il giocatore all'elenco dei giocatori in gioco
            playersSuspend.remove(ind2); // rimuovi il giocatore riconnesso tra quelli in sospeso
            connections.add(connectionsSuspend.get(ind2)); //aggiungi la connessione del giocatore tra quelle attive
            connectionsSuspend.remove(ind2); // rimuovi la connessione del giocatore da quelle sospese
            this.run();// riavvio l'ascolto della view


        }

        public void connectionLost(){

            this.connected=false; // il giocatore non è connesso

            int index = connections.indexOf(this.client); // ricerca l'indice del giocatore
            playersSuspend.add(playersActive.get(index)); // aggiungi il giocatore all'elenco di giocatori sospesi
            controller.updatePlayers(playersActive.get(index));
            playersActive.remove(index); // rimuovi il giocatore sospeso dai giocatori attivi
            connectionsSuspend.add(this.client); // aggiungi la connessione nell array delle connessioni sospese
            connections.remove(index); // rimuovi la connessione dalle connessioni attive
            playerNotPlay.add(this); // aggiungi questo thread all'elenco di thread riferiti a giocatori sospesi
            playersPlay.remove(this); // rimuovi questo thread all'elenco di thread riferiti a giocatori attivi

        }
    }



   /* public synchronized void sendBroadcast(Message message){
        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            this.connections.get(i).send(message); // spedisci il messaggio
        }

    }*/



    @Override
    public void update(MessageMV event) {

    }

    // metodo che cerca l'username nelle connessioni
    public int searchUser(String user){
        boolean trovato=false;
        int i=0;
        while (!trovato){
            if (connections.get(i).getUsername().equalsIgnoreCase(user))
                trovato=true;
            else
                i++;
        }
        return i;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendMessageTurn(ArrayList<Player> playersInRound, int turno){
        connections.get(searchUser(playersInRound.get(turno).getName())).sendIsYourTurn(playersInRound.get(turno).getFavSig(),
                playersInRound.get(turno).getSetDice(),playersInRound.get(turno).getUseTools());
    }

    public void sendMessageUpdate (int turno, Game model, String name){
        // INVIA A TUTTI I GIOCATORI LE INFORMAZIONI DI TUTTI I GIOCATORI.
        ArrayList<Map> maps = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        ArrayList<Boolean> tools = new ArrayList<>();
        String message = "E' il turno di "+name;


        for (int i=0; i<playersActive.size();i++){
            maps.add(playersActive.get(i).getMap());
            users.add(playersActive.get(i).getName());
        }
        for (int i=0; i<model.getToolCards().size();i++)
            tools.add(model.getToolCards().get(i).isUsed());

        for (int i=0; i<playersActive.size(); i++)
            connections.get(i).sendUpdate(maps,users,message,tools,model.getRoundSchemeMap(),model.getStock());

    }

    public void createMessageCopper(String title, int player){
        connections.get(player).manageCopper(title);
    }
    public void createMessageCork(String title, int player){
        connections.get(player).manageCork(title);
            }
    public void createMessageEglomise(String title, int player){
        connections.get(player).manageEglomise(title);
    }
    public void createMessageFluxBrush(String title, int player){
        connections.get(player).manageFluxBrush(title);
    }
    public void createMessageFluxRemover(String title, int player){
        connections.get(player).manageFluxRemover(title);
    }

    public void createMessageGlazing(String title){
        String error="ciao";
        try {
            controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()),
                    null,controller.firstOrSecond(),0,controller.getGame().getStock(),
                    controller.getPlayersInRound().get(controller.getTurno()).getSetDice(),0,0,null,
                    null,null,0,error);
        } catch (notValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }
    public void createMessageGrinding(String title, int player){
        connections.get(player).manageGrinding(title);
    }
    public void createMessageGrozing(String title, int player){
        connections.get(player).manageGrozing(title);
    }
    public void createMessageLathekin(String title, int player){
        connections.get(player).manageLathekin(title);
    }
    public void createMessageLens(String title, int player){
        connections.get(player).manageLens(title);
    }
    public void createMessageRunning(String title, int player){
        connections.get(player).manageRunning(title);
    }
    public void createMessageTap(String title, int player){
        connections.get(player).manageTap(title);
    }

    public void sendScorePlayers(ArrayList<Player> players){
        for (int i=0; i<playersActive.size();i++)
            connections.get(i).sendFinalPlayers(players);
    }
}
