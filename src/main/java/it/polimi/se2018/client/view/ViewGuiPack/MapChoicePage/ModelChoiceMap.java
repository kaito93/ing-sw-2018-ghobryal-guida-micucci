/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage;

import it.polimi.se2018.shared.model_shared.Cell;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class ModelChoiceMap {

    List<Cell[][]> maps = new ArrayList();
    List<String> mapName = new ArrayList();
    List<Integer> favour = new ArrayList();
    private int indexOfDefinitiveMap=-1;

    public ModelChoiceMap() {
    }

    public List<Integer> getFavour() {
        return favour;
    }

    public List<String> getMapName() {
        return mapName;
    }

    public List<Cell[][]> getMaps() {
        return maps;
    }

    public void setUpMap(List<Cell[][]> mapToBeSetted, List<String> mapNameToBeSetted, List<Integer> favourToBeSetted){
        for(int i = 0; i<favourToBeSetted.size();i++){
                favour.add(i, favourToBeSetted.get(i));
                mapName.add(i, mapNameToBeSetted.get(i));
                maps.add(i,mapToBeSetted.get(i));
        }
    }


    public void setIndexOfDefinitiveMap(int indexOfDefinitiveMap) {
        this.indexOfDefinitiveMap = indexOfDefinitiveMap;
    }

    public int getIndexOfDefinitiveMap() {
        return indexOfDefinitiveMap;
    }
}
