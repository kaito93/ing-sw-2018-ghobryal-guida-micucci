package it.polimi.se2018.view;

import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;

public class GameView {

    private ArrayList<String> users;
    private ArrayList<Cell[][]> cells = new ArrayList<>();

    private ArrayList<String> titleTools;
    private ArrayList<String> descriptionTools;
    private ArrayList<Boolean> useTools;

    private ArrayList<String> titlePublicObjective;
    private ArrayList<String> descriptionPublicObjective;

    private String titlePrivateObjective;
    private String descriptionPrivateObjective;

    private int favor;

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
}
