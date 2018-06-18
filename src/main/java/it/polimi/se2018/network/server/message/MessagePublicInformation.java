package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;

/**
 * class that manage the public news in the game
 * @author Samuele Guida
 */
public class MessagePublicInformation implements MessageCV{

    private static final long serialVersionUID = -6430841943473871296L;
    private ArrayList<String> titlePublicObjective = new ArrayList<>();
    private ArrayList<String> descriptionPublicObjective = new ArrayList<>();
    private ArrayList<Integer>scorePublicObjective = new ArrayList<>();
    private ArrayList<String> titleTools = new ArrayList<>();
    private ArrayList<String> descriptionTools = new ArrayList<>();

    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the information of the public cards
     * @param publicObjective arraylist of public cards
     */
    public void setPublicObjective(ArrayList<PublicObjectiveCard> publicObjective) {
        for (int i=0; i<publicObjective.size();i++){
            titlePublicObjective.add(publicObjective.get(i).getTitle());
            descriptionPublicObjective.add(publicObjective.get(i).getDescription());
            scorePublicObjective.add(publicObjective.get(i).getScore());
        }
    }

    /**
     * method that set the information of the tool cards
     * @param toolCards arraylist of tool cards
     */
    public void setToolCards(ArrayList<ToolCard> toolCards) {
        for (int i=0; i<toolCards.size();i++){
            titleTools.add(toolCards.get(i).getTitle());
            descriptionTools.add(toolCards.get(i).getDescription());
        }
    }

    /**
     * method that return the titles of the tool cards
     * @return an arraylist of strings
     */
    public ArrayList<String> getTitleTools() {
        return titleTools;
    }

    /**
     * method that return the descriptions of the tool cards
     * @return an arraylist of strings
     */
    public ArrayList<String> getDescriptionTools() {
        return descriptionTools;
    }

    /**
     * method that return the descriptions of the public objective cards
     * @return an arraylist of strings
     */

    public ArrayList<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }

    /**
     * method that return the titles of the public objective cards
     * @return an arraylist of strings
     */

    public ArrayList<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }

    /**
     * method that return the scores from the public objective cards
     * @return an arraylist of integers
     */
    public ArrayList<Integer> getScorePublicObjective() {
        return scorePublicObjective;
    }
}

