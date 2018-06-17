package it.polimi.se2018.test_model.cards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * class PrivateObjectiveCard Tester
 * @author Anton Ghobryal
 */
public class TestPrivateObjectiveCard extends TestCase {

    private Map map;
    private Card card;
    private Game game;
    private ArrayList<Map> maps;
    private Dice d1, d2, d3;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestPrivateObjectiveCard(String name){
        super(name);
    }

    /**
     * setup a new Private Objective Card, a new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        game = new Game();
        maps = game.getMaps();
        map = game.getThatMap("bellesguard");
        card = new PrivateObjectiveCard("Test", "Test", Color.BLUE);
        d1 = new Dice();
        d1.setColor(Color.BLUE);
        d1.setValue(1);
        d2 = new Dice();
        d2.setColor(Color.GREEN);
        d2.setValue(3);
        d3 = new Dice();
        d3.setColor(Color.BLUE);
        d3.setValue(1);
        map.posDice(d1, 0, 0);
        map.posDice(d2, 1, 1);
        map.posDice(d3, 1, 2);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        map.removeDiceMap(0, 0);
        map.removeDiceMap(1, 1);
        map.removeDiceMap(1, 2);
        d1=null;
        d2=null;
        d3=null;
        card=null;
        for(Map map1: maps)
            map1.finalize();
        map.finalize();
        game.finalize();
        System.gc();
        super.tearDown();
    }

    /**
     * tests search method in PrivateObjectiveCard class
     */
    public void testSearch(){
        assertEquals(card.search(map), 2);
        assertNotSame(card.search(map), 5);
    }
}
