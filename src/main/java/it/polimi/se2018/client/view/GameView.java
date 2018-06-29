package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.shared.model_shared.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class that simulate the model for the client. A simply store of information
 * @author Samuele Guida
 */
public class GameView implements Serializable {

    private String myUsername;
    private int yourIndex;

    private List<String> users;
    private List<Cell[][]> cells = new ArrayList<>();

    private List<String> titleTools;
    private List<String> descriptionTools;
    private List<Boolean> useTools;

    private List<String> titlePublicObjective;
    private List<String> descriptionPublicObjective;
    private List<Integer> scorePublicObjective;

    private String titlePrivateObjective;
    private String descriptionPrivateObjective;

    private RoundSchemeCell[] roundSchemeMap;
    private List<Dice> stock;

    private List<Integer> favUser;

    private boolean useTool;
    private boolean posDice;

    /**
     * method that return the stock
     * @return a list of dice
     */
    public List<Dice> getStock() {
        return stock;
    }

    /**
     * method that return the roundSchemeMap
     * @return a matrix of round scheme cell
     */
    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    /**
     * method that set the stock
     * @param stock list of dices
     */
    public void setStock(List<Dice> stock) {
        this.stock = stock;
    }

    /**
     * method that set the round scheme Map
     * @param roundSchemeMap an array of round Scheme Cell
     */
    public void setRoundSchemeMap(RoundSchemeCell[] roundSchemeMap) {
        this.roundSchemeMap = roundSchemeMap;
    }

    /**
     * method that return the title of tool cards
     * @return a list of string
     */
    List<String> getTitleTools() {
        return titleTools;
    }

    /**
     * method that return the title of public objective cards
     * @return a list of string
     */
    List<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }

    /**
     * method that return the descriptions of the tool cards
     * @return a list of strings
     */
    List<String> getDescriptionTools() {
        return descriptionTools;
    }

    /**
     * method that return the descriptions of the public objective cards
     * @return a list of strings
     */
    List<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }

    /**
     * method that return the name of all players
     * @return a list of strings
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * method that return the description of the private objective card
     * @return a string
     */
    String getDescriptionPrivateObjective() {
        return descriptionPrivateObjective;
    }

    /**
     * method that return the title of the private objective card
     * @return a string
     */

    String getTitlePrivateObjective() {
        return titlePrivateObjective;
    }

    /**
     * method that set the names of all users
     * @param users a list of strings
     */
    public void setUsers(List<String> users) {
        this.users = users;
        setYourIndex(users.indexOf(getMyUsername()));
    }

    /**
     * method that return the username of this player
     * @return a string
     */
    private String getMyUsername() {
        return myUsername;
    }

    /**
     * method that set the username of this player
     * @param myUsername a string
     */
    void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    /**
     * method that set the description of the private objective card
     * @param descriptionPrivateObjective a string
     */
    void setDescriptionPrivateObjective(String descriptionPrivateObjective) {
        this.descriptionPrivateObjective = descriptionPrivateObjective;
    }

    /**
     * method that set the descriptions of the public objective cards
     * @param descriptionPublicObjective a list of strings
     */
    void setDescriptionPublicObjective(List<String> descriptionPublicObjective) {
        this.descriptionPublicObjective = descriptionPublicObjective;
    }

    /**
     * method that set the descriptions of the tool cards
     * @param descriptionTools a list of string
     */
    void setDescriptionTools(List<String> descriptionTools) {
        this.descriptionTools = descriptionTools;
    }

    /**
     * method that set the title of the private objective card
     * @param titlePrivateObjective a string
     */
    void setTitlePrivateObjective(String titlePrivateObjective) {
        this.titlePrivateObjective = titlePrivateObjective;
    }

    /**
     * method that set the titles of public objective cards
     * @param titlePublicObjective a list of strings
     */
    void setTitlePublicObjective(List<String> titlePublicObjective) {
        this.titlePublicObjective = titlePublicObjective;
    }

    /**
     * method that set the titles of tool cards
     * @param titleTools a list of strings
     */
    void setTitleTools(List<String> titleTools) {
        this.titleTools = titleTools;
    }

    /**
     * method that set the maps of all players
     * @param cells a list of matrix of cell
     */
    public void setCells(List<Cell[][]> cells) {
        this.cells = cells;
    }

    /**
     * method that return the maps of all players
     * @return a list of matrix of cell
     */
    public List<Cell[][]> getCells() {
        return cells;
    }

    /**
     * method that set the check if a tool card is used or not
     * @param useTools a list of boolean
     */
    public void setUseTools(List<Boolean> useTools) {
        this.useTools = useTools;
    }

    /**
     * method that return the checks if a tool card is used or not
     * @return a list of boolean
     */
    public List<Boolean> getUseTools() {
        return useTools;
    }

    /**
     * method that set the position of this player in the array of all players
     * @param yourIndex an integer
     */
    void setYourIndex(int yourIndex) {
        this.yourIndex = yourIndex;
    }

    /**
     * method that return the position of this player in the array of all players
     * @return an integer
     */
    int getYourIndex() {
        return yourIndex;
    }

    /**
     * method that set true if this player has positioned a dice before in this step, else false
     * @param posDice a boolean
     */
    public void setPosDice(boolean posDice) {
        this.posDice = posDice;
    }

    /**
     * method that set true if this player has used a tool card before in this step, else false
     * @param useTool a boolean
     */
    public void setUseTool(boolean useTool) {
        this.useTool = useTool;
    }

    /**
     * method that return if this player has used a tool card before in this step, else false
     * @return a boolean
     */
    public boolean isUseTool() {
        return useTool;
    }

    /**
     * method that return true if this player has positioned a dice before in this step, else false
     * @return a boolean
     */
    public boolean isPosDice() {
        return posDice;
    }

    /**
     * method that set the remaining favors for all players
     * @param favUser a list of integer
     */
    void setFavUser(List<Integer> favUser) {
        this.favUser = favUser;
    }

    /**
     * method that return the remaining favors for all players
     * @return a list of integer
     */
    List<Integer> getFavUser() {
        return favUser;
    }

    /**
     * method that set the scores for all public objective cards in the game
     * @param scorePublicObjective a list of integer
     */
    void setScorePublicObjective(List<Integer> scorePublicObjective) {
        this.scorePublicObjective = scorePublicObjective;
    }

    /**
     * method that return the scores for all public objective cards in the game
     * @return a list of integer
     */
    List<Integer> getScorePublicObjective() {
        return scorePublicObjective;
    }
}
