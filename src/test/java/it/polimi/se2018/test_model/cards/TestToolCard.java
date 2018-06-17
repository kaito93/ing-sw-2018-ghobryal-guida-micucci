package it.polimi.se2018.test_model.cards;


import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.GrozingPliers;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import junit.framework.TestCase;

/**
 * class ToolCard Tester
 * @author Anton Ghobryal
 */
public class TestToolCard extends TestCase {

    private Card card;
    private ToolCardStrategy strategy;
    private Player player;
    private Game game;
    private Map map;
    private Dice d1, d2, d3;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestToolCard(String name){
        super(name);
    }

    /**
     * setup strategy new Private Objective Card, strategy new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        strategy = new GrozingPliers();
        card = new ToolCard("Test", "Test", 0, Color.BLUE, strategy);
        player = new Player("Anton");
        map = game.getThatMap("bellesguard");
        player.setMap(map);
        player.setFavorSig();
        d1 = new Dice();
        d1.setValue(1);
        d1.setColor(Color.BLUE);
        d2 = new Dice();
        d2.setValue(2);
        d2.setColor(Color.GREEN);
        d3 = new Dice();
        d3.setValue(3);
        d3.setColor(Color.YELLOW);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        game.finalize();
        game=null;
        map.finalize();
        map=null;
        card=null;
        strategy=null;
        player=null;
        d1=null;
        d2=null;
        d3=null;
        System.gc();
        super.tearDown();
    }

    /**
     * tests isUsed method in ToolCard class
     */
    public void testIsUsed(){
        assertFalse(card.isUsed());
    }

    /**
     * tests getId method in ToolCard class
     */
    public void testGetId(){
        assertEquals(card.getId(), 0);
    }

    /**
     * tests getColor method in ToolCard class
     */
    public void testGetColor(){
        assertEquals(card.getColor(), Color.BLUE);
    }

    /**
     * tests getStrategy method in ToolCard class
     */
    public void testGetStrategy(){
        assertEquals(card.getStrategy(), strategy);
    }

    /**
     * tests useTool method in ToolCard class
     */
    public void testUseTool(){
        assertEquals(player.getFavSig(), map.getDifficultyLevel());
        assertTrue(card.useTool(player, d1, 0, 0, null, false, 0, 0, null, null, null, 0));
        assertNotSame(player.getFavSig(), map.getDifficultyLevel());
        assertEquals(player.getFavSig(), map.getDifficultyLevel()-1);
        assertTrue(card.useTool(player, d2, 1, 0, null, false, 0, 0, null, null, null, 0));
        assertNotSame(player.getFavSig(), map.getDifficultyLevel());
        assertNotSame(player.getFavSig(), map.getDifficultyLevel()-1);
        assertEquals(player.getFavSig(), map.getDifficultyLevel()-3);
        assertFalse(card.useTool(player, d3, 1, 0, null, false, 0, 0, null, null, null, 0));
    }

}
