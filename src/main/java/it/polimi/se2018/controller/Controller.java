package it.polimi.se2018.controller;

import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;

import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.message.MessageFinalScore;
import it.polimi.se2018.util.Logger;
import it.polimi.se2018.util.Observer;


import java.util.ArrayList;
import java.util.logging.Level;

public class Controller implements Observer<MessageVC> {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private static final boolean A=true;
    Boolean b=false;
    Game game;
    int move=0;
    VirtualView view;
    ArrayList<Player> players;
    boolean setDice = false;
    boolean useTools = false;
    int turno;
    ArrayList<Player> playersInRound = new ArrayList<>();


    public void update(MessageVC message) {
        message.accept(this);

    }

    public Controller (VirtualView view, ArrayList<Player> players)  {
        this.game =new Game();
        this.view=view;
        this.players=players;
        view.addObservers(this);
        view.start();

    }

    public void visit(ResponseMap message){
        int index=searchUser(message.getUsername());
        if (index>=0)
        {
            this.players.get(index).setMap(message.getMapChoose());
            this.players.get(index).setFavorSig();
        }
        else
            LOGGER.log(Level.WARNING, "E' stato passato un giocatore errato");
    }

    public int searchUser(String user){
        for (int i=0; i<this.players.size();i++){
            if (players.get(i).getName().equalsIgnoreCase(user))
                return i;
        }
        return -1;
    }

    public void startGame(){
        game.setPrivateObjectiveCard(players); // chiama il metodo per settare le carte obiettivo privato
        view.startGame();
        view.publicInformation(game.getPublicObjCard());
        game();
    }

    public Game getGame() {
        return game;
    }

    public void updatePlayers(Player players){
        // TO DO: SE IL GIOCATORE SI DISCONNETTE BISOGNA MODIFICARE TUTTI I CONTATORI DEI GIOCATORI IN GIOCO
    }

    public void game(){


        setPlayersInRound(playersInRound);
        Message mex;



        // CICLO CHE GESTISCE I ROUND
        for (int round = 0; round< game.getMaxRound(); round++){

            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1
            game.setStock(game.getDiceBag().extractDice(playersInRound.size()+1));

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for ( turno=0; turno<playersInRound.size(); turno++){

                playersInRound.get(turno).setSetDice(false);
                playersInRound.get(turno).setUseTools(false);

                // CICLO CHE GESTISCE LE DUE MOSSE DEL GIOCATORE DENTRO IL SINGOLO TURNO
                for (move=0; move<2;move++){
                    // Invia a tutti i giocatori le informazioni generali del turno
                    view.sendMessageUpdate(turno, getGame(),playersInRound.get(turno).getName());

                    // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI PER IL PROPRIO TURNO DI GIOCO
                    view.sendMessageTurn(playersInRound,turno);

                    b=false;
                    waitMove();
                    LOGGER.log(Level.INFO,"Termine mossa "+ String.valueOf(move) + " del giocatore "+ playersInRound.get(turno).getName());

                }

            }

            // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
            game.getRoundSchemeMap()[round].setDices(game.getStock());

            // aggiorna l'arraylist per i turni dentro al round. quello che era primo diventa ultimo
            updatePlayersInRound(playersInRound);
        }

        // FINE PARTITA

        // CALCOLA PUNTEGGI
        calcScore();
        ArrayList<Player> finalPlayers = vsScore(playersInRound);

        // INVIA AI GIOCATORI I PUNTEGGI FINALI + MAPPE FINALI + OBIETTIVI PRIVATI DI TUTTI I GIOCATORI
        MessageFinalScore messag = new MessageFinalScore();
        messag.setPlayersFinal(finalPlayers);
        mex = new Message(Message.CVEVENT,messag);
        view.sendBroadcast(mex);


    }

