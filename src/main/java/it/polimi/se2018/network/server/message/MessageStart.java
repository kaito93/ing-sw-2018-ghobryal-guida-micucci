package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageStart implements MessageCV {

    private static final long serialVersionUID = 7676495002144599825L;
    private String titlePrivateCard;
    private String descriptionPrivateCard;


    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit (this);
    }


    public void setCard(PrivateObjectiveCard card) {
        this.descriptionPrivateCard=card.getDescription();
        this.titlePrivateCard=card.getTitle();
    }



    public String getDescriptionPrivateCard() {
        return descriptionPrivateCard;
    }

    public String getTitlePrivateCard() {
        return titlePrivateCard;
    }
}
