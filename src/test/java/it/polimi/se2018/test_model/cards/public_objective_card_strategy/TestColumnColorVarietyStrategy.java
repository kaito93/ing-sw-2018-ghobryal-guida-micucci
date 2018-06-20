package it.polimi.se2018.test_model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ColumnColorVarietyStrategy;
import junit.framework.TestCase;

import java.util.List;

/**
 * class ColorVarietyStrategy Tester
 * @author Anton Ghobryal
 */
public class TestColumnColorVarietyStrategy extends TestCase {
    private Map map;
    private Card card;
    private Game game;
    private List<Map> maps;
    private Dice g6, y5, b1, r2, g1, r3, y6, p5, b2;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestColumnColorVarietyStrategy(String name){
        super(name);
    }

    /**
     * setup a new Public Objective Card with Color Variety Strategy, a new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        maps = game.getMaps();
        map = game.getThatMap("Batllo");
        card = new PublicObjectiveCard("Column Color Diagonals", "Test", 5, new ColumnColorVarietyStrategy());
        g6 = new Dice();
        g6.setColor(Color.GREEN);
        g6.setValue(6);
        b1 = new Dice();
        b1.setColor(Color.BLUE);
        b1.setValue(1);
        r2 = new Dice();
        r2.setColor(Color.RED);
        r2.setValue(2);
        y5 = new Dice();
        y5.setColor(Color.YELLOW);
        y5.setValue(5);
        r3 = new Dice();
        r3.setColor(Color.RED);
        r3.setValue(3);
        y6 = new Dice();
        y6.setColor(Color.YELLOW);
        y6.setValue(6);
        g1 = new Dice();
        g1.setColor(Color.GREEN);
        g1.setValue(1);
        p5 = new Dice();
        p5.setColor(Color.PURPLE);
        p5.setValue(5);
        b2 = new Dice();
        b2.setColor(Color.BLUE);
        b2.setValue(2);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        r2 =null;
        r3 =null;
        p5 =null;
        y5 =null;
        y6 =null;
        g6 =null;
        g1 =null;
        b1 =null;
        b2=null;
        card=null;
        try {
            for(Map map1: maps)
                map1.finalize();
            map.finalize();
            game.finalize();
        } catch (Throwable throwable) {
            //salta
        }
        System.gc();
        super.tearDown();
    }

    /**
     * tests search method in ColorVarietyStrategy class
     */
    public void testSearch(){
        assertEquals(card.search(map), 0);
        assertTrue(map.posDice(r2, 3, 2));
        assertTrue(map.posDice(y5, 2, 2));
        assertTrue(map.posDice(b1, 1, 2));
        assertTrue(map.posDice(g6, 0, 2));
        assertNotSame(card.search(map), 0);
        assertEquals(card.search(map), card.getScore());
        assertTrue(map.posDice(b2, 0, 1));
        assertTrue(map.posDice(p5, 0, 0));
        assertTrue(map.posDice(y6, 1, 0));
        assertTrue(map.posDice(r3, 2, 0));
        assertTrue(map.posDice(g1, 3, 0));
        assertNotSame(card.search(map), card.getScore());
        assertEquals(card.search(map), card.getScore()*2);
        map.removeDiceMap(3, 2);
        map.removeDiceMap(2, 2);
        map.removeDiceMap(1, 2);
        map.removeDiceMap(0, 2);
        map.removeDiceMap(0, 1);
        map.removeDiceMap(0, 0);
        map.removeDiceMap(1, 0);
        map.removeDiceMap(2, 0);
        map.removeDiceMap(3, 0);
    }
}
