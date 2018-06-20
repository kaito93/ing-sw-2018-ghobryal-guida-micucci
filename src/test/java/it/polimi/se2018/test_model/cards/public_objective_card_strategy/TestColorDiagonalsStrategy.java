package it.polimi.se2018.test_model.cards.public_objective_card_strategy;


import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Game;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.controller.public_objective_card_strategy.ColorDiagonalsStrategy;
import junit.framework.TestCase;

import java.util.List;

/**
 * class ColorDiagonalsStrategy Tester
 * @author Anton Ghobryal
 */
public class TestColorDiagonalsStrategy extends TestCase {
    private Map map;
    private Card card;
    private Game game;
    private List<Map> maps;
    private Dice y1, y2, y3, y4, y5, g2, g5, g1, g3;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestColorDiagonalsStrategy(String name){
        super(name);
    }

    /**
     * setup a new Public Objective Card with Color Diagonals Strategy, a new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        maps = game.getMaps();
        map = game.getThatMap("Virtus");
        card = new PublicObjectiveCard("Color Diagonals", "Test", 5, new ColorDiagonalsStrategy());
        y1 = new Dice();
        y1.setColor(Color.YELLOW);
        y1.setValue(1);
        y2 = new Dice();
        y2.setColor(Color.YELLOW);
        y2.setValue(2);
        y3 = new Dice();
        y3.setColor(Color.YELLOW);
        y3.setValue(3);
        y4 = new Dice();
        y4.setColor(Color.YELLOW);
        y4.setValue(4);
        y5 = new Dice();
        y5.setColor(Color.YELLOW);
        y5.setValue(5);
        g2 = new Dice();
        g2.setColor(Color.GREEN);
        g2.setValue(2);
        g5 = new Dice();
        g5.setColor(Color.GREEN);
        g5.setValue(5);
        g1 = new Dice();
        g1.setColor(Color.GREEN);
        g1.setValue(1);
        g3 = new Dice();
        g3.setColor(Color.GREEN);
        g3.setValue(3);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        y1 =null;
        y2 =null;
        y3 =null;
        y4 =null;
        y5 =null;
        g2 =null;
        g5 =null;
        g1 =null;
        g3 =null;
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
     * tests search method in ColorDiagonalsStrategy class
     */
    public void testSearch(){
        assertEquals(card.search(map), 0);
        assertTrue(map.posDice(y1, 3, 2));
        assertTrue(map.posDice(y3, 2, 1));
        assertTrue(map.posDice(y5, 1, 0));
        assertTrue(map.posDice(g2, 3, 1));
        assertTrue(map.posDice(g5, 2, 2));
        assertTrue(map.posDice(g1, 1, 3));
        assertTrue(map.posDice(g3, 0, 4));
        assertNotSame(card.search(map), 0);
        assertEquals(card.search(map), 7);
        map.removeDiceMap(3, 2);
        map.removeDiceMap(2, 1);
        map.removeDiceMap(1, 0);
        map.removeDiceMap(3, 1);
        map.removeDiceMap(2, 2);
        map.removeDiceMap(1, 3);
        map.removeDiceMap(0, 4);
    }
}
