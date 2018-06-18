package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

/**
 * Class that manage the private information
 * @author Samuele Guida
 */
public class MessageStart implements MessageCV {

    private static final long serialVersionUID = 7676495002144599825L;
    private String titlePrivateCard;
    private String descriptionPrivateCard;


    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit (this);
    }

    /**
     * method that set title and description of a single private objective card
     * @param card a single private card
     */

    public void setCard(PrivateObjectiveCard card) {
        this.descriptionPrivateCard=card.getDescription();
        this.titlePrivateCard=card.getTitle();
    }


    /**
     * method that return the description of the private card
     * @return a string
     */
    public String getDescriptionPrivateCard() {
        return descriptionPrivateCard;
    }

    /**
     * method that return the title of the private card
     * @return a string
     */
    public String getTitlePrivateCard() {
        return titlePrivateCard;
    }
}
