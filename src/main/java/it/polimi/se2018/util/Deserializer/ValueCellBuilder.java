package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ValueCell;

/**
 * class to set the creation for the value cell
 * extends mapBuilder
 */
public class ValueCellBuilder extends MapsBuilder {
    private Cell valueCell;

    /**
     * map constructor: set all the value of the class: in particular the string is setted to "value"
     */
    public ValueCellBuilder(){
        super();
        this.setCell(valueCell);
        this.setToBeCompared("value");
    }
    @Override
    /**
     * constructor of cards object: compare the strings and create the cards
     */
    public Cell createCell(String color, int value, int number) {
        valueCell = new ValueCell();
        valueCell.setValue(value);
        valueCell.setNumberCell(number);
        return valueCell;
    }
}
