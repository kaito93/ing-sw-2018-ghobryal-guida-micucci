package it.polimi.se2018.client.view.ViewGuiPack.gameBoardPage;

import it.polimi.se2018.shared.model_shared.Cell;

import java.util.ArrayList;

public class ModelGameBoardPage {
    Cell[][] definitiveMap;
    int fav;
    String name;


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
}
