package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

/** class ColoredCell
 *  defines a cell with a particoular color
 *  @author Andrea Micucci, Anton Ghobryal
 */

public class ColoredCell extends Cell {
    private Color color;

    /** class constructor
     *  return a cell with a particular color
     */
    public ColoredCell(){
        super();
        color = null;
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
     * sets cell's color
     * @param color a Color type to color the cell
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
