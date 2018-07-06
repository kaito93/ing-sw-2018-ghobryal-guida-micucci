package it.polimi.se2018.client.view.ViewGuiPack.gameBoardPage;

import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.util.List;

/**
 * class to use the data of the gameboard
 * @author Andrea Micucci
 */
public class ModelGameBoardPage {
    Cell[][] definitiveMap;
    int fav;
    String name;
    private String privateCardTitle;
    private String privateCardDescription;

    private List<String> titlePub;
    private List<String> descriptionPub;
    private List<Integer> scorePub;

    private List<String> titleTools;
    private List<String> descriptionTool;
    private List<Boolean> usageTool;

    private List<Dice> stock;

    private RoundSchemeCell[] schemeRound;

    private List<Cell[][]> maps;

    private List<String> users;

    private List<String> favors;

    public ModelGameBoardPage(){

    }

    /**
     * getter for the matrix of cell chosen by player
     * @return matrix of cell
     */
    public Cell[][] getDefinitiveMap() {
        return definitiveMap;
    }

    /**
     * getter method for the favour
     * @return int of favour
     */
    public int getFav() {
        return fav;
    }

    /**
     * getter for the name of the maps
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * setter of the maps
     * @param definitiveMap to be setted
     */
    public void setDefinitiveMap(Cell[][] definitiveMap) {
        this.definitiveMap = definitiveMap;
    }

    /**
     * setter method for the fav
     * @param fav int
     */
    public void setFav(int fav) {
        this.fav = fav;
    }

    /**
     * setter method for the name
     * @param name to be setted
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method for the private cards title
     * @return string of title
     */
    public  String getPrivateCardTitle() {
        return privateCardTitle;
    }

    /**
     * getter method for the description of the private cards
     * @return a string of description
     */
    public String getPrivateCardDescription() {
        return privateCardDescription;
    }

    /**
     * setter method for the private card description
     * @param privateCardDescription to be setted
     */
    public void setPrivateCardDescription(String privateCardDescription) {
        this.privateCardDescription = privateCardDescription;
    }

    /**
     * setter method for the private cards title
     * @param privateCardTitle string to be setted
     */
    public void setPrivateCardTitle(String privateCardTitle) {
        this.privateCardTitle = privateCardTitle;
    }

    /**
     * getter method for description of public cards
     * @return a list of string
     */
    public List<String> getDescriptionPub() {
        return descriptionPub;
    }

    /**
     * getter method for the score of public cards
     * @return a list of integer
     */
    public List<Integer> getScorePub() {
        return scorePub;
    }

    /**
     * getter method for the title of public cards
     * @return a list of string with the title of public cards
     */
    public List<String> getTitlePub() {
        return titlePub;
    }

    /**
     * setter method for description Public cards
     * @param descriptionPub
     */
    public void setDescriptionPub(List<String> descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    /**
     * setter method for the score of public cards
     * @param scorePub to be setted
     */
    public void setScorePub(List<Integer> scorePub) {
        this.scorePub = scorePub;
    }

    /**
     * setter method for the title of public cards
     * @param titlePub to be setted
     */
    public void setTitlePub(List<String> titlePub) {
        this.titlePub = titlePub;
    }

    /**
     * getter method for the description
     * @return the description of tool cards
     */
    public List<String> getDescriptionTool() {
        return descriptionTool;
    }

    /**
     * getter method for the title of tool cards
     * @return a list of title of tool cards
     */
    public List<String> getTitleTools() {
        return titleTools;
    }

    /**
     * getter method for the usage of tool cards
     * @return a list of boolean if the cards are used
     */
    public List<Boolean> getUsageTool() {
        return usageTool;
    }

    /**
     * setter method for the description of tool cards
     * @param descriptionTool description of public cards
     */
    public void setDescriptionTool(List<String> descriptionTool) {
        this.descriptionTool = descriptionTool;
    }

    /**
     * setter method for the title of tool cards
     * @param titleTools list of title of the tool cards
     */
    public void setTitleTools(List<String> titleTools) {
        this.titleTools = titleTools;
    }

    /**
     * setter method for the usage tools
     * @param usageTool boolean that is true if the card is already used
     */
    public void setUsageTool(List<Boolean> usageTool) {
        this.usageTool = usageTool;
    }

    /**
     * setter method for the stock of dicce
     * @param stock list of the dice for the stock
     */
    public void setStock(List<Dice> stock) {
        this.stock = stock;
    }

    /**
     * getter method for the stock dices
     * @return a list of dice that make the stock
     */
    public List<Dice> getStock() {
        return stock;
    }

    /**
     * getter method for the array of roundscheme
     * @return array of roundSchemeCell
     */
    public RoundSchemeCell[] getSchemeRound() {
        return schemeRound;
    }

    /**
     * setter method for the scheme Round cell
     * @param schemeRound that has to be setted
     */
    public void setSchemeRound(RoundSchemeCell[] schemeRound) {
        this.schemeRound = schemeRound;
    }

    /**
     * getter method for the maps
     * @return list of matrix of cell
     */
    public List<Cell[][]> getMaps() {
        return maps;
    }

    /**
     * setter method for the maps
     * @param maps list of matrix of cell that has to be setted
     */
    public void setMaps(List<Cell[][]> maps) {
        this.maps = maps;
    }

    /**
     * setter method for the users
     * @param users list of string with the name of users
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }

    /**
     * getter method for the users
     * @return a string of users
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * getter method for the favours
     * @return a list of string
     */
    public List<String> getFavors() {
        return favors;
    }

    /**
     * setter method for favours
     * @param favors to be setted, list of string
     */
    public void setFavors(List<String> favors) {
        this.favors = favors;
    }
}
