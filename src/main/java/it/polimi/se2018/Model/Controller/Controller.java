package it.polimi.se2018.Model.Controller;

import it.polimi.se2018.Model.Model;
import it.polimi.se2018.View.Message.MessageVC;

import java.util.Observable;
import java.util.Observer;

public class Controller implements it.polimi.se2018.Util.Observer <MessageVC> {

    Model model;

    public void update(MessageVC message) {
        message.accept(this);
    }


}
