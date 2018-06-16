package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class MessageUseTool implements MessageVC {

    private static final long serialVersionUID = -1139168132175799279L;
    private String titleCardChoosed;

    public void setTitleCardChoosed(String titleCardChoosed) {
        this.titleCardChoosed = titleCardChoosed;
    }

    @Override
    public void accept(Controller controller) {
        controller.useTools(titleCardChoosed);
    }
}
