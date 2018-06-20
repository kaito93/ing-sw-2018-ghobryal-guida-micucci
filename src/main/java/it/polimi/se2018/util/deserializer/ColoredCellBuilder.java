package it.polimi.se2018.util.deserializer;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ColoredCell;

/**
 * constructor class for the colored cell of the maps
 * extends map builder abstract class
 */
public class ColoredCellBuilder extends MapsBuilder {
    private Cell colored;

    /**
     * class constructor inizialize the string that has to be compared to "color"
     * and set the cell that must be built with color
     */
    public ColoredCellBuilder(){
        super();
        this.setCell(colored);
        this.setToBeCompared("color");
    }

    /**
     * method that create a colored cell object after comparing the strings of type
     */
    @Override
    public Cell createCell(String color, int value, int number) {
     colored = new ColoredCell();
     colored.setColor(Color.parseInput(color));
     colored.setNumberCell(number);
     return colored;
    }
}
