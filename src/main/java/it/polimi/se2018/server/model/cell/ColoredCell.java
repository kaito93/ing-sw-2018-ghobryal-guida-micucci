package it.polimi.se2018.server.model.cell;

import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Cell;

/** class ColoredCell
 *  defines a cell with a particoular color
 *  @author Andrea Micucci, Anton Ghobryal
 */

public class ColoredCell extends Cell {

    /** class constructor
     *  return a cell with a particular color
     */
    public ColoredCell(){
        super();
    }
    
    /** method that returns the color of the cell
     * @return a colour attribute contained in enumeration
     */
    public Color getColor(){
        return color;
    }

    /** method that returns the value of the cell
     * @return 0 always, that indicate there is no special value in this cell
     */
    @Override
    public int getValue() {
        return 0;
    }

    /**
     * sets the cell's color on the map
     * @param color cell's color
     */
    @Override
    public void setColor(Color color) {
        this.color=color;
    }

    /**
     * sets the cell's value on the map
     * @param value cell's value
     */
    @Override
    public void setValue(int value) {
        this.value = 0;
    }


}
