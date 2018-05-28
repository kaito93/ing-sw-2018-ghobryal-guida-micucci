package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;

import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.message.MessageFinalScore;
import it.polimi.se2018.network.server.message.MessageUpdate;
import it.polimi.se2018.network.server.message.MessageYourTurn;


import java.util.ArrayList;

public class Controller implements it.polimi.se2018.util.Observer <MessageVC> {

    Model model;
    VirtualView view;
    ArrayList<Player> players;
    boolean setDice = false;
    boolean useTools = false;


    public void update(MessageVC message) {
        message.accept(this);

    }

    public Controller (VirtualView view, ArrayList<Player> players)  {
        this.model=new Model();
        this.view=view;
        this.players=players;
        view.addObservers(this);
        view.start();
        game();
    }

    public void visit(ResponseMap message){
        int index=searchUser(message.getUsername());
        if (index>=0)
        {
            this.players.get(index).setMap(message.getMapChoose());
            this.players.get(index).setFavorSig();
        }
        else
            System.err.println("E' stato passato un giocatore errato");
    }

    public int searchUser(String user){
        for (int i=0; i<this.players.size();i++){
            if (players.get(i).getName().equalsIgnoreCase(user))
                return i;
        }
        return -1;
    }

    public void startGame(){
        model.setPrivateObjectiveCard(players); // chiama il metodo per settare le carte obiettivo privato
        view.startGame();
        view.publicInformation(model.getPublicObjCard(),model.getToolCards());

    }

    public Model getModel() {
        return model;
    }

    public void updatePlayers(Player players){
        // TO DO: SE IL GIOCATORE SI DISCONNETTE BISOGNA MODIFICARE TUTTI I CONTATORI DEI GIOCATORI IN GIOCO
    }

    public void game(){

        ArrayList<Player> playersInRound = new ArrayList<>();
        setPlayersInRound(playersInRound);
        Message mex;


        // CICLO CHE GESTISCE I ROUND
        for (int round=0; round<model.getMaxRound();round++){

            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for (int turno=0; turno<playersInRound.size(); turno++){

                // INVIA A TUTTI I GIOCATORI LE INFORMAZIONI DI TUTTI I GIOCATORI.
                MessageUpdate message= new MessageUpdate();
                message.setMessage("E' il turno di "+playersInRound.get(turno).getName());
                for (int i=0; i<this.players.size();i++){
                    message.addMaps(players.get(i).getMap());
                    message.addUsers(players.get(i).getName());
                }
                mex = new Message(Message.CVEVENT,message);
                view.sendBroadcast(mex);

                // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI PER IL PROPRIO TURNO DI GIOCO
                playersInRound.get(turno).setSetDice(false);
                playersInRound.get(turno).setUseTools(false);

                sendMessageTurn(playersInRound,turno);


                // finchè qualcuno non viene notificata la mossa del giocatore attendi.
                while(playersInRound.get(turno).getSetDice()==false && playersInRound.get(turno).getUseTools()==false) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



                // SECONDA MOSSA
                sendMessageTurn(playersInRound,turno);
                // finchè una delle due è falsa vuol dire che manca ancora una azione.
                while(playersInRound.get(turno).getSetDice()==false || playersInRound.get(turno).getUseTools()==false) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
            model.getRoundSchemeMap()[round].setDices(model.getStock());

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

    public void sendMessageTurn(ArrayList<Player> playersInRound, int turno){
        MessageYourTurn mes = new MessageYourTurn();
        mes.setPosDice(playersInRound.get(turno).getSetDice());
        mes.setUseTools(playersInRound.get(turno).getUseTools());
        view.sendToPlayer(playersInRound.get(turno).getName(),mes);
    }

    public void setPlayersInRound(ArrayList <Player> players){
        // inizializza la prima metà dell'array
        for (int i=0;i<this.players.size();i++)
            players.add(this.players.get(i));
        // inizializza la seconda metà dell'array
        for (int i=this.players.size();i>0;i--)
            players.add(this.players.get(i));
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
            for (int j=0; j<model.getPublicObjCard().size();j++){
               // calcola il punteggio ottenuto tramite la carta obiettivo pubblica
                players.get(i).setScore(players.get(i).getScore()+model.getPublicObjCard().get(j).search(players.get(i).getMap()));
            }
            // calcola il punteggio ottenuto tramite la carta obiettivo privata
            players.get(i).setScore(players.get(i).getScore()+players.get(i).calcPrivateScore());
            // calcola il punteggio ottenuto tramite i segnalini favore rimasti
            players.get(i).setScore(players.get(i).getScore()+players.get(i).getFavSig());
            // calcola il punteggio ottenuto sottraendo gli spazi liberi nella mappa

            //players.get(i).setScore(players.get(i).getScore()+ players.get(i).getMap().CALCOLA);
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
                        if (players.get(i).calcPrivateScore()>playersFinal.get(j).calcPrivateScore()){
                            // se il punteggio ottenuto dall'obiettivo privato del giocatore è più alto aggiungilo prima di quello confrontato
                            playersFinal.add(j,players.get(i));
                            set=true;
                        }
                        else{ // altrimenti
                            // se anche i punteggi privati sono uguali
                            if (players.get(i).calcPrivateScore()==playersFinal.get(j).calcPrivateScore()){
                                // confronta il numero di segnalini favore rimasti
                                if (players.get(i).getFavSig()>playersFinal.get(j).getFavSig()) {
                                    // se al giocatore sono rimasti più segnalini favore rispetto a quello confrontato allora aggiungilo prima di quello confrontato
                                    playersFinal.add(j, players.get(i));
                                    set=true;
                                }
                                else{
                                    // se anche i segnalini favore rimasti sono uguali
                                    if (players.get(i).getFavSig()==playersFinal.get(j).getFavSig()){
                                        // se il giocatore preso in considerazione ha giocato prima del giocatore preso in considerazione nell'ultimo round
                                        if (playersInLastRound.indexOf(players.get(i))<playersInLastRound.indexOf(playersFinal.get(j))) {
                                            playersFinal.add(j, players.get(i));
                                            set=true;
                                        }
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

    public void setSetDice(boolean setDice) {
        this.setDice = setDice;
    }

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }
}
