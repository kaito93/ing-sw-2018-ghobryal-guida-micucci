package it.polimi.se2018.model;

import java.util.ArrayList;

public class RoundScheme {
    ArrayList<Dice> restOfStock;
    
    public RoundScheme(){
        restOfStock = new ArrayList<Dice>();
    }
    
    public ArrayList<Dice> getRestOfStock(){
        return restOfStock;
    }
    
    public void setDice(ArrayList<Dice> toAdd){
       // restOfStock.add(toAdd);
    }
}

