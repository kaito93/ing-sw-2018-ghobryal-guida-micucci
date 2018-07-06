/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage;

import it.polimi.se2018.shared.model_shared.Cell;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the data of the Login window
 * @author Andrea Micucci
 */
public class ModelChoiceMap {

    List<Cell[][]> maps = new ArrayList();
    List<String> mapName = new ArrayList();
    List<Integer> favour = new ArrayList();
    private int indexOfDefinitiveMap=-1;
    GridPane gridDefinitive;

    /**
     * empty class constructor
     */
    public ModelChoiceMap() {
    }

    /**
     * getter method for the favour of the player
     * @return the List of possible favour associated by a map
     */
    public List<Integer> getFavour() {
        return favour;
    }

    /**
     * getter method for the map name of the Player
     * @return a list of string with the path name
     */
    public List<String> getMapName() {
        return mapName;
    }

    /**
     * getter method for the maps
     * @return the list of matrix of cell
     */
    public List<Cell[][]> getMaps() {
        return maps;
    }

    /**
     * method for set up of the map to only one data structure
     * @param mapToBeSetted maps that has to be setted
     * @param mapNameToBeSetted map name that has to be setted
     * @param favourToBeSetted favour that has to be setted
     */
    public void setUpMap(List<Cell[][]> mapToBeSetted, List<String> mapNameToBeSetted, List<Integer> favourToBeSetted){
        for(int i = 0; i<favourToBeSetted.size();i++){
                favour.add(i, favourToBeSetted.get(i));
                mapName.add(i, mapNameToBeSetted.get(i));
                maps.add(i,mapToBeSetted.get(i));
        }
    }


    /**
     * setter method for the index of the chosen map
     * @param indexOfDefinitiveMap that has to be setted
     */
    public void setIndexOfDefinitiveMap(int indexOfDefinitiveMap) {
        this.indexOfDefinitiveMap = indexOfDefinitiveMap;
    }

    /**
     * getter method for the index of the definitive map
     * @return an int value
     */
    public int getIndexOfDefinitiveMap() {
        return indexOfDefinitiveMap;
    }

    /**
     * getter method for the grid pane for the chosen map
     * @return return the definitive gridpane of map
     */
    public GridPane getGridDefinitive() {
        return gridDefinitive;
    }

    /**
     * setter method for the definitive grid pane chosen
     * @param gridDefinitive represent the chosen map
     */
    public void setGridDefinitive(GridPane gridDefinitive) {
        this.gridDefinitive = gridDefinitive;
    }
}
