package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class MessageUseTool implements MessageVC {

    private static final long serialVersionUID = -1139168132175799279L;
    private String title;

    /**
     * method that set the title of a card
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.useTools(title);
    }
}
