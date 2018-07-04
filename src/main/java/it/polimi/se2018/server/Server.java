package it.polimi.se2018.server;


import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.server.network.*;
import it.polimi.se2018.shared.message_socket.server_to_client.Message;
import it.polimi.se2018.shared.message_socket.client_to_server.RequestConnection;
import it.polimi.se2018.shared.message_socket.RequestReconnect;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageFinalGame;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.server.deserializer.server_deserializer.ServerDeserialize;
import it.polimi.se2018.shared.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * Class that manage the main Server.
 *
 * @author Samuele Guida
 */
public class Server implements Remote {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String REMOTEERROR = "Errore di connessione: {0} !";

    private ServerSocket socketServer;
    private ConnectionServerRMI connectionServerRMI;
    private Registry registry;
    private ArrayList<ConnectionServer> clients = new ArrayList<>();
    private int port;
    private int time;
    private Timer timer = new Timer();
    private ArrayList<Lobby> lobbies = new ArrayList<>();
    private int portRMI;

    /**
     * constructor class
     *
     * @param port  an integer used for the port where the server can listen new request
     * @param timer an integer used for to wait a new player
     * @param portRMI an integer used for the port where the server can listen new request RMI
     */
    public Server(int port, int timer, int portRMI) {
        this.time = timer;
        this.port = port;
        this.portRMI = portRMI;
    }

