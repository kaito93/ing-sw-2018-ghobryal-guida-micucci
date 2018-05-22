package it.polimi.se2018.network.server;



import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.connection.ConnectionServerRMI;
import it.polimi.se2018.network.server.connection.ConnectionServerSocket;
import it.polimi.se2018.network.server.message.MessageNewUsername;

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

public class Server implements ServerRMI{

    ServerSocket socketServer;
    ServerRMI skeleton;
    ArrayList<ConnectionServer> clients = new ArrayList<ConnectionServer>();
    boolean active = false;
    int port;
    int time;
    Timer timer = new Timer();
    ArrayList<Lobby> lobbies = new ArrayList<Lobby>();

    public static final int MVEvent=0;
    public static final int CVEvent=1;
    public static final int SystemMessage=2;

    public Server(int port, int timer) {
        this.time = timer;
        this.port = port;
    }

    public void start(){
        try{
            startRMI(); // prova ad avviare il server RMI

        }
        catch (RemoteException e) {
            System.out.print("Errore oggetto remoto RMI"); // se non ce la fa, segnalalo

        }
        active = true;
        try {
            socketServer = new ServerSocket(port); //avvia il server sulla porta
            System.out.println("server socket avviato.");
            while (active) {
                System.out.println("In attesa di giocatori");
                Socket socket = socketServer.accept();  // rimani in attesa fino a quando si connette un giocatore
                System.out.println("Ho ricevuto una richiesta");

                ObjectOutputStream outputSocket = new ObjectOutputStream(socket.getOutputStream()); // crea un oggetto che legga la richiesta socket
                ObjectInputStream inputSocket = new ObjectInputStream(socket.getInputStream()); // crea un oggetto che legga la richiesta socket
                Object obj=inputSocket.readObject();
                if (obj instanceof RequestConnection) {
                    System.out.println("Richiesta di connessione da parte di un giocatore. Username richiesto: "+((RequestConnection) obj).getUser());
                    ConnectionServer conness = new ConnectionServerSocket(socket, (RequestConnection) obj, outputSocket, inputSocket); // crea connessione
                    if (clients.isEmpty()) {
                        clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                        TimerCount count = new TimerCount(); //inizializza il timer
                        this.timer.schedule(count, 0, time / 20); // fa partire il timer}
                    }
                    else{
                        if (checkUsername(((RequestConnection) obj).getUser())==true) { // Se l'username scelto dal giocatore non è già stato registrato da un altro giocatore
                            clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                        }
                        else{// se l'username è già preso
                            MessageNewUsername message = new MessageNewUsername(); // crea un nuovo messaggio
                            Message mess = new Message(SystemMessage,message); // creo un messaggio di sistema
                            conness.send(mess); //invia il messaggio. [nota bene: non si salva conness nell'array]
                        }

                    }

                }
                else
                    System.out.println("Tipo di messaggio ricevuto sconosciuto");


                if (!active)
                    break;
                }

        } catch (IOException e) {

            System.out.print("Errore I/O Socket");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            System.out.print("Impossibile avviare il server RMI");
        }

    }

    public boolean checkUsername(String userNewPlayer){

        for (int i=0; i<this.clients.size();i++) // Per ogni client già registrato
        {
            if (this.clients.get(i).getUsername().equalsIgnoreCase(userNewPlayer)) // controlla se l'username scelto dal nuovo giocatore è già
                {
                    System.out.println("L'username scelto dal giocatore è già stato richiesto");
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
        int counterMax;

        public TimerCount() {

        }

        @Override
        public void run() {

            if (clients.size()==4){ // se si raggiunge il numero massimo di giocatori per una partita...

                this.cancel();
                new Lobby(clients).start();
                System.out.print("Sono presenti 4 giocatori. Il gioco si sta avviando");
                clients=new ArrayList<ConnectionServer>();
                System.out.print("Il server è pronto per accettare richieste per un'altra partita");
            }
            else{
                if (counter==20) { // se si esaurisce il tempo di attesa
                    this.cancel();
                    Lobby newLobby = new Lobby(clients);
                    lobbies.add(newLobby);
                    newLobby.start();
                    System.out.print("Timer scaduto. Il gioco si sta avviando");
                    clients=new ArrayList<ConnectionServer>();
                    System.out.print("Il server è pronto per accettare richieste per un'altra partita");
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
        int porta=9736;
        int timer=10000;
        Server server;
        // To Do: Caricamento da file di configurazione partita di porta e timer

        server=new Server(porta,timer);
        server.start();

    }
}