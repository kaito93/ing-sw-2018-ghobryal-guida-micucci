
package it.polimi.se2018.test_model;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.model.exception.notValidCellException;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * class Map Tester
 * @author Anton Ghobryal
 */

public class TestMap extends TestCase {
    private Dice d1;
    private Dice d2;
    private Dice d3;
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
        d1 = new Dice();
        d2 = new Dice();
        d3 = new Dice();
        d1.setColor(Color.BLUE);
        d2.setColor(Color.GREEN);
        d3.setColor(Color.BLUE);
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
        d1=null;
        d2=null;
        d3=null;
        cell=null;
        map.finalize();
        map = null;
        game.finalize();
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
        assertEquals(map.getName(), "bellesguard");
        assertNotSame(map.getName(), "Anton");
    }

    /**
     * tests valueAlreadyExistInColumn method in class Map
     */
    public void testValueAlreadyExistInColumn() throws notValidCellException {
        for(int i=0; i<map.numColumn(); i++){
            try {
                assertFalse(map.valueAlreadyExistInColumn(i, 6));
            } catch (notValidCellException e) {
                fail();
            }
        }
        ArrayList<Dice> dices = new ArrayList<>();
        getDicesSameValue(dices);
        assertFalse(map.valueAlreadyExistInColumn(0, 6));
        map.getCell(0, 0).setDice(dices.get(0));
        assertTrue(map.valueAlreadyExistInColumn(0, 6));
        map.getCell(1, 0).setDice(dices.get(0));
        assertTrue(map.valueAlreadyExistInColumn(0, 6));
    }

    /**
     * a helper method that creates two dices
     * @param dices an array list where to save the created dices
     */
    private void getDicesSameValue(ArrayList<Dice> dices) {
        for (int i=0; i<2; i++) {
            dices.add(new Dice());
            try {
                dices.get(i).setValue(6);
            } catch (InvalidValueException e) {
                fail();
            }
        }
    }

    /**
     * tests colorAlreadyExistInColumn method in class Map
     */
    public void testColorAlreadyExistInColumn() throws notValidCellException {
        for(int i=0; i<map.numColumn(); i++){
                try {
                    assertFalse(map.colorAlreadyExistInColumn(i, Color.BLUE));
                } catch (notValidCellException e) {
                    fail();
                }
            }
        ArrayList<Dice> dices = new ArrayList<>();
        getDicesSameColor(dices);
        assertFalse(map.colorAlreadyExistInColumn(0, Color.BLUE));
        map.getCell(0, 0).setDice(dices.get(0));
        assertTrue(map.colorAlreadyExistInColumn(0, Color.BLUE));
        map.getCell(1, 0).setDice(dices.get(0));
        assertTrue(map.colorAlreadyExistInColumn(0, Color.BLUE));
    }

    /**
     * tests valueAlreadyExistInRow method in class Map
     */
    public void testValueAlreadyExistInRow() throws notValidCellException {
        for(int i=0; i<map.numRow(); i++){
            try {
                assertFalse(map.valueAlreadyExistInRow(i, 6));
            } catch (notValidCellException e) {
                fail();
            }
        }
        ArrayList<Dice> dices = new ArrayList<>();
        getDicesSameValue(dices);
        assertFalse(map.valueAlreadyExistInRow(0, 6));
        map.getCell(0, 0).setDice(dices.get(0));
        assertTrue(map.valueAlreadyExistInRow(0, 6));
        map.getCell(0, 1).setDice(dices.get(0));
        assertTrue(map.valueAlreadyExistInRow(0, 6));
    }

    /**
     * tests colorAlreadyExistInRow method in class Map
     */
    public void testColorAlreadyExistInRow() throws notValidCellException {
        for(int i=0; i<map.numRow(); i++){
            try {
                assertFalse(map.colorAlreadyExistInRow(i, Color.BLUE));
            } catch (notValidCellException e) {
                fail();
            }
        }
        ArrayList<Dice> dices = new ArrayList<>();
        getDicesSameColor(dices);
        assertFalse(map.colorAlreadyExistInRow(0, Color.BLUE));
        map.getCell(0, 0).setDice(dices.get(0));
        assertTrue(map.colorAlreadyExistInRow(0, Color.BLUE));
        map.getCell(0, 1).setDice(dices.get(0));
        assertTrue(map.colorAlreadyExistInRow(0, Color.BLUE));
    }

    /**
     * a helper method that creates two dices
     * @param dices an array list where to save the created dices
     */
    private void getDicesSameColor(ArrayList<Dice> dices) {
        for (int i=0; i<2; i++) {
            dices.add(new Dice());
            try {
                dices.get(i).setColor(Color.BLUE);
            } catch (NullPointerException e) {
                fail();
            }
        }
    }

    /**
     * tests isBorderEmpty method in class Map
     */
    public void testIsBorderEmpty(){
        assertTrue(map.isBorderEmpty());
        assertTrue(map.posDice(d1, 1, 4));
        assertFalse(map.isBorderEmpty());
        assertTrue(map.posDice(d2, 0, 3));
        assertFalse(map.isBorderEmpty());
    }

    /**
     * tests isAdjacentDice method in class Map
     */
    public void testIsAdjacentDice(){
        assertFalse(map.isAdjacentDice(0, 0));
        map.posDice(d1, 0, 0);
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
            d1.setValue(6);
            d2.setValue(5);
        } catch (InvalidValueException e) {
            fail();
        }
        assertFalse(map.diceCompatibleValueCell(0, 1, d2.getValue()));
        assertTrue(map.diceCompatibleValueCell(0, 1, d1.getValue()));
    }

    /**
     * tests diceCompatibleColorCell method in class Map
     */
    public void testDiceCompatibleColorCell(){
        assertFalse(map.diceCompatibleColorCell(0, 0, d2.getColor()));
        assertTrue(map.diceCompatibleColorCell(0, 0, d1.getColor()));
    }

    /**
     * tests posDice method in class Map
     */
    public void testPosDice(){
        assertFalse(map.posDice(d1, 1, 3));
        assertFalse(map.posDice(d1, map.numRow()-1, map.numColumn()-1));
        assertTrue(map.posDice(d1, 0, 0));
        assertFalse(map.posDice(d3, 1, 0));
        assertTrue(map.posDice(d2, 1, 0));
    }

    /**
     * tests isEmptyCell method in class Map
     */
    public void testIsEmptyCell(){
        assertTrue(map.isEmptyCell(0,0));
        map.posDice(d1, 0, 0);
        assertFalse(map.isEmptyCell(0,0));
    }

    /**
     * tests emptyCells method in class Map
     */
    public void testEmptyCells(){
        assertEquals(map.emptyCells(), map.numRow()*map.numColumn());
        assertNotSame(map.emptyCells(), (map.numRow()*map.numColumn())-1);
        map.posDice(d1, 0, 0);
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
    public void testgetErrorBool(){
        assertNotNull(map.getErrorBool());
    }
}