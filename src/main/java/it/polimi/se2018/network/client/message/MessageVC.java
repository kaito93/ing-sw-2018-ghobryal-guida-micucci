package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

import java.io.Serializable;

public interface MessageVC extends Serializable {

    void accept(Controller controller);
}
