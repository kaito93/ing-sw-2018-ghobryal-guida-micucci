package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.network.client.connection.ConnectionClient;

public class MessageStart implements MessageCV {

    String titlePrivateCard;
    String descriptionPrivateCard;


    @Override
    public void accept(ConnectionClient client) {
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
