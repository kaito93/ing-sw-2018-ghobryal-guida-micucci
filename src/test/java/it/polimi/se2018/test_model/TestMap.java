
package it.polimi.se2018.test_model;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.model.exception.notValidCellException;
import junit.framework.TestCase;

/**
 * class Map Tester
 * @author Anton Ghobryal
 */

public class TestMap extends TestCase {
    private Dice b1;
    private Dice g1;
    private Dice b2;
    private Game game;
    private Map map;
    private Cell[][] cell;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestMap(String name){
        super(name);
    }

    /**
     * setup a new Map and some dices
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        b1 = new Dice();
        g1 = new Dice();
        b2 = new Dice();
        b1.setColor(Color.BLUE);
        b1.setValue(1);
        g1.setColor(Color.GREEN);
        g1.setValue(1);
        b2.setColor(Color.BLUE);
        b2.setValue(2);
        game = new Game();
        map = game.getThatMap("bellesguard");
        cell=map.getCells();
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        b1 =null;
        g1 =null;
        b2 =null;
        cell=null;
        try {
            map.finalize();
            game.finalize();
        } catch (Throwable throwable) {
            //salta
        }
        map = null;
        game= null;
        System.gc();
        super.tearDown();
    }

    /**
     * tests getDifficultyLevel method in class Map
     */
    public void testGetDifficultyLevel(){
        assertEquals(map.getDifficultyLevel(), 3);
        assertFalse(map.getDifficultyLevel()!=3);
    }

    /**
     * tests numColumn method in class Map
     */
    public void testNumColumn(){
        assertEquals(map.numColumn(), 5);
        assertFalse(map.numColumn()!=5);
    }

    /**
     * tests numRow method in class Map
     */
    public void testNumRow(){
        assertEquals(map.numRow(), 4);
        assertFalse(map.numRow()!=4);
    }

    /**
     * tests getCell method in class Map
     */
    public void testGetCell(){
        try {
            assertNotNull(map.getCell(0,0));
            assertNotNull(map.getCell(3,4));
            assertNotNull(map.getCell(2,3));
        }catch (notValidCellException e){
            fail();
        }
        try {
            assertNotNull(map.getCell(-1,0));
            fail();
        }catch (notValidCellException e){
            testNumRow();
        }
        try {
            assertNotNull(map.getCell(0,5));
            fail();
        }catch (notValidCellException e){
            testNumColumn();
        }
        try {
            assertNotNull(map.getCell(4,0));
            fail();
        }catch (notValidCellException e){
            testNumRow();
        }
        try {
            assertNotNull(map.getCell(0, -1));
            fail();
        }catch (notValidCellException e){
            testNumColumn();
        }
    }

    /**
     * tests getName method in class Map
     */

    public void testGetName(){
        assertEquals(map.getName().toLowerCase(), "bellesguard");
        assertNotSame(map.getName().toLowerCase(), "Anton");
    }

    /**
     * tests isBorderEmpty method in class Map
     */
    public void testIsBorderEmpty(){
        assertTrue(map.isBorderEmpty());
        assertTrue(map.posDice(b1, 1, 4));
        assertFalse(map.isBorderEmpty());
        assertTrue(map.posDice(g1, 0, 3));
        assertFalse(map.isBorderEmpty());
    }

    /**
     * tests isAdjacentDice method in class Map
     */
    public void testIsAdjacentDice(){
        assertFalse(map.isAdjacentDice(0, 0));
        map.posDice(b1, 0, 0);
        assertTrue(map.isAdjacentDice(1,1));
        assertTrue(map.isAdjacentDice(1,0));
        assertTrue(map.isAdjacentDice(0,1));
        assertFalse(map.isAdjacentDice(3,3));
        assertFalse(map.isAdjacentDice(map.numRow()-1,0));
        assertFalse(map.isAdjacentDice(0,map.numColumn()-1));
        assertFalse(map.isAdjacentDice(map.numRow()-1,map.numColumn()-1));
    }

    /**
     * tests diceCompatibleValueCell method in class Map
     */
    public void testDiceCompatibleValueCell(){
        try {
            b1.setValue(6);
            g1.setValue(5);
        } catch (InvalidValueException e) {
            fail();
        }
        assertFalse(map.diceCompatibleValueCell(0, 1, g1.getValue()));
        assertTrue(map.diceCompatibleValueCell(0, 1, b1.getValue()));
    }

    /**
     * tests diceCompatibleColorCell method in class Map
     */
    public void testDiceCompatibleColorCell(){
        assertFalse(map.diceCompatibleColorCell(0, 0, g1.getColor()));
        assertTrue(map.diceCompatibleColorCell(0, 0, b1.getColor()));
    }

