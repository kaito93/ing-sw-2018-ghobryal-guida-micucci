/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage;

import it.polimi.se2018.shared.model_shared.Cell;
import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class ModelChoiceMap {

    ArrayList<Cell[][]> maps;
    ArrayList<String> mapName;
    ArrayList<Integer> favour;

    public ModelChoiceMap() {
    }

    public ArrayList<Integer> getFavour() {
        return favour;
    }

    public ArrayList<String> getMapName() {
        return mapName;
    }

    public ArrayList<Cell[][]> getMaps() {
        return maps;
    }
    
    
    
}
