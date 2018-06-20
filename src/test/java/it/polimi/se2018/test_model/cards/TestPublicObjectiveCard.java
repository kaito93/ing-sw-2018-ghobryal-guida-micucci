package it.polimi.se2018.test_model.cards;

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
 * class PublicObjectiveCard Tester
 * @author Anton Ghobryal
 */
public class TestPublicObjectiveCard extends TestCase {
    private Map map;
    private Card card;
    private Game game;
    private List<Map> maps;
    private Dice d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestPublicObjectiveCard(String name){
        super(name);
    }

    /**
     * setup a new Public Objective Card, a new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        maps = game.getMaps();
        map = game.getThatMap("bellesguard");
        card = new PublicObjectiveCard("Column - Color Variety", "Test", 5, new ColumnColorVarietyStrategy());
        d1 = new Dice();
        d1.setColor(Color.BLUE);
        d1.setValue(1);
        d2 = new Dice();
        d2.setColor(Color.GREEN);
        d2.setValue(2);
        d3 = new Dice();
        d3.setColor(Color.YELLOW);
        d3.setValue(3);
        d4 = new Dice();
        d4.setColor(Color.PURPLE);
        d4.setValue(5);
        d5 = new Dice();
        d5.setColor(Color.GREEN);
        d5.setValue(6);
        d6 = new Dice();
        d6.setColor(Color.YELLOW);
        d6.setValue(3);
        d7 = new Dice();
        d7.setColor(Color.PURPLE);
        d7.setValue(5);
        d8 = new Dice();
        d8.setColor(Color.PURPLE);
        d8.setValue(5);
        d9 = new Dice();
        d9.setColor(Color.BLUE);
        d9.setValue(1);
        d10 = new Dice();
        d10.setColor(Color.GREEN);
        d10.setValue(6);
        d11 = new Dice();
        d11.setColor(Color.YELLOW);
        d11.setValue(2);
        map.posDice(d1, 0, 0);
        map.posDice(d2, 1, 0);
        map.posDice(d3, 2, 0);
        map.posDice(d4, 3, 0);
        map.posDice(d5, 0, 1);
        map.posDice(d6, 1, 1);
        map.posDice(d7, 2, 1);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        map.removeDiceMap(0, 0);
        map.removeDiceMap(1, 0);
        map.removeDiceMap(2, 0);
        map.removeDiceMap(3, 0);
        map.removeDiceMap(0, 1);
        map.removeDiceMap(1, 1);
        map.removeDiceMap(2, 1);
        d1=null;
        d2=null;
        d3=null;
        d4=null;
        d5=null;
        d6=null;
        d7=null;
        d8=null;
        d9=null;
        d10=null;
        d11=null;
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
     * tests search method in PublicObjectiveCard class
     */
    public void testSearch(){
        assertEquals(card.search(map), card.getScore());
        assertNotSame(card.search(map), 2*card.getScore());
        assertTrue(map.posDice(d8, 0, 2));
        assertTrue(map.posDice(d9, 1, 2));
        assertTrue(map.posDice(d10, 2, 2));
        assertTrue(map.posDice(d11, 3, 2));
        assertEquals(card.search(map), 2*card.getScore());
        assertNotSame(card.search(map), card.getScore());
        map.removeDiceMap(3, 2);
        map.removeDiceMap(0, 2);
        map.removeDiceMap(1, 2);
        map.removeDiceMap(2, 2);
    }
}
