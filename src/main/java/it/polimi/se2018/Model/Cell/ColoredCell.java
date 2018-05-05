package it.polimi.se2018.Model.Cell;

import it.polimi.se2018.Model.Color;

public class ColoredCell extends Cell {
    Color color;

    public ColoredCell(){
        super();
        color = null;
    }
    
    public Color getColor(){
        return color;
    }
}
