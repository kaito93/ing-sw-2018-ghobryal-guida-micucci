package it.polimi.se2018.test_model.cards.public_objective_card_strategy;

import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Game;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.controller.public_objective_card_strategy.*;
import junit.framework.TestCase;

import java.util.List;

/**
 * Most public objective cards Tester
 * @author Anton Ghobryal
 */
public class TestSomeCards extends TestCase {
    private Map map;
    private Card card1, card2, card3, card4, card5, card6, card7;
    private Game game;
    private List<Map> maps;
    private Dice g6, y5, b1, r4, g1, r3, y6, p5, b2, r2, p3, g4, p1;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestSomeCards(String name){
        super(name);
    }

    /**
     * setup some new public cards, a new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        maps = game.getMaps();
        map = game.getThatMap("Batllo");
        card1 = new PublicObjectiveCard("Deep Shades", "Test", 5, new DeepShadesStrategy());
        card2 = new PublicObjectiveCard("Medium Shades", "Test", 5, new LightShadesStrategy());
        card3 = new PublicObjectiveCard("Light Shades", "Test", 5, new MediumShadesStrategy());
        card4 = new PublicObjectiveCard("Row Color Variety", "Test", 5, new RowColorVarietyStrategy());
        card5 = new PublicObjectiveCard("Row Shade Variety", "Test", 5, new RowShadeVarietyStrategy());
        card6 = new PublicObjectiveCard("Column Color Variety", "Test", 5, new ColumnColorVarietyStrategy());
        card7 = new PublicObjectiveCard("Column Shade Variety", "Test", 5, new ColumnShadeVarietyStrategy());
        g6 = new Dice();
        g6.setColor(Color.GREEN);
        g6.setValue(6);
        b1 = new Dice();
        b1.setColor(Color.BLUE);
        b1.setValue(1);
        r4 = new Dice();
        r4.setColor(Color.RED);
        r4.setValue(4);
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
        r2 = new Dice();
        r2.setColor(Color.RED);
        r2.setValue(2);
        p3 = new Dice();
        p3.setColor(Color.PURPLE);
        p3.setValue(3);
        g4 = new Dice();
        g4.setColor(Color.GREEN);
        g4.setValue(4);
        p1 = new Dice();
        p1.setColor(Color.PURPLE);
        p1.setValue(1);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        r4 =null;
        r3=null;
        p5=null;
        y5=null;
        y6=null;
        g6=null;
        g1=null;
        b1=null;
        b2=null;
        r2=null;
        p3=null;
        g4=null;
        card1 =null;
        card2=null;
        card3=null;
        card4=null;
        card5=null;
        card6=null;
        card7=null;
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
     * tests search method in most public objective cards class
     */
    public void testSearch(){
        assertEquals(card1.search(map), 0);
        assertEquals(card2.search(map), 0);
        assertEquals(card3.search(map), 0);
        assertEquals(card4.search(map), 0);
        assertEquals(card5.search(map), 0);
        assertEquals(card6.search(map), 0);
        assertEquals(card7.search(map), 0);
        assertTrue(map.posDice(b1, 3, 0));
        assertTrue(map.posDice(g4, 3, 1));
        assertTrue(map.posDice(y6, 2, 2));
        assertTrue(map.posDice(r2, 3, 2));
        assertTrue(map.posDice(y5, 3, 3));
        assertTrue(map.posDice(p3, 3, 4));
        assertTrue(map.posDice(p1, 2, 3));
        assertNotSame(card1.search(map), 0);
        assertEquals(card1.search(map), card1.getScore());
        assertNotSame(card2.search(map), 0);
        assertEquals(card2.search(map), card2.getScore());
        assertNotSame(card3.search(map), 0);
        assertEquals(card3.search(map), card3.getScore());
        assertNotSame(card4.search(map), 0);
        assertEquals(card4.search(map), card4.getScore());
        assertNotSame(card5.search(map), 0);
        assertEquals(card5.search(map), card5.getScore());
        assertEquals(card6.search(map), 0);
        assertEquals(card7.search(map), 0);
        assertTrue(map.posDice(b2, 2, 4));
        assertTrue(map.posDice(p5, 1, 1));
        assertTrue(map.posDice(g1, 1, 4));
        assertTrue(map.posDice(r3, 2, 0));
        assertTrue(map.posDice(r4, 1, 3));
        assertTrue(map.posDice(g6, 0, 3));
        assertNotSame(card1.search(map), card1.getScore());
        assertEquals(card1.search(map), card1.getScore()*2);
        assertNotSame(card2.search(map), card2.getScore());
        assertEquals(card2.search(map), card2.getScore()*2);
        assertNotSame(card3.search(map), card3.getScore());
        assertEquals(card3.search(map), card3.getScore()*2);
        assertEquals(card4.search(map), card4.getScore());
        assertEquals(card5.search(map), card5.getScore());
        assertEquals(card6.search(map), card6.getScore());
        assertEquals(card7.search(map), card7.getScore());
        map.removeDiceMap(3, 0);
        map.removeDiceMap(3, 1);
        map.removeDiceMap(2, 2);
        map.removeDiceMap(3, 2);
        map.removeDiceMap(3, 3);
        map.removeDiceMap(3, 4);
        map.removeDiceMap(2, 4);
        map.removeDiceMap(1, 1);
        map.removeDiceMap(1, 4);
        map.removeDiceMap(2, 0);
        map.removeDiceMap(1, 3);
        map.removeDiceMap(0, 3);
    }
}
