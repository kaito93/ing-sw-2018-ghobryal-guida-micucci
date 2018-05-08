package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

public class ColoredCell extends Cell {
    Color color;

    public ColoredCell(){
        super();
        color = null;
    }
    
    public Color getColor(){
        return color;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
