package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;

import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;


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
            this.players.get(index).setFavorSig(message.getMapChoose().getDifficultyLevel());
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

        // CICLO CHE GESTISCE I ROUND
        for (int round=0; round<model.getMaxRound();round++){

            // ESTRAI I DADI DAL SACCHETTO E METTILI NELLA RISERVA. #DADI ESTRATTI = (2*giocatori)+1

            // CICLO CHE GESTISCE I TURNI INTERNI AL ROUND...
            // PS. ATTENZIONE ALLA GESTIONE DELLE RICONNESSIONI CHE POTREBBE FAR SBALLARE IL CONTATORE DEI TURNI
            for (int turno=0; turno<players.size()*2; turno++){

                // INVIA AL SINGOLO GIOCATORE LE INFORMAZIONI DI TUTTI I GIOCATORI.

            }

            // PIAZZA I DADI RIMANENTI NEL TRACCIATO DEI ROUND.
            model.getRoundSchemeMap()[round].setDices(model.getStock());

        }

        // CALCOLA PUNTEGGI

        // INVIA AI GIOCATORI I PUNTEGGI FINALI + MAPPE FINALI + OBIETTIVI PRIVATI DI TUTTI I GIOCATORI
    }

    public void setSetDice(boolean setDice) {
        this.setDice = setDice;
    }

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }
}
