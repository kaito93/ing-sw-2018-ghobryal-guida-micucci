package it.polimi.se2018.util.Deserializer;

import java.util.ArrayList;

public class TransitionForMaps {

    private String title;
    private int level;
    ArrayList<Maps> maps;

    public TransitionForMaps(){
        int level = 0;
        maps = new ArrayList<Maps>();
    }

    public void setMap(ArrayList<Maps> map) {
        this.maps = map;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMapName(String mapName) {
        this.title = mapName;
    }

    public ArrayList<Maps> getMap() {
        return maps;
    }

    public int getLevel() {
        return level;
    }

    public String getMapName() {
        return title;
    }
}
