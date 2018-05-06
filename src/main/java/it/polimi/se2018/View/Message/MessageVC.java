package it.polimi.se2018.View.Message;

import it.polimi.se2018.Model.Controller.Controller;

import java.io.Serializable;

public interface MessageVC extends Serializable {

    public void accept(Controller controller);
}
