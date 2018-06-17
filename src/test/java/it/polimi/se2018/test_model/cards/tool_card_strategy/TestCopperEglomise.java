package it.polimi.se2018.test_model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.CopperFoilBurnisher;
import it.polimi.se2018.model.cards.tool_card_strategy.EglomiseBrush;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import junit.framework.TestCase;

/**
 * class CopperFoilBurnisher Tester
 * @author Anton Ghobryal
 */
public class TestCopperEglomise extends TestCase {
    private Card card1, card2;
    private ToolCardStrategy strategy1, strategy2;
    private Player player;
    private Game game;
    private Map map;
    private Dice b1, g2, p3;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestCopperEglomise(String name){
        super(name);
    }

    /**
     * setup strategy1 new Private Objective Card, strategy1 new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        strategy1 = new CopperFoilBurnisher();
        strategy2 = new EglomiseBrush();
        card1 = new ToolCard("Copper Foil Burnisher", "Test", 0, Color.BLUE, strategy1);
        card2 = new ToolCard("Eglomise Brush", "Test", 0, Color.BLUE, strategy2);
        player = new Player("Anton");
        map = game.getThatMap("virtus");
        player.setMap(map);
        player.setFavorSig();
        b1 = new Dice();
        b1.setValue(1);
        b1.setColor(Color.BLUE);
        g2 = new Dice();
        g2.setValue(2);
        g2.setColor(Color.GREEN);
        p3 = new Dice();
        p3.setValue(3);
        p3.setColor(Color.PURPLE);
        map.posDice(b1, 3, 2);
        map.posDice(g2, 3, 1);
        map.posDice(p3, 2, 1);
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
        card1 =null;
        card2=null;
        strategy2=null;
        strategy1 =null;
        player=null;
        b1 =null;
        g2 =null;
        System.gc();
        super.tearDown();
    }

    /**
     * Tests useTool method in CopperFoilBurnisher class
     */
    public void testUseTool(){
        assertFalse(card1.useTool(player, g2, 0, 0, null, false, 3, 1,
                null, null, null, -1));
        assertFalse(card2.useTool(player, p3, 1, 3, null, false, 2, 1,
                null, null, null, -1));
        assertTrue(card1.useTool(player, b1, 3, 0, null, false, 3, 2,
                null, null, null, -1));
        assertTrue(card2.useTool(player, p3, 2, 2, null, false, 2, 1,
                null, null, null, -1));
        assertFalse(card2.useTool(player, p3, 2, 0, null, false, 1, 1,
                null, null, null, -1));
    }
}
