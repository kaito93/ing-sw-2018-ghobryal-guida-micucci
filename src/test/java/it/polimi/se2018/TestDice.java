/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.NumberNotValidException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andrea
 */
public class TestDice {
    @Test
    public void TestThrowDice() throws NumberNotValidException{
        Dice dice = new Dice();
        dice.throwDice();
      //  assertEquals((1<=dice.getValue() && (dice.getValue()<=6));
    }
}
