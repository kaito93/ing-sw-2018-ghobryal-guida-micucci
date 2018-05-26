package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.message.MessageStart;

import java.util.ArrayList;

public class Controller implements it.polimi.se2018.util.Observer <MessageVC> {

    Model model;
    VirtualView view;
    ArrayList<Player> players;

    public void update(MessageVC message) {
        message.accept(this);

    }

    public Controller (VirtualView view, ArrayList<Player> players)  {
        this.model=new Model();
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
        }
        else
            System.out.println("E' stato passato un giocatore errato");
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

    }

    public void updatePlayers(Player players){
        // TO DO: SE IL GIOCATORE SI DISCONNETTE BISOGNA MODIFICARE TUTTI I CONTATORI DEI GIOCATORI IN GIOCO
    }


}
