package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ColoredCell;

public class ColoredCellBuilder extends MapsBuilder {
    private ColoredCell colored;
    private Color colour;

    public ColoredCellBuilder(){
        super();
        this.setCell(colored);
        this.setToBeCompared("color");
    }

    @Override
    public Cell createCell(String color, int value, int number) {
     colored = new ColoredCell();
     colored.setColor(colour.parseInput(color));
     colored.setNumberCell(number);
     return colored;
    }
}
