package it.polimi.se2018.test_model.cell;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ColoredCell;
import junit.framework.TestCase;

/**
 * class Cell Tester
 * @author Anton Ghobryal
 */
public class TestColoredCell extends TestCase {
    private Cell colorCell;
    /**
     * class Constructor
     * @param name test method name
     */
    public TestColoredCell(String name){
        super(name);
    }

    /**
     * create a colored cell
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        colorCell = new ColoredCell();
        super.setUp();
    }

    /**
     * tearing down the test data
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        colorCell=null;
        System.gc();
        super.tearDown();
    }

    /**
     * test get and set Value methods in ValueCell class
     */
    public void testGetSetValue(){
        assertEquals(colorCell.getValue(), 0);
        colorCell.setValue(1);
        assertEquals(colorCell.getValue(), 0);
        assertNotSame(colorCell.getValue(), 1);
    }

    /**
     * test get and set Color methods in ValueCell class
     */
    public void testGetSetColor(){
        assertEquals(colorCell.getColor(), Color.NULL);
        colorCell.setColor(Color.BLUE);
        assertEquals(colorCell.getColor(), Color.BLUE);
        assertNotSame(colorCell.getColor(), Color.NULL);
    }
}
