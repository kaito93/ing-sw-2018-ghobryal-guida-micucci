package it.polimi.se2018.network.server;



import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.connection.ConnectionServerRMI;
import it.polimi.se2018.network.server.connection.ConnectionServerSocket;
import sun.misc.Request;

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
    Timer timer = new Timer();

    public Server(int port, int timer) {

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
                    System.out.println("Richiesta di connessione da parte di un giocatore");
                    if (clients.isEmpty()) {
                        ConnectionServer conness = new ConnectionServerSocket(socket, (RequestConnection) obj, outputSocket, inputSocket); // crea connessione
                        clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                        TimerCount count = new TimerCount(); //inizializza il timer
                        this.timer.schedule(count, 0, timer / 20); // fa partire il timer}
                    }
                    else{
                        ConnectionServer conness = new ConnectionServerSocket(socket, (RequestConnection) obj, outputSocket, inputSocket); // crea connessione
                        clients.add(conness); // aggiungi connessione all'elenco delle connessioni del giocatore
                    }

                }


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

    @Override
    public void Connetti(Remote client) {
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
                new Lobby().start();
                System.out.print("Sono presenti 4 giocatori. Il gioco si sta avviando");
                clients=new ArrayList<ConnectionServer>();
                System.out.print("Il server è pronto per accettare richieste per un'altra partita");
            }
            else{
                if (counter==20) { // se si esaurisce il tempo di attesa
                    this.cancel();
                    new Lobby().start();
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
        // To Do: Caricamento da file di configurazione partita di porta e timer

        new Server(porta,timer);

    }
}