package it.polimi.se2018.server.deserializer.maps.cells;

import it.polimi.se2018.server.model.cell.BlankCell;
import it.polimi.se2018.shared.model_shared.Cell;

public class BlankCellBuilder extends CellsBuilder {
    private Cell blank;

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
