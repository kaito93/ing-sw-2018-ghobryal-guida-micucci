package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.Cell;

import java.util.ArrayList;

public class entireMap {
    private String title;
    private int level;
    private ArrayList<Cell> matrix;

    public entireMap()
    {
        level = 0;
        matrix = new ArrayList<>();
    }

    public void setAllMatrix(ArrayList<Cell> matrix) {
        this.matrix = matrix;
    }

    public void setMatrix(Cell cellOfMatrix) {
        this.matrix.add(cellOfMatrix);
    }

    public entireMap getMatrixCell(int index){
        return this;
    }

    public Cell getCellOfMatrix(int index){
        return this.getMatrix().get(index);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Cell> getMatrix() {
        return matrix;
    }
}
