package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

public class ValueCell extends Cell {

    int value;
    
    public ValueCell(){
        super();
        value = 0;
    }
    
    public int getValue(){
        return value;
    }

    @Override
    public Color getColor() {
        return null;
    }

}
