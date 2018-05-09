package it.polimi.se2018.model.cell;

/** class ColoredCell
 *  defines a cell with a particoular color
 *  @author: Andrea Micucci, Anton Ghobryal
 */
import it.polimi.se2018.model.Color;

public class ColoredCell extends Cell {
    Color color;

    /** class constructor
     *  @return a cell with a particoular color
     */
    public ColoredCell(){
        super();
        color = null;
    }
    
    /** method that returns the color of the cell
     * @return: a colour attribute contained in enumeration
     */
    public Color getColor(){
        return color;
    }

    /** method that returns the value of the cell
     * @return: 0 always, that indicate there is no special value in this cell
     */
    @Override
    public int getValue() {
        return 0;
    }
}
