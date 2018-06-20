package it.polimi.se2018.test_model;

import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.exception.InvalidValueException;
import junit.framework.TestCase;

/**
 * class Dice Tester
 * @author Anton Ghobryal
 */

public class TestDice extends TestCase {

    private Dice d;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestDice(String name){
        super(name);
    }

    /**
     * setup a new dice
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        d = new Dice();
        d.setColor(Color.BLUE);
        super.setUp();
    }

    /**
     * tests throwDice method in class Dice
     */
    public void testThrowDice(){
        d.throwDice();
        testGetValue();
    }

    /**
     * tests getValue method in class Dice
     */
    public void testGetValue(){
        assertTrue(d.getValue()<7);
        assertTrue(d.getValue()>0);
        assertFalse(d.getValue()>6);
        assertFalse(d.getValue()<1);
    }

    /**
     * tests getColor method in class Dice
     */
    public void testGetColor(){
        assertNotNull(d.getColor());
    }

    /**
     * tests setValue method in class Dice
     */
    public void testSetValue(){
        try {
            d.setValue(5);
        } catch (InvalidValueException e) {
            fail();
        }
        testGetValue();
        assertEquals(d.getValue(), 5);
        assertNotSame(d.getValue(), 3);
        try {
            d.setValue(7);
            fail();
        } catch (InvalidValueException e) {
            testGetValue();
        }
    }

    public void testDiceNull(){
        Dice d1 = Dice.diceNull();
        assertEquals(d1.getValue(), 0);
        assertEquals(d1.getColor(), Color.NULL);
        assertNotSame(d1.getValue(), d.getValue());
        assertNotSame(d1.getColor(), d.getColor());
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        d = null;
        System.gc();
        super.tearDown();
    }
}
