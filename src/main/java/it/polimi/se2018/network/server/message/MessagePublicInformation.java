package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.util.ArrayList;

public class MessagePublicInformation implements MessageCV{

    ArrayList<PublicObjectiveCard> publicObjective = new ArrayList<>();
    ArrayList<ToolCard> toolCards = new ArrayList<>();



    @Override
    public void accept(ConnectionClient client) {

    }

    public void setPublicObjective(ArrayList<PublicObjectiveCard> publicObjective) {
        this.publicObjective = publicObjective;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjective() {
        return publicObjective;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }
}
