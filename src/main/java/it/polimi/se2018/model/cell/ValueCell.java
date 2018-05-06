package it.polimi.se2018.model.cell;

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
