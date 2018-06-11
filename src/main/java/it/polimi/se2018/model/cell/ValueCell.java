package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

/** class ValueCell
 *  defines a cell with a particoular value inside
 *  @author Andrea Micucci, Anton Ghobryal
 */

public class ValueCell extends Cell {


    /** class constructor
     *  return a cell with a particoular number
     */
    public ValueCell(){
        super();
    }
    
    /** method that return the value of the cell
     * @return an integer between 1 and 6
     */
    public int getValue(){
        return value;
    }

    /** method that return the colour of the cell
     * @return o in all case because the cell has no number particoular
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * sets the cell's color on the map
     * @param color cell's color
     */
    @Override
    public void setColor(Color color) {
        this.color=Color.NULL;
    }

    /**
     * sets the cell's value on the map
     * @param value cell's value
     */
    @Override
    public void setValue(int value) {
        this.value = value;
    }


}
