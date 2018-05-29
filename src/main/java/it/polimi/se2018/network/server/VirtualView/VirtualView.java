package it.polimi.se2018.network.server.VirtualView;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestReconnect;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class VirtualView extends Observable<MessageVC> implements Observer<MessageMV> {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    SocketVirtualView socketView;
    RMIVirtualView RMIview;
    Controller controller;
    ArrayList<ConnectionServer> connections;
    ArrayList<Player> playersActive = new ArrayList<>();
    ArrayList<PlayerPlay> playersPlay = new ArrayList<>();
    PlayerPlay currentPlayer;

    ArrayList <Player> playersSuspend = new ArrayList<>();
    ArrayList <ConnectionServer> connectionsSuspend = new ArrayList<>();
    ArrayList<PlayerPlay> playerNotPlay = new ArrayList<>();




    public VirtualView (){
        //this.RMIview = new RMIVirtualView(controller); // creo la virtual view per le connessioni socket
       // this.socketView= new SocketVirtualView(controller); // creo la virtual view per le connessioni RMI
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
            MessageChooseMap message = new MessageChooseMap(); // prepara un messaggio da inviare per scegliere la carta schema
            for (int j=0; j<2;j++){ // sceglie 2 carte schema
                Map m = randomMap(maps);
                message.addMap(m); // aggiunge la mappa estratta al messaggio da inviare
                message.setPlayer(playersActive.get(i)); // invio alla view il giocatore proprietario
            }

            Message mess= new Message(Message.CVEVENT, message);
            connections.get(i).send(mess); // viene inviato il messaggio al giocatore per scegliere la carta


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
            MessageStart message = new MessageStart(); //crea un nuovo messaggio
            Message mex = new Message(Message.CVEVENT,message);
            message.setCard(playersActive.get(i).getCardPrivateObj());
            message.setMap(playersActive.get(i).getMap());
            message.setFavor(playersActive.get(i).getFavSig());
            connections.get(i).send(mex);// mando il messaggio
        }




    }

    public void publicInformation(ArrayList<PublicObjectiveCard> publicCards, ArrayList<ToolCard> tools){
        // invia le informazioni a tutti i giocatori delle informazioni GENERALI della partita.
        MessagePublicInformation messag = new MessagePublicInformation();
        Message mex= new Message(Message.CVEVENT,messag);
        messag.setPublicObjective(publicCards);
        messag.setToolCards(tools);
        sendBroadcast(mex);
    }

    public Map randomMap(ArrayList<Map> ma){
        Random random = new Random();
        int j = random.nextInt(ma.size()); // estrai un numero casuale tra tutte le mappe disponibili
        Map val= ma.get(j); // salvati la mappa
        ma.remove(j); // rimuovi la mappa dall array di mappe
        return val; // ritorna la mappa estratta
    }

    public ArrayList<Map> loadMaps() {
        // TO DO MIK: Carica le carte schema da file qui

        // CODICE PER TEST

        ArrayList<Map> maps = new ArrayList<>();
        for (int i=0; i<8; i++){
            try {Map map = new Map("ciao",1,1,1);
                maps.add(map);
            }
            catch (notValidMatrixException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }


        }
        return maps; // SOLO PER NON DARE ERRORE
    }


    class PlayerPlay extends Thread{

        ConnectionServer client;
        boolean connected=false;

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
            this.connected=false; // il giocatore non è connesso

            int index = connections.indexOf(this.client); // ricerca l'indice del giocatore
            playersSuspend.add(playersActive.get(index)); // aggiungi il giocatore all'elenco di giocatori sospesi
            controller.updatePlayers(playersActive.get(index));
            playersActive.remove(index); // rimuovi il giocatore sospeso dai giocatori attivi
            connectionsSuspend.add(this.client); // aggiungi la connessione nell array delle connessioni sospese
            connections.remove(index); // rimuovi la connessione dalle connessioni attive
            playerNotPlay.add(this); // aggiungi questo thread all'elenco di thread riferiti a giocatori sospesi
            playersPlay.remove(this); // rimuovi questo thread all'elenco di thread riferiti a giocatori attivi

            String text= "Il giocatore "+client.getUsername()+ " si è disconnesso. Il giocatore è stato sospeso.";
            Message message = new Message(Message.SYSTEMEVENT, text); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
            sendBroadcast(message);// spedisci il messaggio in broadcast

            if (currentPlayer==this){ // se toccava al giocatore sospeso
                // TO DO: bisognerà generare un fake-message vuoto per gli osservatori
                // Fake message di tipo risposta mossa.
            }

            // GESTIONE DELLA RICONNESSIONE [Ipotesi]

            boolean reconnect=false;
            while (!reconnect){ // fin quando il giocatore non si è riconnesso
                try{
                    MessageVC reconn = (MessageVC) client.getInput().readObject();
                    if (reconn instanceof RequestReconnect)
                        reconnect=true;
                    else
                        reconnect=false;
                } catch (IOException e) {
                    LOGGER.log(Level.OFF, "Il player " + client.getUsername()+" è ancora disconnesso. Non ho ricevuto nulla", e);

                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.OFF, "Il player " + client.getUsername()+" è disconnesso. Non manda dati corretti", e);

                }

            }
            int ind2= playerNotPlay.indexOf(this);
            playersPlay.add(this); // aggiungi il thread del giocatore tra quelli attivi
            playerNotPlay.remove(this); // rimuovi il thread del giocatore tra quelli sospesi
            playersActive.add(playersSuspend.get(ind2)); // aggiungi il giocatore all'elenco dei giocatori in gioco
            playersSuspend.remove(ind2); // rimuovi il giocatore riconnesso tra quelli in sospeso
            connections.add(connectionsSuspend.get(ind2)); //aggiungi la connessione del giocatore tra quelle attive
            connectionsSuspend.remove(ind2); // rimuovi la connessione del giocatore da quelle sospese
            this.run();// riavvio l'ascolto della view


        }
    }

    public synchronized void sendBroadcast(Message message){
        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            this.connections.get(i).send(message); // spedisci il messaggio
        }

    }
    class SocketVirtualView extends VirtualView{

    }

    class RMIVirtualView extends VirtualView{

    }


    @Override
    public void update(MessageMV event) {

    }

    // metodo che invia un messaggio al giocatore
    public void sendToPlayer(String user, MessageCV message){
        Message messa = new Message(Message.CVEVENT,message);
        connections.get(searchUser(user)).send(messa);
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
}
