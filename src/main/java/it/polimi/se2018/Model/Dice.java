package it.polimi.se2018.Model;

import java.util.Random;

public class Dice {

    int value;
    Color color;
    
    public Dice(){
        value = 1;
        color = null;
    }
    
    public void throwDice(){
        Random x = new Random();
        value = x.nextInt(6)+1;
    }
    
    public int getValue(){
        return value;
    }

    public Color getColor() {
        return color;
    }
}
