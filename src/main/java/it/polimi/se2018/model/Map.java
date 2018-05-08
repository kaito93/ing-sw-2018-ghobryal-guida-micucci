package it.polimi.se2018.model;

import it.polimi.se2018.model.cell.*;

public class Map {

    String name;
    int difficultyLevel;
    Cell cell[][];
    
    public Map(String glassWindowName, int difficulty, int row, int column){
        name = glassWindowName;
        difficultyLevel = difficulty;
        cell = new Cell[row][column];
    }
    
    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    public int numColumn(){
        return cell[0].length;
    }
    
    public int numRow(){
        return cell.length;
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
