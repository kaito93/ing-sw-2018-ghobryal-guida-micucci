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
    
    public boolean AlreadyExistInColumn(int column, Color color, int value){
        int index;
        for(index = 0; index < this.numRow(); index++)
        {
            if((this.getCell(index, column).getColor() == color) || (this.getCell(index, column).getValue() == value))
                return true;
            else 
                continue;
        }
        return false;
    }
    
    public boolean AlreadyExistInRow(int row, Color color, int value){
        int index;
        for(index = 0; index < this.numRow(); index++)
        {
            if((this.getCell(row, index).getColor() == color) || (this.getCell(row, index).getValue() == value))
                return true;
            else 
                continue;
        }
        return false;
    }
    
    public boolean setCell(Dice d, int row, int column){
        if (validPosition(row, column, d) == false)
            return false;
        else
        {
            
            return true;
        }
    }
    
    public boolean validPosition(int row, int column, Dice dice){
        if ((this.getCell(row, column).getDice() != null) && (this.getCell(row, column).getDice().getValue() != dice.getValue()) && (this.getCell(row, column).getDice().getColor() != dice.getColor()))
            return false;
        else if ((this.getCell(row, column).getDice() == null) && (this.getCell(row, column).getDice().getValue() == dice.getValue()) && (this.getCell(row, column).getDice().getColor() == dice.getColor()))
        {
            if(((this.AlreadyExistInColumn(column, dice.getColor(), dice.getValue())) == true) && (this.AlreadyExistInRow(row, dice.getColor(), dice.getValue()) == true))
                return false;
        }
        return true;
            
    }
}
