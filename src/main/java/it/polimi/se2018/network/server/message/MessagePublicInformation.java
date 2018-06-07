package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;

public class MessagePublicInformation implements MessageCV{

    private static final long serialVersionUID = -6430841943473871296L;
    ArrayList<String> titlePublicObjective = new ArrayList<>();
    ArrayList<String> descriptionPublicObjective = new ArrayList<>();

    ArrayList<String> titleTools = new ArrayList<>();
    ArrayList<String> descriptionTools = new ArrayList<>();





    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    public void setPublicObjective(ArrayList<PublicObjectiveCard> publicObjective) {
        for (int i=0; i<publicObjective.size();i++){
            titlePublicObjective.add(publicObjective.get(i).getTitle());
            descriptionPublicObjective.add(publicObjective.get(i).getDescription());
        }
    }

    public void setToolCards(ArrayList<ToolCard> toolCards) {
        for (int i=0; i<toolCards.size();i++){
            titleTools.add(toolCards.get(i).getTitle());
            descriptionTools.add(toolCards.get(i).getDescription());
        }
    }
    public ArrayList<String> getTitleTools() {
        return titleTools;
    }

    public ArrayList<String> getDescriptionTools() {
        return descriptionTools;
    }


    public ArrayList<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }



    public ArrayList<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }




}
