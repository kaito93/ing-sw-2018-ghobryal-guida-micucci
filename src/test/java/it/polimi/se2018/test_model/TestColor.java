package it.polimi.se2018.test_model;


import it.polimi.se2018.model.Color;
import junit.framework.TestCase;
import org.testng.annotations.Test;

public class TestColor extends TestCase {

    public TestColor(String name){
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testEqualsColor(){
        assertTrue(Color.BLUE.equalsColor(Color.BLUE));
        assertFalse(Color.BLUE.equalsColor(Color.GREEN));
    }

    @Test
    public void testParseInput(){
        assertTrue(Color.parseInput("blue") instanceof Color);
        assertEquals(Color.parseInput("blue"), Color.BLUE);
        assertNotSame(Color.parseInput("blue"), Color.GREEN);
    }

    @Test
    public void testToString(){
        assertEquals(Color.BLUE.toString(), "blue");
        assertNotSame(Color.BLUE.toString(), Color.BLUE);
        assertNotSame(Color.BLUE.toString(), "Blue");
        assertNotSame(Color.BLUE.toString(), "BLUE");
    }

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        super.tearDown();
    }
}
