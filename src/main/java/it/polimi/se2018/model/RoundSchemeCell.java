package it.polimi.se2018.model;

import java.util.ArrayList;

public class RoundSchemeCell {
    ArrayList<Dice> restOfStock;
    
    public RoundSchemeCell(){
        restOfStock = new ArrayList<Dice>();
    }
    
    public ArrayList<Dice> getRestOfStock(){
        return restOfStock;
    }
    
    public void setDice(ArrayList<Dice> toAdd){
        restOfStock.addAll(toAdd);
    }
    
    public Dice getDice(int value, Color color){
    for(int i=0; i<restOfStock.size(); i++)
        {
            if ((restOfStock.get(i).getColor() != color) || (restOfStock.get(i).getValue() > value))
                 continue;
            else
                return restOfStock.get(i);
        }
    return null;
    }
}