    /**
     * method that create the server and listen socket requests
     */
    public void start() {
        boolean active;

        startRMI(); // prova ad avviare il server RMI

        try {
            socketServer = new ServerSocket(port); //avvia il server sulla porta
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        try {
            LOGGER.log(Level.INFO, "Server socket avviato");
            active = true;
            while (active) {
                LOGGER.log(Level.INFO, "In attesa di giocatori");

                Socket socket = socketServer.accept();  // rimani in attesa fino a quando si connette un giocatore
                LOGGER.log(Level.INFO, "Ho ricevuto una richiesta");

                ObjectOutputStream outputSocket = new ObjectOutputStream(socket.getOutputStream()); // crea un oggetto che legga la richiesta socket
                ObjectInputStream inputSocket = new ObjectInputStream(socket.getInputStream()); // crea un oggetto che legga la richiesta socket
                Object obj = inputSocket.readObject();
                if (obj instanceof RequestConnection) {
                    LOGGER.log(Level.FINE, "Richiesta di connessione da parte di un giocatore. Username richiesto: {0}", ((RequestConnection) obj).getUser());

                    ConnectionServer conness = new ConnectionServerSocket(socket, ((RequestConnection) obj).getUser(), outputSocket, inputSocket); // crea connessione
                    addConnection(conness,((RequestConnection) obj).getUser());
                } else if (obj instanceof RequestReconnect) {
                    MessageFinalGame message = new MessageFinalGame();
                    message.setMessage("Hai richiesto di connetterti ad una partita terminata.");
                    Message mex = new Message(Message.SYSTEMEVENT, message);
                    outputSocket.writeUnshared(mex);
                    outputSocket.reset();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * method that start the RMI Server
     */
    private void startRMI() {
        try {
            registry = createRegistry();
            connectionServerRMI = new ConnectionServerRMI(this, "");
            rebind(connectionServerRMI);
        }catch (RemoteException | NullPointerException e) {
            LOGGER.log(Level.INFO, "Oggetto già esportato", e);
        }
        LOGGER.log(Level.INFO, "Server RMI avviato");
    }

    private Registry createRegistry(){
        try {
            registry = LocateRegistry.createRegistry(portRMI);
        }catch (RemoteException e1){
            LOGGER.log(Level.INFO, "Registro già presente");
            while (true){
                try {
                    registry = LocateRegistry.getRegistry(portRMI);
                    break;
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "Porta " + portRMI+ " occupata", e);
                }
            }
        }
        return registry;
    }

    public void rebind(ConnectionServerRMI connectionServerRMI){
        try{
            registry.rebind("//localhost/ServerConnectionReference", connectionServerRMI);
        } catch (NullPointerException e){
            LOGGER.log(Level.INFO, "Registro non avviato correttamente", e);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }


    public void connect(ConnectionClient stub, String user) {
        try {
            connectionServerRMI.setStub(stub);
            addConnection(connectionServerRMI, user);
            connectionServerRMI = new ConnectionServerRMI(this, "");
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    /**
     * method that check if an username is alreasy taken
     *
     * @param userNewPlayer a string used for a new username
     * @return false if is not possible for the new user use this username, else true.
     */
    private boolean checkUsername(String userNewPlayer) {

        try {
            for (ConnectionServer client : this.clients) {
                if (client.getUsername().equalsIgnoreCase(userNewPlayer)) // controlla se l'username scelto dal nuovo giocatore è già
                {
                    LOGGER.log(Level.WARNING, "L'username scelto dal giocatore è già stato richiesto");

                    return false;
                }                                     // preso da un altro giocatore. In questo caso torna false
            }
        }catch (RemoteException e){
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
        return true; // Se dopo aver controllato tutti i giocatori non è stato trovato l'username scelto, allora l'username è disponibile

    }

    /**
     * Internal class used for manage the timer to wait a new player
     */
    class TimerCount extends TimerTask {

        int numPlayers;
        int counter;


        /**
         * method that check if a game can starts.
         */
        @Override
        public void run() {

            if (clients.size() == 4) { // se si raggiunge il numero massimo di giocatori per una partita...

                this.cancel();
                Lobby newLobby = new Lobby(clients, lobbies.size());
                lobbies.add(newLobby);
                newLobby.start();
                LOGGER.log(Level.INFO, "Sono presenti 4 giocatori. Il gioco si sta avviando");

                clients = new ArrayList<>();
                LOGGER.log(Level.INFO, "Il server è pronto per accettare richieste per un'altra partita");
            } else {
                if (counter == 20) { // se si esaurisce il tempo di attesa
                    this.cancel();
                    Lobby newLobby = new Lobby(clients, lobbies.size());
                    lobbies.add(newLobby);
                    newLobby.start();
                    LOGGER.log(Level.WARNING, "Timer scaduto. Il gioco si sta avviando");

                    clients = new ArrayList<>();
                    LOGGER.log(Level.INFO, "Il server è pronto per accettare richieste per un'altra partita");


                }

                if (clients.size() > 1)
                    counter++; // se c'è almeno un client connesso aumenta il timer
                else
                    counter = 0; //altrimenti lo resetta (come da specifiche)

                if (clients.size() > numPlayers) { // se si connette un nuovo giocatore, resetta il contatore e aggiorna il numero totale di giocatori

                    counter = 0;
                    numPlayers = clients.size();

                }


            }
        }
    }

    /**
     * method that check the unique of username and add the client in the arraylist of connectionsServer and start the timer.
     * @param conness the connection with the client
     * @param user username that client have choose
     */
    private void addConnection(ConnectionServer conness, String user) throws RemoteException {
        String text=  user+" - Ho aggiunto il giocatore all'elenco dei giocatore in attesa";
        if (clients.isEmpty()) {
            clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
            LOGGER.log(Level.INFO, text);
            TimerCount count = new TimerCount(); //inizializza il timer
            timer.schedule(count, 0, time / 20); // fa partire il timer
        } else {
            if (checkUsername(user)) { // Se l'username scelto dal giocatore non è già stato registrato da un altro giocatore
                clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                LOGGER.log(Level.INFO, text);
            } else {// se l'username è già preso
                conness.sendErrorUser();
            }
        }
    }

    /**
     * the main of this project server side
     *
     * @param args a matrix of strings
     */
    public static void main(String[] args) {
        PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/server/json_server/Pathname.json");
        path.deserializing();
        ServerDeserialize serv = new ServerDeserialize(path.getPathFromType("server"));
        serv.deserializing();

        int porta = serv.getSs().getPort();
        int timer = serv.getSs().getTime();
        int portaRMI = serv.getSs().getPortRMI();

        // QUI PUOI CHIUDERE IL BUFFER READER

        Server server;
        server = new Server(porta, timer, portaRMI);

        server.start();


    }
}