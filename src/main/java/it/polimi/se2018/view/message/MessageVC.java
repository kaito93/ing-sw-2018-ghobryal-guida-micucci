package it.polimi.se2018.view.message;

import it.polimi.se2018.controller.Controller;

import java.io.Serializable;

public interface MessageVC extends Serializable {

    public void accept(Controller controller);
}
