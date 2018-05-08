/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.model.cell;

import it.polimi.se2018.model.Color;

/**
 *
 * @author Andrea
 */
public class BlankCell extends Cell{

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
        
    }
    
}
