package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.BlankCell;
import it.polimi.se2018.model.cell.Cell;

public class BlankCellBuilder extends MapsBuilder {
    BlankCell blank;

    public BlankCellBuilder(){
        super();
        this.setToBeCompared("blank");
        this.setCell(blank);
    }


    @Override
    public Cell createCell(String color, int value, int number) {
        blank = new BlankCell();
        blank.setNumberCell(number);
        return blank;
    }
}
