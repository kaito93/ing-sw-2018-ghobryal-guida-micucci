package it.polimi.se2018.test_model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * classes CorkbackedStraightedge & GlazingHummer & Lathekin & LensCutter Tester
 * @author Anton Ghobryal
 */
public class TestSomeCards2 extends TestCase {
    private Card card1, card2, card3, card4;
    private ToolCardStrategy strategy1, strategy2, strategy3, strategy4;
    private Player player;
    private Game game;
    private Map map;
    private Dice b1, g2, g3;
    private List<Dice> stock;
    private RoundSchemeCell[] roundSchemeCells;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestSomeCards2(String name){
        super(name);
    }

    /**
     * setup strategy1 new Private Objective Card, strategy1 new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        strategy1 = new CorkbackedStraightedge();
        strategy2 = new GlazingHammer();
        strategy3 = new Lathekin();
        strategy4 = new LensCutter();
        card1 = new ToolCard("Cork-backed Straightedge", "Test", 0, Color.BLUE, strategy1);
        card2 = new ToolCard("Flux Brush", "Test", 0, Color.PURPLE, strategy2);
        card3 = new ToolCard("Lathekin", "Test", 0, Color.YELLOW, strategy3);
        card4 = new ToolCard("Lens Cutter", "Test", 0, Color.YELLOW, strategy4);
        player = new Player("Anton");
        map = game.getThatMap("virtus");
        roundSchemeCells = game.getRoundSchemeMap();
        player.setMap(map);
        player.setFavorSig();
        b1 = new Dice();
        b1.setValue(1);
        b1.setColor(Color.BLUE);
        g2 = new Dice();
        g2.setValue(2);
        g2.setColor(Color.GREEN);
        g3 = new Dice();
        g3.setValue(3);
        g3.setColor(Color.GREEN);
        stock = new ArrayList<>();
        stock.add(b1);
        roundSchemeCells[0].getRestOfStock().add(g3);
        map.posDice(b1, 3, 2);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        try {
            game.finalize();
            map.finalize();
        } catch (Throwable throwable) {
            //salta
        }
        game=null;
        map=null;
        card1=null;
        strategy1=null;
        card2=null;
        strategy2=null;
        card3=null;
        strategy3=null;
        card4=null;
        strategy4=null;
        player=null;
        roundSchemeCells=null;
        stock.remove(g2);
        stock=null;
        b1=null;
        g2=null;
        g3=null;
        System.gc();
        super.tearDown();
    }

    /**
     * Tests useTool method in CopperFoilBurnisher & GlazingHummer & Lathekin & LensCutter class
     */
    public void testUseTool(){
        //assertTrue(map.posDice(b1, 3, 2));
        assertFalse(card1.useTool(player, g2, 3, 1, null, -1, -1,
                null, null, null, -1));
        assertFalse(card1.useTool(player, g2, 3, 0, null, -1, -1,
                null, null, null, -1));
        assertFalse(card1.useTool(player, g2, 2, 1, null, -1, -1,
                null, null, null, -1));
        assertFalse(card1.useTool(player, g2, 3, 0, null, 3, 2,
                null, null, null, -1));
        assertFalse(card2.useTool(player, null, 1, -1, stock, -1, -1,
                null, null, null, -1));
        assertFalse(card2.useTool(player, null, 2, -1, stock, -1, -1,
                null, null, null, -1));
        assertFalse(card3.useTool(player, null, 1, 0, stock, 2, 0,
                null, null, null, -1));
        assertTrue(card1.useTool(player, g2, map.numRow()-1, map.numColumn()-1, null, -1, -1,
                null, null, null, -1));
        stock.add(g2);
        card3.getStrategy().setRow3(3);
        card3.getStrategy().setColumn3(2);
        card3.getStrategy().setRow4(3);
        card3.getStrategy().setColumn4(4);
        assertFalse(card3.useTool(player, null, 2, 4, stock, 2, 0,
                null, null, null, -1));
        assertTrue(card3.useTool(player, null, 1, 0, stock, 2, 0,
                null, null, null, -1));
        assertFalse(card4.useTool(player, g3, 0, -1, stock, 1, 1,
                g2, roundSchemeCells, null, -1));
        assertFalse(card4.useTool(player, g2, 0, -1, stock, 1, 1,
                g2, roundSchemeCells, null, -1));
        assertTrue(card4.useTool(player, g2, 0, -1, stock, 1, 1,
                g3, roundSchemeCells, null, -1));
        assertTrue(card2.useTool(player, null, 2, -1, stock, -1, -1,
                null, null, null, -1));
        stock.remove(g2);
    }
}
