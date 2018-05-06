package it.polimi.se2018.model;

import it.polimi.se2018.model.cell.*;

public class Map {

    String name;
    int difficultyLevel;
    Cell cell[][];
    
    public Map(String glassWindowName, int difficulty, int row, int column){
        name = glassWindowName;
        difficultyLevel = difficulty;
        // matrice? parliamone con anton
    }
    
    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    public int numColumn(){
        return 0;
    }
    
    public int numRow(){
        return 0;
    }
    
    public Cell getCell(int row, int column){
        return cell[row][column];
    }
    
    public String getName(){
        return name;
    }
    
    public boolean setCell(Dice d, int row, int column){
        if (validPosition(row, column) == false)
            return false;
        else
        {
            cell[row][column].setDice(d);
            return true;
        }
    }
    
    public boolean validPosition(int row, int column){
        if (getCell(row, column) != null)
            return false;
        else
            return true;
    }
}
