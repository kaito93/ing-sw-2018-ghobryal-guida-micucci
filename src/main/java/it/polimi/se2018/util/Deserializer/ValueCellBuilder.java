package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ValueCell;

public class ValueCellBuilder extends MapsBuilder {
    private ValueCell valueCell;

    public ValueCellBuilder(){
        super();
        this.setCell(valueCell);
        this.setToBeCompared("value");
    }
    @Override
    public Cell createCell(String color, int value, int number) {
        valueCell = new ValueCell();
        valueCell.setValue(value);
        valueCell.setNumberCell(number);
        return valueCell;
    }
}
