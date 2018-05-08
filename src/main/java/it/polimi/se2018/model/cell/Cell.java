package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Dice;

public class Cell {

    Dice dice;
    
    public Cell(){
        dice = null;
    }
    
    public void setDice(Dice nextDice){
        dice = nextDice;
    }
    
    public Dice getDice(){
        return dice;
    }
}