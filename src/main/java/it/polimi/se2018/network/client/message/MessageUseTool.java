package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class MessageUseTool implements MessageVC {

    String titleCardChoosed;

    public void setTitleCardChoosed(String titleCardChoosed) {
        this.titleCardChoosed = titleCardChoosed;
    }

    @Override
    public void accept(Controller controller) {
        controller.useTools(titleCardChoosed);
    }
}
