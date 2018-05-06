package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Dice;

public class Cell {

    Dice dice;
    
    public Cell(){
        dice = null;
    }
    
    public Dice getDice(){
        return dice;
    }
}
