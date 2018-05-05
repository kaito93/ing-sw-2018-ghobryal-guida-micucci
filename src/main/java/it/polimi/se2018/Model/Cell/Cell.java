package it.polimi.se2018.Model.Cell;

import it.polimi.se2018.Model.Dice;

public class Cell {

    Dice dice;
    
    public Cell(){
        dice = null;
    }
    
    public Dice getDice(){
        return dice;
    }
}
