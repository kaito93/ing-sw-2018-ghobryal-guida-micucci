package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.shared.message_socket.MessageCV;

import java.util.ArrayList;
import java.util.List;

/**
 * class that manage the public news in the game
 * @author Samuele Guida
 */
public class MessagePublicInformation implements MessageCV {

    private static final long serialVersionUID = -6430841943473871296L;
    private ArrayList<String> titlePublicObjective = new ArrayList<>();
    private ArrayList<String> descriptionPublicObjective = new ArrayList<>();
    private ArrayList<Integer>scorePublicObjective = new ArrayList<>();
    private ArrayList<String> titleTools = new ArrayList<>();
    private ArrayList<String> descriptionTools = new ArrayList<>();

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the information of the public cards
     * @param publicObjective arraylist of public cards
     */
    public void setPublicObjective(List<PublicObjectiveCard> publicObjective) {
        for (PublicObjectiveCard aPublicObjective : publicObjective) {
            titlePublicObjective.add(aPublicObjective.getTitle());
            descriptionPublicObjective.add(aPublicObjective.getDescription());
            scorePublicObjective.add(aPublicObjective.getScore());
        }
    }

    /**
     * method that set the information of the tool cards
     * @param toolCards arraylist of tool cards
     */
    public void setToolCards(List<ToolCard> toolCards) {
        for (ToolCard toolCard : toolCards) {
            titleTools.add(toolCard.getTitle());
            descriptionTools.add(toolCard.getDescription());
        }
    }

    /**
     * method that return the titles of the tool cards
     * @return an arraylist of strings
     */
    public List<String> getTitleTools() {
        return titleTools;
    }

    /**
     * method that return the descriptions of the tool cards
     * @return an arraylist of strings
     */
    public List<String> getDescriptionTools() {
        return descriptionTools;
    }

    /**
     * method that return the descriptions of the public objective cards
     * @return an arraylist of strings
     */

    public List<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }

    /**
     * method that return the titles of the public objective cards
     * @return an arraylist of strings
     */

    public List<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }

    /**
     * method that return the scores from the public objective cards
     * @return an arraylist of integers
     */
    public List<Integer> getScorePublicObjective() {
        return scorePublicObjective;
    }
}

