package it.polimi.se2018.Model.Cell;

public class ValueCell extends Cell {

    int value;
    
    public ValueCell(){
        super();
        value = 0;
    }
    
    public int getValue(){
        return value;
    }

}
