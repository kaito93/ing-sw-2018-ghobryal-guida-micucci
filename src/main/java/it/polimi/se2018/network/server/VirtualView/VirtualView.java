package it.polimi.se2018.network.server.VirtualView;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.message.MessageDisconnect;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class VirtualView extends Observable<MessageVC> {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private Controller controller;
    private ArrayList<ConnectionServer> connections = new ArrayList<>();
    private ArrayList<Player> playersActive = new ArrayList<>();
    private ArrayList<PlayerPlay> playersPlay = new ArrayList<>();
    private PlayerPlay currentPlayer;

    private ArrayList<Player> playersSuspend = new ArrayList<>();
    private ArrayList<ConnectionServer> connectionsSuspend = new ArrayList<>();
    private ArrayList<PlayerPlay> playerNotPlay = new ArrayList<>();
    private boolean terminate = false;


    public ArrayList<Player> setClients(ArrayList<ConnectionServer> connect) {
        for (int i = 0; i < connect.size(); i++) {
            try {
                connections.add(connect.get(i).clone());
            } catch (CloneNotSupportedException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        for (int i = 0; i < connections.size(); i++) { // per ogni connessione creata
            PlayerPlay player = new PlayerPlay(connections.get(i));// crea un thread per il giocatore
            playersPlay.add(player); // aggiungi il thread all'elenco
            playersActive.add(new Player(connections.get(i).getUsername())); // crea un giocatore e aggiungilo all'elenco dei giocatori attivi

        }
        setView();
        return playersActive;

    }

    public void start() {

        for (int i = 0; i < this.connections.size(); i++) { // per ogni giocatore
            try {
                connections.get(i).sendMap(controller.getGame().getMaps(), playersActive.get(i));
            } catch (NullPointerException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

        }
        LOGGER.log(Level.INFO, "Tutto è pronto! Si cominciaaaaaaaa");
    }

    public void startServer() {
        for (int i = 0; i < this.playersPlay.size(); i++)
            this.playersPlay.get(i).start(); // avvia i thread ascoltatori dei giocatori
    }

    public void startGame() {
        // invia le informazioni al singolo giocatore delle SUE caratteristiche
        for (int i = 0; i < playersActive.size(); i++) { // per ogni giocatore
            connections.get(i).sendPrivateInformation(playersActive.get(i).getCardPrivateObj());
        }
    }

    public void publicInformation(ArrayList<PublicObjectiveCard> publicCards) {
        // invia le informazioni a tutti i giocatori delle informazioni GENERALI della partita.

        ArrayList<ToolCard> tools = controller.getGame().getToolCards();
        for (int i = 0; i < this.connections.size(); i++) { // per ogni giocatore
            connections.get(i).sendPublicInformation(publicCards, tools);
        }
    }

    class PlayerPlay extends Thread {

        private ConnectionServer client;
        boolean connected;

        public PlayerPlay(ConnectionServer player) {
            this.client = player;
            this.connected = true;

        }

        @Override
        public void run() { // metodo sempre in esecuzione che controlla se il giocatore è ancora connesso
            while (connected) {
                try {
                    MessageVC message = (MessageVC) client.getInput().readUnshared();
                    if (message instanceof MessageDisconnect) {
                        connected = false;
                        LOGGER.log(Level.OFF, "Il player " + client.getUsername() + " non ha effettuato una mossa in tempo\n" +
                                "l'ho messo in sospensione");
                    } else
                        notifyObservers(message);
                } catch (IOException e) {
                    connected = false;
                    if (client.isConnected())
                        LOGGER.log(Level.OFF, "Il player " + client.getUsername() + " si è disconnesso. Non ho ricevuto nulla", e);
                    else
                        return;
                } catch (ClassNotFoundException e) {
                    connected = false;
                    LOGGER.log(Level.OFF, "Il player " + client.getUsername() + " si è disconnesso. Non manda dati corretti", e);

                }

            }
            reconn();
            for (int i = 0; i < playersPlay.size(); i++)
                playersPlay.get(i).closeThread();
            for (int i = 0; i < playerNotPlay.size(); i++)
                playerNotPlay.get(i).closeThread();

        }

        public void reconn() {
            Player temp = connectionLost();
            controller.updatePlayers(temp);
            if (currentPlayer == this) { // se toccava al giocatore sospeso
                controller.fakeMove();
            }


            if (!terminate) {
                String text = "Il giocatore " + client.getUsername() + " si è disconnesso. Il giocatore è stato sospeso.";

                for (int i = 0; i < connections.size(); i++) { // per ogni giocatore
                    connections.get(i).sendLostConnection(text,i);
                }

                // GESTIONE DELLA RICONNESSIONE

                this.client.tryReconnect();
                text = "Il giocatore " + client.getUsername() + " si è riconnesso. Tornerà in gioco dal prossimo round.";
                for (int i = 0; i < connections.size(); i++) { // per ogni giocatore
                    connections.get(i).sendGainConnection(text);
                }
               client.sendAcceptReconnection(
                        "Ti sei riconnesso. Ricomincerai a giocare al prossimo turno.",playersPlay.size());
                int ind2 = playerNotPlay.indexOf(this);

                playersPlay.add(this); // aggiungi il thread del giocatore tra quelli attivi
                playerNotPlay.remove(this); // rimuovi il thread del giocatore tra quelli sospesi
                playersActive.add(playersSuspend.get(ind2)); // aggiungi il giocatore all'elenco dei giocatori in gioco
                playersSuspend.remove(ind2); // rimuovi il giocatore riconnesso tra quelli in sospeso
                connections.add(connectionsSuspend.get(ind2)); //aggiungi la connessione del giocatore tra quelle attive
                connectionsSuspend.remove(ind2); // rimuovi la connessione del giocatore da quelle sospese
                this.run();// riavvio l'ascolto della view
            } else
                this.interrupt();

        }

        public Player connectionLost() {

            this.connected = false; // il giocatore non è connesso

            int index = connections.indexOf(this.client); // ricerca l'indice del giocatore
            playersSuspend.add(playersActive.get(index)); // aggiungi il giocatore all'elenco di giocatori sospesi
            Player temp = playersActive.get(index);
            playersActive.remove(index); // rimuovi il giocatore sospeso dai giocatori attivi
            connectionsSuspend.add(this.client); // aggiungi la connessione nell array delle connessioni sospese
            connections.remove(index); // rimuovi la connessione dalle connessioni attive
            playerNotPlay.add(this); // aggiungi questo thread all'elenco di thread riferiti a giocatori sospesi
            playersPlay.remove(this); // rimuovi questo thread all'elenco di thread riferiti a giocatori attivi
            return temp;
        }

        public void closeThread() {
            this.interrupt();
        }
    }


    // metodo che cerca l'username nelle connessioni
    public int searchUser(String user) {
        boolean trovato = false;
        int i = 0;
        while (!trovato) {
            if (connections.get(i).getUsername().equalsIgnoreCase(user))
                trovato = true;
            else
                i++;
        }
        return i;
    }

    // DA TESTARE
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendMessageTurn(ArrayList<Player> playersInRound, int turno, boolean posDice, boolean useTool) {
        connections.get(searchUser(playersInRound.get(turno).getName())).sendIsYourTurn(
                posDice,useTool);
    }

    public void sendMessageUpdate(int turno, Game model, String name) {
        // INVIA A TUTTI I GIOCATORI LE INFORMAZIONI DI TUTTI I GIOCATORI.
        ArrayList<Map> maps = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        ArrayList<Boolean> tools = new ArrayList<>();
        ArrayList<Integer> fav = new ArrayList<>();
        String message = "E' il turno di " + name;


        for (int i = 0; i < playersActive.size(); i++) {
            maps.add(playersActive.get(i).getMap());
            users.add(playersActive.get(i).getName());
            fav.add(playersActive.get(i).getFavSig());
        }
        for (int i = 0; i < model.getToolCards().size(); i++)
            tools.add(model.getToolCards().get(i).isUsed());

        for (int i = 0; i < playersActive.size(); i++)
            connections.get(i).sendUpdate(maps, users, message, tools, model.getRoundSchemeMap(), model.getStock(), fav);

    }

    public void createMessageCopper(String title, int player) {
        connections.get(player).manageCopper(title);
    }

    public void createMessageCork(String title, int player) {
        connections.get(player).manageCork(title);
    }

    public void createMessageEglomise(String title, int player) {
        connections.get(player).manageEglomise(title);
    }

    public void createMessageFluxBrush(String title, int player) {
        connections.get(player).manageFluxBrush(title);
    }

    public void createMessageFluxRemover(String title, int player) {
        connections.get(player).manageFluxRemover(title);
    }

    public void createMessageGlazing(String title) {
        controller.manageGlazing(title);
    }

    public void createMessageGrinding(String title, int player) {
        connections.get(player).manageGrinding(title);
    }

    public void createMessageGrozing(String title, int player) {
        connections.get(player).manageGrozing(title);
    }

    public void createMessageLathekin(String title, int player) {
        connections.get(player).manageLathekin(title);
    }

    public void createMessageLens(String title, int player) {
        connections.get(player).manageLens(title);
    }

    public void createMessageRunning(String title, int player) {
        connections.get(player).manageRunning(title);
    }

    public void createMessageTap(String title, int player) {
        connections.get(player).manageTap(title);
    }

    public void sendScorePlayers(ArrayList<Player> players) {
        for (int i = 0; i < playersActive.size(); i++)
            connections.get(i).sendFinalPlayers(players);
    }

    public void createMessageError(String error, int player) {
        connections.get(player).manageError(error);
    }

    public void manageFluxRemover2(Dice dice, String title, Player player) {
        connections.get(playersActive.indexOf(player)).manageFluxRemover2(dice, title);
    }

    // DA TESTARE
    public void setCurrentPlayer(Player currentPlayer) {
        for (int i = 0; i < playersActive.size(); i++) {
            if (currentPlayer == playersActive.get(i))
                this.currentPlayer = playersPlay.get(i);
        }
    }

    public void manageVictoryAbbandon() {
        connections.get(0).sendVictoryAbbandon();
        terminate = true;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void disconnect(){
        for (int i=0; i<this.connections.size();i++)
            connections.get(i).setConnected(false);
    }

    public Controller getController() {
        return controller;
    }

    public void setView(){
        for (ConnectionServer connection : connections) connection.setView(this);
    }
}
