package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.message.MessageVC;

public class Controller implements it.polimi.se2018.util.Observer <MessageVC> {

    Model model;

    public void update(MessageVC message) {
        message.accept(this);
    }


}
