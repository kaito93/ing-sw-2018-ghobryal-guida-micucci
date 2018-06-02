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


}