    synchronized public void waitMove(){
        try {
            normale: LOGGER.log(Level.INFO,"Attendo che il giocatore "+ this.playersInRound.get(turno).getName()+ " effettui la sua mossa" );

            while(!b){
                this.wait();
                b=true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    synchronized public void setTools(){
        this.playersInRound.get(turno).setUseTools(A);
        notifyAll();
    }

    synchronized public void setPos(){
        this.playersInRound.get(turno).setSetDice(A);
        notifyAll();
    }





    public void setPlayersInRound(ArrayList <Player> players){
        // inizializza la prima metà dell'array
        for (int i=0;i<this.players.size();i++)
            players.add(this.players.get(i));
        // inizializza la seconda metà dell'array
        for (int i=this.players.size();i>0;i--)
            players.add(this.players.get(i-1));
    }

    public void updatePlayersInRound(ArrayList<Player> players){
        Player exFirst= players.get(0); // salvo il giocatore da spostare
        players.remove(players.size()-1); // elimino il giocatore che si trova in fondo
        players.remove(0); // elimino il giocatore che si trova in prima posizione
        players.add(this.players.size()-1,exFirst); // aggiungi il primo turno del giocatore
        players.add(this.players.size(),exFirst); // aggiungi il secondo turno del giocatore
    }

    // metodo per calcolare i punteggi dei giocatori
    public void calcScore(){
        // cicla i giocatori
        for (int i=0;i<this.players.size();i++){
             // cicla le carte obiettivo pubbliche
            for (int j = 0; j< game.getPublicObjCard().size(); j++){
               // calcola il punteggio ottenuto tramite la carta obiettivo pubblica
                players.get(i).setScore(players.get(i).getScore()+ game.getPublicObjCard().get(j).search(players.get(i).getMap()));
            }
            // calcola il punteggio ottenuto tramite la carta obiettivo privata
            players.get(i).setScore(players.get(i).getScore()+players.get(i).getCardPrivateObj().search(players.get(i).getMap()));
            // calcola il punteggio ottenuto tramite i segnalini favore rimasti
            players.get(i).setScore(players.get(i).getScore()+players.get(i).getFavSig());
            // calcola il punteggio ottenuto sottraendo gli spazi liberi nella mappa
            players.get(i).setScore(players.get(i).getScore()- players.get(i).getMap().emptyCells());
        }
    }

    // Metodo che ritorna un elenco ordinato dei player per punteggio
    public ArrayList<Player> vsScore(ArrayList<Player> playersInLastRound){
        boolean set;
        int j;
        ArrayList <Player> playersFinal = new ArrayList<>();
        // metti il primo giocatore nell'array.
        playersFinal.add(players.get(0));

        // cicla i giocatori

        for (int i=1; i<players.size();i++){
            // cicla i giocatori già calcolati
            set=false;
            j=0;
            while(!set && j<playersFinal.size()){
                // confronta i punteggi
                if (players.get(i).getScore()>playersFinal.get(j).getScore()) {
                    // se il punteggio del giocatore preso in considerazione è più alto, aggiungilo prima di quello confrontato
                    playersFinal.add(j, players.get(i));
                    set=true;
                }
                else
                { // altrimenti
                    if (players.get(i).getScore()==playersFinal.get(j).getScore()){
                        // se il punteggio del giocatore preso in considerazione è uguale a quello confrontato
                        // confronta i punteggi ottenuti dalle carte obiettivo privato
                        if (players.get(i).getCardPrivateObj().search(players.get(i).getMap())>playersFinal.get(j).getCardPrivateObj().search(playersFinal.get(j).getMap())){
                            // se il punteggio ottenuto dall'obiettivo privato del giocatore è più alto aggiungilo prima di quello confrontato
                            playersFinal.add(j,players.get(i));
                            set=true;
                        }
                        else{ // altrimenti
                            // se anche i punteggi privati sono uguali
                            if (players.get(i).getCardPrivateObj().search(players.get(i).getMap())==playersFinal.get(j).getCardPrivateObj().search(playersFinal.get(j).getMap())){
                                // confronta il numero di segnalini favore rimasti
                                if (players.get(i).getFavSig()>playersFinal.get(j).getFavSig()) {
                                    // se al giocatore sono rimasti più segnalini favore rispetto a quello confrontato allora aggiungilo prima di quello confrontato
                                    playersFinal.add(j, players.get(i));
                                    set=true;
                                }
                                else{
                                    // se anche i segnalini favore rimasti sono uguali
                                    if (players.get(i).getFavSig()==playersFinal.get(j).getFavSig() && (playersInLastRound.indexOf(players.get(i))<playersInLastRound.indexOf(playersFinal.get(j)))){
                                        // se il giocatore preso in considerazione ha giocato prima del giocatore preso in considerazione nell'ultimo round
                                        playersFinal.add(j, players.get(i));
                                        set=true;
                                    }
                                }

                            }


                        }

                    }
                }
                // se sei arrivato alla fine dei giocatori da confrontare
                if (j+1==playersFinal.size()){
                    // piazza il giocatore in fondo all'array.
                    playersFinal.add(players.get(i));
                    set=true;
                }
                else
                    // altrimenti aumenta j per proseguire il confronto
                    j++;
            }

        }



        return playersFinal;
    }

    public void useTools(String titleCard){
        boolean find=false;
        int i=0;
        while (!find || i< game.getToolCards().size()){
            if (game.getToolCards().get(i).getTitle().equalsIgnoreCase(titleCard))
                find=true;
            else
                i++;
        }
        game.getToolCards().get(i).getStrategy().requestMessage(view);
    }

    public void setSetDice(boolean setDice) {
        this.setDice = setDice;
    }

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }
}
