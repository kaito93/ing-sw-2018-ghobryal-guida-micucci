/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.model;

import it.polimi.se2018.model.Dice;

/**
 *
 * @author Andrea
 */
public class NumberNotValidException extends Exception {

    boolean exitBooleanValue;
    public NumberNotValidException(Dice dice) throws NumberNotValidException {
        exitBooleanValue = false;
        dice.throwDice();
    }
    
}
