package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class MessageDisconnect implements MessageVC {

    private static final long serialVersionUID = -7488306524541797257L;
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {

    }
}

