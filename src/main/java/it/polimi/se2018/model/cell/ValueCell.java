package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

/** class ValueCell
 *  defines a cell with a particoular value inside
 *  @author Andrea Micucci, Anton Ghobryal
 */

public class ValueCell extends Cell {

    private int value;
    
    /** class constructor
     *  return a cell with a particoular number
     */
    public ValueCell(){
        super();
        value = 0;
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
        return null;
    }

}
