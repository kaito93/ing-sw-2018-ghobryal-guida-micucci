package it.polimi.se2018.test_model.cell;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.server.model.cell.ValueCell;
import junit.framework.TestCase;

/**
 * class CellBuilder Tester
 * @author Anton Ghobryal
 */
public class TestCellBuilder extends TestCase {

    private Cell valueCell;

    /**
     * class Constructor
     * @param name test method name
     */
    public TestCellBuilder(String name){
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
     * test get and set Dice methods in CellBuilder class
     */
    public void testGetSetDice(){
        assertNull(valueCell.getDice());
        valueCell.setDice(new Dice());
        assertNotNull(valueCell.getDice());
    }

    /**
     * test get and set Number methods in CellBuilder class
     */
    public void testGetSetNumber(){
        assertEquals(valueCell.getNumberCell(), 0);
        valueCell.setNumberCell(1);
        assertNotSame(valueCell.getNumberCell(), 0);
        assertEquals(valueCell.getNumberCell(), 1);
    }
}
