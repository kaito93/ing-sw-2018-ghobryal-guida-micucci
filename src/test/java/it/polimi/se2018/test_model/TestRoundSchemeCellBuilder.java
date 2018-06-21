package it.polimi.se2018.test_model;

import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * class RoundSchemeCell Tester
 * @author Anton Ghobryal
 */

public class TestRoundSchemeCellBuilder extends TestCase {
    private RoundSchemeCell roundSchemeCell;
    private ArrayList<Dice> roundSchemeCellDices;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestRoundSchemeCellBuilder(String name){
        super(name);
    }

    /**
     * setup a new round Scheme CellBuilder and a new dice set
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        roundSchemeCell = new RoundSchemeCell();
        roundSchemeCellDices = new ArrayList<>();
        for(int i=0; i<4; i++){
            Dice d = new Dice();
            d.setColor(Color.BLUE);
            roundSchemeCellDices.add(d);
        }
        roundSchemeCell.setDices(roundSchemeCellDices);
        super.setUp();
    }

    /**
     * tests getRestOfStock in class RoundSchemeCell
     */
    public void testGetRestOfStock(){
        assertEquals(roundSchemeCell.getRestOfStock(), roundSchemeCellDices);
    }

    /**
     * tests Remove in class RoundSchemeCell
     */
    public void testRemoveDice(){
        assertSame(roundSchemeCell.getRestOfStock().get(0), roundSchemeCellDices.get(0));
        roundSchemeCell.removeDice(roundSchemeCellDices.get(0));
        assertFalse(roundSchemeCell.getRestOfStock().contains(roundSchemeCellDices.get(0)));
    }

    /**
     * tearing down the test data
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        for(int i=0; i<4; i++){
            roundSchemeCell.removeDice(roundSchemeCellDices.remove(0));
        }
        roundSchemeCell = null;
        roundSchemeCellDices = null;
        System.gc();
        super.tearDown();
    }

}
