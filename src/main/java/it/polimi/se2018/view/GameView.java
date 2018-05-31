package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;

public class GameView {

    private String myUsername;
    private int yourIndex;

    private ArrayList<String> users;
    private ArrayList<Cell[][]> cells = new ArrayList<>();

    private ArrayList<String> titleTools;
    private ArrayList<String> descriptionTools;
    private ArrayList<Boolean> useTools;

    private ArrayList<String> titlePublicObjective;
    private ArrayList<String> descriptionPublicObjective;

    private String titlePrivateObjective;
    private String descriptionPrivateObjective;

    private RoundSchemeCell roundSchemeMap[];
    private ArrayList<Dice> stock;

    private int favor;

    public ArrayList<Dice> getStock() {
        return stock;
    }

    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    public void setStock(ArrayList<Dice> stock) {
        this.stock = stock;
    }

    public void setRoundSchemeMap(RoundSchemeCell[] roundSchemeMap) {
        this.roundSchemeMap = roundSchemeMap;
    }

    public ArrayList<String> getTitleTools() {
        return titleTools;
    }

    public ArrayList<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }

    public ArrayList<String> getDescriptionTools() {
        return descriptionTools;
    }

    public ArrayList<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getDescriptionPrivateObjective() {
        return descriptionPrivateObjective;
    }

    public String getTitlePrivateObjective() {
        return titlePrivateObjective;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
        setYourIndex(users.indexOf(getMyUsername()));
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public void setDescriptionPrivateObjective(String descriptionPrivateObjective) {
        this.descriptionPrivateObjective = descriptionPrivateObjective;
    }

    public void setDescriptionPublicObjective(ArrayList<String> descriptionPublicObjective) {
        this.descriptionPublicObjective = descriptionPublicObjective;
    }

    public void setDescriptionTools(ArrayList<String> descriptionTools) {
        this.descriptionTools = descriptionTools;
    }

    public void setTitlePrivateObjective(String titlePrivateObjective) {
        this.titlePrivateObjective = titlePrivateObjective;
    }

    public void setTitlePublicObjective(ArrayList<String> titlePublicObjective) {
        this.titlePublicObjective = titlePublicObjective;
    }

    public void setTitleTools(ArrayList<String> titleTools) {
        this.titleTools = titleTools;
    }

    public void setCells(ArrayList<Cell[][]> cells) {
        this.cells = cells;
    }

    public ArrayList<Cell[][]> getCells() {
        return cells;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public int getFavor() {
        return favor;
    }

    public void setUseTools(ArrayList<Boolean> useTools) {
        this.useTools = useTools;
    }

    public ArrayList<Boolean> getUseTools() {
        return useTools;
    }

    public void setYourIndex(int yourIndex) {
        this.yourIndex = yourIndex;
    }

    public int getYourIndex() {
        return yourIndex;
    }
}