    /**
     * tests posDice method in class Map
     */
    public void testPosDice(){
        assertFalse(map.posDice(b1, 1, 3));
        assertFalse(map.posDice(b1, map.numRow()-1, map.numColumn()-1));
        assertTrue(map.posDice(b1, 0, 0));
        assertFalse(map.posDice(b2, 1, 0));
        assertFalse(map.posDice(g1, 1, 0));
    }

    /**
     * tests isEmptyCell method in class Map
     */
    public void testIsEmptyCell(){
        assertTrue(map.isEmptyCell(0,0));
        map.posDice(b1, 0, 0);
        assertFalse(map.isEmptyCell(0,0));
    }

    /**
     * tests emptyCells method in class Map
     */
    public void testEmptyCells(){
        assertEquals(map.emptyCells(), map.numRow()*map.numColumn());
        assertNotSame(map.emptyCells(), (map.numRow()*map.numColumn())-1);
        map.posDice(b1, 0, 0);
        assertEquals(map.emptyCells(), (map.numRow()*map.numColumn())-1);
        assertNotSame(map.emptyCells(), map.numRow()*map.numColumn());
    }

    /**
     * tests getCells method in class Map
     */
    public void testGetCells(){
        assertEquals(map.getCells(), cell);
        assertNotNull(map.getCells());
    }

    /**
     * tests getErrorBool method in class Map
     */
    public void testGetErrorBool(){
        assertNotNull(Map.getErrorBool());
    }

    /**
     * tests removeDiceMap method in class Map
     */
    public void testRemoveDiceMap(){
        map.posDice(b1, 0, 0);
        assertFalse(map.isEmptyCell(0,0));
        map.removeDiceMap(0, 0);
        assertTrue(map.isEmptyCell(0,0));
    }

    /**
     * tests existDice method in class Map
     */
    public void testExistDice(){
        assertFalse(map.existDice(b1, 0, 0));
        assertTrue(map.posDice(b1, 0, 0));
        assertTrue(map.existDice(b1, 0, 0));
        assertFalse(map.existDice(g1, 0, 0));
    }

    /**
     * tests isAdjacentColor method in class Map
     */
    public void testIsAdjacentColor(){
        try {
            assertFalse(map.isAdjacentColor(map.numRow()-1, map.numColumn()-1, Color.GREEN));
            assertTrue(map.posDice(g1, map.numRow()-1, map.numColumn()-1));
            assertFalse(map.isAdjacentColor(map.numRow()-2, map.numColumn()-1, Color.BLUE));
            assertTrue(map.isAdjacentColor(map.numRow()-2, map.numColumn()-1, Color.GREEN));
            assertTrue(map.posDice(b2, map.numRow()-2, map.numColumn()-1));
            assertTrue(map.isAdjacentColor(map.numRow()-3, map.numColumn()-1, Color.BLUE));
        } catch (notValidCellException e) {
            fail();
        }
    }

    /**
     * tests isAdjacentValue method in class Map
     */
    public void testIsAdjacentValue(){
        try {
            assertFalse(map.isAdjacentValue(map.numRow()-1, map.numColumn()-1, 1));
            assertTrue(map.posDice(g1, map.numRow()-1, map.numColumn()-1));
            assertFalse(map.isAdjacentValue(map.numRow()-2, map.numColumn()-1, 2));
            assertTrue(map.isAdjacentValue(map.numRow()-2, map.numColumn()-1, 1));
            assertTrue(map.posDice(b2, map.numRow()-2, map.numColumn()-1));
            assertTrue(map.isAdjacentValue(map.numRow()-3, map.numColumn()-1, 2));
            assertFalse(map.posDice(b1, map.numRow()-1, map.numColumn()-2));
            assertTrue(map.isAdjacentValue(map.numRow()-1, map.numColumn()-2, 1));
        } catch (notValidCellException e) {
            fail();
        }
    }

    /**
     * tests isCellValid method in class Map
     */
    public void testIsCellValid(){
        map.posDice(b1, 0, 0);
        assertFalse(map.isCellValid(b2, 0, 0)); //c'è già un dado
        assertTrue(map.isCellValid(b2, 1, 0)); //blank cell
        assertFalse(map.isCellValid(b2, 0, 1)); //value cell con valore diverso
        assertTrue(map.isCellValid(b2, 2, 3)); //value cell con lo stesso valore
        assertTrue(map.isCellValid(b2, 1, 2)); //colored cell con lo stesso colore
        assertFalse(map.isCellValid(b2, 0, 4)); //colored cell con colore diverso
    }
}