package it.polimi.se2018.network.server;



import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.client.message.RequestReconnect;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.connection.ConnectionServerRMI;
import it.polimi.se2018.network.server.connection.ConnectionServerSocket;
import it.polimi.se2018.network.server.message.MessageFinalGame;
import it.polimi.se2018.network.server.message.MessageNewUsername;
import it.polimi.se2018.network.server.message.MessagePlayerDisconnect;
import it.polimi.se2018.util.Deserializer.PathDeserializer;
import it.polimi.se2018.util.Deserializer.ServerDeserialize;
import it.polimi.se2018.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class Server implements ServerRMI{

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    ServerSocket socketServer;
    ServerRMI skeleton;
    ArrayList<ConnectionServer> clients = new ArrayList<>();
    boolean active = false;
    int port;
    int time;
    Timer timer = new Timer();
    ArrayList<Lobby> lobbies = new ArrayList<>();

    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMMESSAGE=2;

    public Server(int port, int timer) {
        this.time = timer;
        this.port = port;
    }

    public void start(){
        try{
            startRMI(); // prova ad avviare il server RMI

        }
        catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Errore oggetto remoto RMI", e);

        }
        try {
            socketServer = new ServerSocket(port); //avvia il server sulla porta
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        try {
            LOGGER.log(Level.INFO,"Server socket avviato");
            active=true;
            while (active) {
                LOGGER.log(Level.INFO,"In attesa di giocatori");

                Socket socket = socketServer.accept();  // rimani in attesa fino a quando si connette un giocatore
                LOGGER.log(Level.INFO,"Ho ricevuto una richiesta");

                ObjectOutputStream outputSocket = new ObjectOutputStream(socket.getOutputStream()); // crea un oggetto che legga la richiesta socket
                ObjectInputStream inputSocket = new ObjectInputStream(socket.getInputStream()); // crea un oggetto che legga la richiesta socket
                Object obj=inputSocket.readObject();
                if (obj instanceof RequestConnection) {
                    LOGGER.log(Level.FINE,"Richiesta di connessione da parte di un giocatore. Username richiesto: "+((RequestConnection) obj).getUser());

                    ConnectionServer conness = new ConnectionServerSocket(socket, (RequestConnection) obj, outputSocket, inputSocket); // crea connessione
                    if (clients.isEmpty()) {
                        clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                        TimerCount count = new TimerCount(); //inizializza il timer
                        this.timer.schedule(count, 0, time / 20); // fa partire il timer
                    }
                    else{
                        boolean a=true;
                        if (checkUsername(((RequestConnection) obj).getUser())==a) { // Se l'username scelto dal giocatore non è già stato registrato da un altro giocatore
                            clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                        }
                        else{// se l'username è già preso
                            conness.sendErrorUser();
                        }

                    }

                }

                else if (obj instanceof RequestReconnect){
                    MessageFinalGame message= new MessageFinalGame();
                    message.setMessage("Hai richiesto di connetterti ad una partita terminata.");
                    Message mex = new Message(Message.SYSTEMEVENT,message);
                    outputSocket.writeUnshared(mex);
                    outputSocket.reset();
                }
                else
                    LOGGER.log(Level.WARNING,"Tipo di messaggio ricevuto sconosciuto");




                if (!active)
                    break;
                }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore I/O Socket", e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }

    private void startRMI() throws RemoteException {
        LocateRegistry.createRegistry(1099);
        skeleton = (ServerRMI) UnicastRemoteObject.exportObject(this, 0);
        try {
            System.out.println("Server RMI avviato");
            Naming.rebind("Server", skeleton);
        }
        catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "Impossibile avviare il server RMI", e);
        }

    }

    public boolean checkUsername(String userNewPlayer){

        for (int i=0; i<this.clients.size();i++) // Per ogni client già registrato
        {
            if (this.clients.get(i).getUsername().equalsIgnoreCase(userNewPlayer)) // controlla se l'username scelto dal nuovo giocatore è già
                {
                    LOGGER.log(Level.WARNING,"L'username scelto dal giocatore è già stato richiesto");

                    return false;}                                     // preso da un altro giocatore. In questo caso torna false
        }
        return true; // Se dopo aver controllato tutti i giocatori non è stato trovato l'username scelto, allora l'username è disponibile

    }


    public void connect(Remote client) {
        ConnectionServer connection = new ConnectionServerRMI();

    }

    class TimerCount extends TimerTask {

        int numPlayers;
        int counter;


        @Override
        public void run() {

            if (clients.size()==4){ // se si raggiunge il numero massimo di giocatori per una partita...

                this.cancel();
                Lobby newLobby= new Lobby(clients,lobbies.size());
                lobbies.add(newLobby);
                newLobby.start();
                LOGGER.log(Level.INFO,"Sono presenti 4 giocatori. Il gioco si sta avviando");

                clients=new ArrayList<>();
                LOGGER.log(Level.INFO,"Il server è pronto per accettare richieste per un'altra partita");
            }
            else{
                if (counter==20) { // se si esaurisce il tempo di attesa
                    this.cancel();
                    Lobby newLobby = new Lobby(clients,lobbies.size());
                    lobbies.add(newLobby);
                    newLobby.start();
                    LOGGER.log(Level.WARNING,"Timer scaduto. Il gioco si sta avviando");

                    clients=new ArrayList<>();
                    LOGGER.log(Level.INFO,"Il server è pronto per accettare richieste per un'altra partita");


                }

                if (clients.size()>1)
                    counter++; // se c'è almeno un client connesso aumenta il timer
                else
                    counter=0; //altrimenti lo resetta (come da specifiche)

                if(clients.size()>numPlayers){ // se si connette un nuovo giocatore, resetta il contatore e aggiorna il numero totale di giocatori

                    counter=0;
                    numPlayers=clients.size();

                }


            }
        }
    }

    public static void main (String[] args) {
        PathDeserializer path = new PathDeserializer();
        path.Deserializing();
        ServerDeserialize serv= new ServerDeserialize(path.getPathFromType("server"));
        serv.Deserializing();

        int porta=serv.getSs().getPort();
        int timer=serv.getSs().getTime();

        // QUI PUOI CHIUDERE IL BUFFER READER

        Server server;
        server=new Server(porta,timer);

        server.start();

    }
}