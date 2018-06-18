package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class MessagePassTurn implements MessageVC {
    private static final long serialVersionUID = -2296402075002947888L;
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.fakeMove();
    }
}
