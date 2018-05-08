package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cell.Cell;

public class Player {

    String name;
    Map map;
    int favorSig;
    PrivateObjectiveCard privateObj;
    int score;
    
    public Player(Long id_Player_interface, String name_player, Map glassWindow, PrivateObjectiveCard priv){
        name = name_player;
        map = glassWindow;
        favorSig = 0;
        privateObj = priv;
        score = 0;
    }
    
    public Card getCardPrivateObj(){
        return privateObj;
    }
    
    public int getScore(){
        return score;
    }
    
    public int getFavSig()
    {
        return favorSig;
    }
    
    public Map getMap(){
        return map;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean posDice(Dice dice, Cell cell){
        if (cell.getDice() == null)
        {
            cell.setDice(dice);
            return true;
        }
        else
            return false;
    }
    
    public void initFavor(Map glassWindow){
        favorSig = glassWindow.getDifficultyLevel();
    }
    
 //   public boolean useTool()

}
