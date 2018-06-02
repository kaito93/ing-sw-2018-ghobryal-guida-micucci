package it.polimi.se2018.util.Deserializer;

import java.util.ArrayList;

public class TransitionForMaps {

    private String mapName;
    private int level;
    ArrayList<Maps> map;

    public TransitionForMaps(){
        int level = 0;
        map = new ArrayList<Maps>();
    }

    public void setMap(ArrayList<Maps> map) {
        this.map = map;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public ArrayList<Maps> getMap() {
        return map;
    }

    public int getLevel() {
        return level;
    }

    public String getMapName() {
        return mapName;
    }
}
