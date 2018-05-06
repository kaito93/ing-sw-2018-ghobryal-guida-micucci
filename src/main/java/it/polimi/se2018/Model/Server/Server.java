package it.polimi.se2018.Model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TimerTask;

public class Server {

    ServerSocket socketServer;
    ArrayList<Socket> clients = new ArrayList<Socket>();
    boolean active = false;

    public Server(int port, int timer) {

        active = true;
        try {
            socketServer = new ServerSocket(port); //avvia il server sulla porta
            while (active) {
                System.out.print("Server avviato. In attesa di giocatori");
                Socket socket = socketServer.accept();  // rimani in attesa fino a quando si connette un giocatore
                System.out.print("Richiesta di connessione da parte di un giocatore");

                if (!active)
                    break;

                if (clients.isEmpty()) {

                    clients.add(socket); //aggiungi la connessione ricevuta all'elenco


                }


            }

        } catch (IOException e) {


        }
    }


    class TimerCount extends TimerTask {

        int numPlayers;
        int counter;
        int counterMax;

        public TimerCount(int timer) {
            counterMax=timer;
        }

        @Override
        public void run() {

            if (clients.size()==4){ // se si raggiunge il numero massimo di giocatori per una partita...

                this.cancel();
                new Lobby().start();
                System.out.print("Sono presenti 4 giocatori. Il gioco si sta avviando");
                clients=new ArrayList<Socket>();
            }
            else{
                if (counter==counterMax) { // se si esaurisce il tempo di attesa
                    this.cancel();
                    new Lobby().start();
                    System.out.print("Timer scaduto. Il gioco si sta avviando");
                    clients=new ArrayList<Socket>();
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
        int porta=0;
        int timer=0;
        // To Do: Caricamento da file di configurazione partita di porta e timer

        new Server(porta,timer);

    }
}