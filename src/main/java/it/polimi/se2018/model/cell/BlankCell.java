/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.model.cell;

/** class BlankCell
 *  contains all methods and datas about a cell not coloured and with no value
 *  @author Andrea
 */
import it.polimi.se2018.model.Color;


public class BlankCell extends Cell{

    /** method that return the value of colour of the cell
     * @return null, cause cell is blank
     */
    @Override
    public Color getColor() {
        return null;
    }
    
    /** method that return the value of the cell
     * @return 0 cause cell is with no value
     */
    @Override
    public int getValue() {
        return 0;
        
    }
    
}