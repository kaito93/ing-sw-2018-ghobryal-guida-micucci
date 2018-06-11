package it.polimi.se2018.test_model.cell;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.cell.ValueCell;
import junit.framework.TestCase;

/**
 * class Cell Tester
 * @author Anton Ghobryal
 */
public class TestCell extends TestCase {

    private Cell valueCell;

    /**
     * class Constructor
     * @param name test method name
     */
    public TestCell(String name){
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
     * test get and set Dice methods in Cell class
     */
    public void testGetSetDice(){
        assertNull(valueCell.getDice());
        valueCell.setDice(new Dice());
        assertNotNull(valueCell.getDice());
    }

    /**
     * test get and set Number methods in Cell class
     */
    public void testGetSetNumber(){
        assertEquals(valueCell.getNumberCell(), 0);
        valueCell.setNumberCell(1);
        assertNotSame(valueCell.getNumberCell(), 0);
        assertEquals(valueCell.getNumberCell(), 1);
    }
}
