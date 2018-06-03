package it.polimi.se2018.test_model;


import it.polimi.se2018.model.Color;
import junit.framework.TestCase;

/**
 * @author Anton Ghobryal
 */

public class TestColor extends TestCase {

    /**
     * class Constructor
     * @param name test method name
     */
    public TestColor(String name){
        super(name);
    }

    /**
     * setting up the test data
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * test equalsColor method in Color Enumeration
     */
    public void testEqualsColor(){
        assertTrue(Color.BLUE.equalsColor(Color.BLUE));
        assertFalse(Color.BLUE.equalsColor(Color.GREEN));
    }

    /**
     * test parseInput method in Color Enumeration
     */
    public void testParseInput(){
        assertEquals(Color.parseInput("blue"), Color.BLUE);
        assertNotSame(Color.parseInput("blue"), Color.GREEN);
    }

    /**
     * test toString method in Color Enumeration
     */
    public void testToString(){
        assertEquals(Color.BLUE.toString(), "blue");
        assertNotSame(Color.BLUE.toString(), Color.BLUE);
        assertNotSame(Color.BLUE.toString(), "Blue");
        assertNotSame(Color.BLUE.toString(), "BLUE");
    }

    /**
     * tearing down the test data
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        System.gc();
        super.tearDown();
    }
}
