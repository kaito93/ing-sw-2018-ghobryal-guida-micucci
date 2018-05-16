package it.polimi.se2018.network.server.VirtualView;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestReconnect;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.message.MessageMV;
import it.polimi.se2018.network.server.message.MessageStart;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;
import org.omg.CORBA.Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class VirtualView extends Observable<MessageVC> implements Observer<MessageMV> {

    SocketVirtualView socketView;
    RMIVirtualView RMIview;
    ArrayList<ConnectionServer> connections;
    ArrayList<Player> playersActive = new ArrayList<Player>();
    ArrayList<PlayerPlay> playersPlay = new ArrayList<PlayerPlay>();
    PlayerPlay currentPlayer;

    ArrayList <Player> playersSuspend = new ArrayList<Player>();
    ArrayList <ConnectionServer> connectionsSuspend = new ArrayList<ConnectionServer>();
    ArrayList<PlayerPlay> playerNotPlay = new ArrayList<PlayerPlay>();

    public static final int MVEvent=0;
    public static final int CVEvent=1;
    public static final int SystemMessage=2;


    public VirtualView (){
        //this.RMIview = new RMIVirtualView(); // creo la virtual view per le connessioni socket
       // this.socketView= new SocketVirtualView(); // creo la virtual view per le connessioni RMI
    }

    public void setClients(ArrayList<ConnectionServer> connect){
        for (int i=0; i<connect.size(); i++){ // per ogni connessione creata
            PlayerPlay player = new PlayerPlay(connect.get(i));// crea un thread per il giocatore
            playersPlay.add(player); // aggiungi il thread all'elenco
            playersActive.add(new Player(connect.get(i).getUsername())); // crea un giocatore e aggiungilo all'elenco dei giocatori attivi

        }
        this.connections=connect;

    }

    public void start() {

        // TO DO MIK: Carica carte schema
        ArrayList<Map> maps = new ArrayList<Map>();
        maps = loadMaps();
        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            MessageStart message = new MessageStart(); // prepara un messaggio da inviare per scegliere la carta schema
            for (int j=0; j<2;j++){ // sceglie 2 carte schema
                Map m = randomMap(maps);
                message.addMap(m); // aggiunge la mappa estratta al messaggio da inviare
            }

            Message mess= new Message(CVEvent, message);
            connections.get(i).send(mess); // viene inviato il messaggio al giocatore per scegliere la carta
        }



    }

    public void startServer(){
        for (int i=0; i<this.playersPlay.size();i++)
            this.playersPlay.get(i).start(); // avvia i thread ascoltatori dei giocatori
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

        ArrayList<Map> maps = new ArrayList<Map>();
        for (int i=0; i<8; i++){
            try {Map map = new Map("ciao",1,1,1);
                maps.add(map);
            }
            catch (notValidMatrixException e) {
                e.printStackTrace();
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
                    System.out.println("Il player si è disconnesso. Non ho ricevuto nulla");
                } catch (ClassNotFoundException e) {
                    connect=false;
                    System.out.println("Il player si è disconnesso. Non manda dati corretti");
                }
            }
            this.connected=false; // il giocatore non è connesso

            int index = connections.indexOf(this.client); // ricerca l'indice del giocatore
            playersSuspend.add(playersActive.get(index)); // aggiungi il giocatore all'elenco di giocatori sospesi
            playersActive.remove(index); // rimuovi il giocatore sospeso dai giocatori attivi
            connectionsSuspend.add(this.client); // aggiungi la connessione nell array delle connessioni sospese
            connections.remove(index); // rimuovi la connessione dalle connessioni attive
            playerNotPlay.add(this); // aggiungi questo thread all'elenco di thread riferiti a giocatori sospesi
            playersPlay.remove(this); // rimuovi questo thread all'elenco di thread riferiti a giocatori attivi

            String text= "Il giocatore "+client.getUsername()+ " si è disconnesso. Il giocatore è stato sospeso.";
            Message message = new Message(SystemMessage, text); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
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
                    System.out.println("Il player è ancora disconnesso. Non ho ricevuto nulla");
                } catch (ClassNotFoundException e) {

                    System.out.println("Il player è disconnesso. Non manda dati corretti");
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

    private synchronized void sendBroadcast(Message message){
        for (int i=0; i<this.connections.size();i++){ // per ogni giocatore
            this.connections.get(i).send(message); // spedisci il messaggio
        }

    }
    class SocketVirtualView extends VirtualView{}

    class RMIVirtualView extends VirtualView{}


    @Override
    public void update(MessageMV event) {

    }


}
