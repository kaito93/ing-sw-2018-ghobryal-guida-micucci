package it.polimi.se2018.test_model.cell;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cell.BlankCell;
import it.polimi.se2018.model.cell.Cell;
import junit.framework.TestCase;

/**
 * class Cell Tester
 * @author Anton Ghobryal
 */
public class TestBlankCell extends TestCase {
    private Cell blankCell;
    /**
     * class Constructor
     * @param name test method name
     */
    public TestBlankCell(String name){
        super(name);
    }

    /**
     * create a colored cell
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        blankCell = new BlankCell();
        super.setUp();
    }

    /**
     * tearing down the test data
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        blankCell =null;
        System.gc();
        super.tearDown();
    }

    /**
     * test get and set Value methods in ValueCell class
     */
    public void testGetSetValue(){
        assertEquals(blankCell.getValue(), 0);
        blankCell.setValue(1);
        assertEquals(blankCell.getValue(), 0);
        assertNotSame(blankCell.getValue(), 1);
    }

    /**
     * test get and set Color methods in ValueCell class
     */
    public void testGetSetColor(){
        assertEquals(blankCell.getColor(), Color.NULL);
        blankCell.setColor(Color.BLUE);
        assertEquals(blankCell.getColor(), Color.NULL);
        assertNotSame(blankCell.getColor(), Color.BLUE);
    }
}