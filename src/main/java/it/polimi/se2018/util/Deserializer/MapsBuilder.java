package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class MapsBuilder implements Observer {

    String toBeCompared;
    Cell cell;
    TransitionForMaps trans;
    ArrayList<Maps> maps;
    ArrayList<Cell> toBeReturn;
    entireMap mappaIntera;

    public MapsBuilder(){
        trans = new TransitionForMaps();
        maps = new ArrayList<>();
        toBeReturn = new ArrayList<>();
        mappaIntera = new entireMap();
    }



    @Override
    public void update(Observable o, Object arg) {
        trans = (TransitionForMaps) arg;
        maps = trans.getMap();
        mappaIntera.setTitle(trans.getMapName());
        mappaIntera.setLevel(trans.getLevel());
        for(int index = 0; index<maps.size(); index++)
        {
            if(maps.get(index).getType().equalsIgnoreCase(toBeCompared)) {
                cell = this.createCell(maps.get(index).getColor(), maps.get(index).getValue(), maps.get(index).getNumberCell());
                mappaIntera.setMatrix(cell);
            }
        }
    }

    public abstract Cell createCell(String color, int value, int number);

    public ArrayList<Cell> getToBeReturn() {
        return toBeReturn;
    }

    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public String getEntireMapTitle(){
        return mappaIntera.getTitle();
    }

    public int getEntireMapLevel(){
        return mappaIntera.getLevel();
    }
}
