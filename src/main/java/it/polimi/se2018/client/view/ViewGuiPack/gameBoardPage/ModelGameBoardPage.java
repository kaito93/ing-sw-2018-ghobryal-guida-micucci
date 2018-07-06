package it.polimi.se2018.client.view.ViewGuiPack.gameBoardPage;

import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.util.List;

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

    public ModelGameBoardPage(){

    }

    public Cell[][] getDefinitiveMap() {
        return definitiveMap;
    }

    public int getFav() {
        return fav;
    }

    public String getName() {
        return name;
    }

    public void setDefinitiveMap(Cell[][] definitiveMap) {
        this.definitiveMap = definitiveMap;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getPrivateCardTitle() {
        return privateCardTitle;
    }

    public String getPrivateCardDescription() {
        return privateCardDescription;
    }

    public void setPrivateCardDescription(String privateCardDescription) {
        this.privateCardDescription = privateCardDescription;
    }

    public void setPrivateCardTitle(String privateCardTitle) {
        this.privateCardTitle = privateCardTitle;
    }

    public List<String> getDescriptionPub() {
        return descriptionPub;
    }

    public List<Integer> getScorePub() {
        return scorePub;
    }

    public List<String> getTitlePub() {
        return titlePub;
    }

    public void setDescriptionPub(List<String> descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    public void setScorePub(List<Integer> scorePub) {
        this.scorePub = scorePub;
    }

    public void setTitlePub(List<String> titlePub) {
        this.titlePub = titlePub;
    }

    public List<String> getDescriptionTool() {
        return descriptionTool;
    }

    public List<String> getTitleTools() {
        return titleTools;
    }

    public List<Boolean> getUsageTool() {
        return usageTool;
    }

    public void setDescriptionTool(List<String> descriptionTool) {
        this.descriptionTool = descriptionTool;
    }

    public void setTitleTools(List<String> titleTools) {
        this.titleTools = titleTools;
    }

    public void setUsageTool(List<Boolean> usageTool) {
        this.usageTool = usageTool;
    }

    public void setStock(List<Dice> stock) {
        this.stock = stock;
    }

    public List<Dice> getStock() {
        return stock;
    }

    public RoundSchemeCell[] getSchemeRound() {
        return schemeRound;
    }

    public void setSchemeRound(RoundSchemeCell[] schemeRound) {
        this.schemeRound = schemeRound;
    }
}
