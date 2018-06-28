package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.shared.model_shared.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


    public List<Dice> getStock() {
        return stock;
    }

    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    public void setStock(List<Dice> stock) {
        this.stock = stock;
    }

    public void setRoundSchemeMap(RoundSchemeCell[] roundSchemeMap) {
        this.roundSchemeMap = roundSchemeMap;
    }

    List<String> getTitleTools() {
        return titleTools;
    }

    List<String> getTitlePublicObjective() {
        return titlePublicObjective;
    }

    List<String> getDescriptionTools() {
        return descriptionTools;
    }

    List<String> getDescriptionPublicObjective() {
        return descriptionPublicObjective;
    }

    public List<String> getUsers() {
        return users;
    }

    String getDescriptionPrivateObjective() {
        return descriptionPrivateObjective;
    }

    String getTitlePrivateObjective() {
        return titlePrivateObjective;
    }

    public void setUsers(List<String> users) {
        this.users = users;
        setYourIndex(users.indexOf(getMyUsername()));
    }

    private String getMyUsername() {
        return myUsername;
    }

    void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    void setDescriptionPrivateObjective(String descriptionPrivateObjective) {
        this.descriptionPrivateObjective = descriptionPrivateObjective;
    }

    void setDescriptionPublicObjective(List<String> descriptionPublicObjective) {
        this.descriptionPublicObjective = descriptionPublicObjective;
    }

    void setDescriptionTools(List<String> descriptionTools) {
        this.descriptionTools = descriptionTools;
    }

    void setTitlePrivateObjective(String titlePrivateObjective) {
        this.titlePrivateObjective = titlePrivateObjective;
    }

    void setTitlePublicObjective(List<String> titlePublicObjective) {
        this.titlePublicObjective = titlePublicObjective;
    }

    void setTitleTools(List<String> titleTools) {
        this.titleTools = titleTools;
    }

    public void setCells(List<Cell[][]> cells) {
        this.cells = cells;
    }

    public List<Cell[][]> getCells() {
        return cells;
    }

    public void setUseTools(List<Boolean> useTools) {
        this.useTools = useTools;
    }

    public List<Boolean> getUseTools() {
        return useTools;
    }

    void setYourIndex(int yourIndex) {
        this.yourIndex = yourIndex;
    }

    int getYourIndex() {
        return yourIndex;
    }

    public void setPosDice(boolean posDice) {
        this.posDice = posDice;
    }

    public void setUseTool(boolean useTool) {
        this.useTool = useTool;
    }

    public boolean isUseTool() {
        return useTool;
    }

    public boolean isPosDice() {
        return posDice;
    }

    void setFavUser(List<Integer> favUser) {
        this.favUser = favUser;
    }

    List<Integer> getFavUser() {
        return favUser;
    }

    void setScorePublicObjective(List<Integer> scorePublicObjective) {
        this.scorePublicObjective = scorePublicObjective;
    }

    List<Integer> getScorePublicObjective() {
        return scorePublicObjective;
    }
}
