package it.polimi.se2018.test_model.cell;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ValueCell;
import junit.framework.TestCase;

/**
 * class Cell Tester
 * @author Anton Ghobryal
 */
public class TestValueCell extends TestCase {

    private Cell valueCell;
    /**
     * class Constructor
     * @param name test method name
     */
    public TestValueCell(String name){
        super(name);
    }

    /**
     * create a value cell
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        valueCell = new ValueCell();
        super.setUp();
    }

    /**
     * tearing down the test data
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        valueCell=null;
        System.gc();
        super.tearDown();
    }

    /**
     * test get and set Value methods in ValueCell class
     */
    public void testGetSetValue(){
        assertEquals(valueCell.getValue(), 0);
        valueCell.setValue(1);
        assertNotSame(valueCell.getValue(), 0);
        assertEquals(valueCell.getValue(), 1);
    }

    /**
     * test get and set Color methods in ValueCell class
     */
    public void testGetSetColor(){
        assertEquals(valueCell.getColor(), Color.NULL);
        valueCell.setColor(Color.BLUE);
        assertNotSame(valueCell.getColor(), Color.BLUE);
        assertEquals(valueCell.getColor(), Color.NULL);
    }


}
