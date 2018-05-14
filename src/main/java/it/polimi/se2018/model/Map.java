package it.polimi.se2018.model;
/** class Map
 * contains all the method to interact with the map
 * @author Andrea Micucci
 */
import it.polimi.se2018.model.cell.*;

public class Map {

    private String name;
    private int difficultyLevel;
    private Cell cell[][];
    
    /**
     * /** class constructor: initialize the object Map type
     * @param glassWindowName: name of the matrix, refers to the corrispondent card
     * @param difficulty: level of difficulty of the matrix
     * @param row: number of row of the matrix
     * @param column: number of column of the matrix
     * @throws notValidMatrixException: when the values of row and column are not valid to construct the matrix
     */
    public Map(String glassWindowName, int difficulty, int row, int column) throws notValidMatrixException{
        name = glassWindowName;
        difficultyLevel = difficulty;
        cell = new Cell[row][column];
        if ((row < 0) || (column < 0))
            throw new notValidMatrixException(this);
    }
    
    /** method that return the difficulty level of the match
     * @return an integer between 3 and 6
     */
    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    /** method that return the number of column of the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */
    public int numColumn(){
        return cell[0].length;
    }
    
    /** method that return the number of row in the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */ 
    public int numRow(){
        return cell.length;
    }
    
    /** method that return a single cell of a matrix that represent the glasswindow
     * @param row: where you want to search
     * @param column: where you want to search
     * @return an object Cell
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     */
    public Cell getCell(int row, int column) throws notValidCellException{
        if ((row <= 0) || (column <= 0) || (row > 6) || (column > 6))
            throw new notValidCellException();
        return cell[row][column];
    }
    
    /** method that return the name of the map (that represent the glasswindow)
     * @return a string that represent the name of the glasswindow
     */
    public String getName(){
        return name;
    }
    
    /** method that verify if a number or a color exist already in a coloumn of the matrix
     * @param column: of the matrix where you want to search the number
     * @param color: that you are searching
     * @param value: that you are searching
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if the value already exist in the column of matrix, else false
     */
    public boolean AlreadyExistInColumn(int column, Color color, int value) throws notValidCellException{
        int index;
        for(index = 0; index < this.numRow(); index++)
        {
            if((this.getCell(index, column).getColor() == color) || (this.getCell(index, column).getValue() == value))
                return true;
        }
        return false;
    }
    
    /** method that verify if a number or a color exist already in a row of the matrix
     * @param row: of the matrix where you want to search the number
     * @param color: that you are searching
     * @param value: that you are searching
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if the value already exist in the row of matrix, else false
     */
    public boolean AlreadyExistInRow(int row, Color color, int value) throws notValidCellException{
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
    /** a method that set all the values of the cell
     * @param d: the dice you want to set in the cell of the matrix
     * @param row: the row in which you want to set the dice
     * @param column: the column in which you want to set the dice
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is "true", if the dice is setted in the cell
     */
    public boolean setCell(Dice d, int row, int column) throws notValidCellException{
        if (validPosition(row, column, d) == false)
            return false;
        else
        {
            getCell(row, column).setDice(d);
            return true;
        }
    }
    
    /** a method that verify is a position is right to put a dice inside it
     * @param row: the row you want to verify
     * @param column: the column you want to verify
     * @param dice: the dice you want to put in the cell, with its value and color
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is "true" if it is possible to put a dice in it
     */
    public boolean validPosition(int row, int column, Dice dice) throws notValidCellException{
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
