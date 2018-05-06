package it.polimi.se2018.Model;

import it.polimi.se2018.Model.Cell.*;

public class Map {

    String name;
    int difficultyLevel;
    Cell cell[][];

    public int numColumn(){
        return 0;
    }
    public int numRow(){
        return 0;
    }
    public Cell getCell(int row, int column){
        return cell[row][column];
    }
}
