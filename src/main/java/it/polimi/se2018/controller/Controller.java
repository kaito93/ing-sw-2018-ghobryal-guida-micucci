package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

public class Controller implements it.polimi.se2018.util.Observer <MessageVC> {

    Model model;
    VirtualView view;

    public void update(MessageVC message) {

    }

    public Controller (VirtualView view){
        this.model=new Model();
        this.view=view;
        view.addObservers(this);
        view.start();
    }


}
