package it.polimi.se2018.test_model.cards.tool_card_strategy;

import it.polimi.se2018.server.model.*;
import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.server.controller.tool_card_strategy.*;
import it.polimi.se2018.shared.exception.InvalidValueException;
import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * classes CopperFoilBurnisher, egl Brush, RunningPliers, TapWheel Tester
 * @author Anton Ghobryal
 */
public class TestSomeCards1 extends TestCase {
    private Card card1, card2, card3, card4;
    private ToolCardStrategy strategy1, strategy2, strategy3, strategy4;
    private Player player;
    private Game game;
    private Map map;
    private Dice b1, g2, p3, r4;
    private List<Dice> diceList;
    private RoundSchemeCell[] roundSchemeCells;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestSomeCards1(String name){
        super(name);
    }

    /**
     * setup strategy1 new Private Objective Card, strategy1 new map, new dices and position'em on the map
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception{
        game = new Game();
        roundSchemeCells = game.getRoundSchemeMap();
        strategy1 = new CopperFoilBurnisher();
        strategy2 = new EglomiseBrush();
        strategy3 = new RunningPliers();
        strategy4 = new TapWheel();
        card1 = new ToolCard("Copper Foil Burnisher", "Test", 0, Color.BLUE, strategy1);
        card2 = new ToolCard("Eglomise Brush", "Test", 0, Color.BLUE, strategy2);
        card3 = new ToolCard("Running Pliers", "Test", 0, Color.RED, strategy3);
        card4 = new ToolCard("Tap Wheel", "Test", 0, Color.BLUE, strategy4);
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
        r4 = new Dice();
        r4.setValue(4);
        r4.setColor(Color.RED);
        map.posDice(b1, 3, 2);
        map.posDice(g2, 3, 1);
        map.posDice(p3, 2, 1);
        diceList = new ArrayList<>();
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
        card1 =null;
        card2=null;
        card3=null;
        strategy3=null;
        card4=null;
        strategy4=null;
        strategy2=null;
        strategy1 =null;
        player=null;
        b1 =null;
        g2 =null;
        diceList.clear();
        diceList=null;
        for (RoundSchemeCell roundSchemeCell : roundSchemeCells) roundSchemeCell.getRestOfStock().clear();
        roundSchemeCells=null;
        System.gc();
        super.tearDown();
    }

    /**
     * Tests useTool method in CopperFoilBurnisher, Egl Brush, RunningPliers, TapWheel class
     */
    public void testUseTool(){
        assertFalse(card1.useTool(player, g2, 0, 0, null, 3, 1,
                null, null, null, -1));
        assertFalse(card2.useTool(player, p3, 1, 3, null, 2, 1,
                null, null, null, -1));
        assertTrue(card1.useTool(player, b1, 3, 0, null, 3, 2,
                null, null, null, -1));
        assertTrue(card2.useTool(player, p3, 2, 2, null, 2, 1,
                null, null, null, -1));
        assertFalse(card2.useTool(player, p3, 2, 0, null, 1, 1,
                null, null, null, -1));
        assertFalse(card3.useTool(player, r4, 2, -1, null, 1, 1,
                null, null, null, -1));
        assertTrue(card3.useTool(player, r4, 1, -1, null,  3, 3,
                null, null, null, -1));

        assertFalse(card4.useTool(player, r4, 2, 0, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        roundSchemeCells[0].getRestOfStock().add(r4);
        assertFalse(card4.useTool(player, r4, 2, 0, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        diceList.add(p3);
        assertFalse(card4.useTool(player, r4, 2, 0, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        diceList.add(b1);
        assertFalse(card4.useTool(player, r4, 2, 0, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        diceList.clear();
        Dice r1 = new Dice();
        try {
            r1.setValue(1);
        } catch (InvalidValueException e) {
            fail();
        }
        r1.setColor(Color.RED);
        assertTrue(map.posDice(r1, 2, 4));
        Dice r2 = new Dice();
        try {
            r2.setValue(2);
        } catch (InvalidValueException e) {
            fail();
        }
        r2.setColor(Color.RED);
        assertTrue(map.posDice(r2, 2, 0));
        diceList.add(r1);
        card4.getStrategy().setRow3(2);
        card4.getStrategy().setColumn3(4);
        card4.getStrategy().setRow4(-1);
        card4.getStrategy().setColumn4(-1);
        assertFalse(card4.useTool(player, r4, 0, 0, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        assertFalse(card4.useTool(player, r4, 0, 1, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        assertTrue(card4.useTool(player, r4, 1, 1, diceList, -1, -1,
                null, roundSchemeCells, null, 0));
        diceList.add(r2);
        card4.getStrategy().setRow3(1);
        card4.getStrategy().setColumn3(1);
        card4.getStrategy().setRow4(2);
        card4.getStrategy().setColumn4(0);
        player.setFavorSig();
        assertTrue(card4.useTool(player, r4, 2, 4, diceList, 1, 1,
                null, roundSchemeCells, null, 0));
    }
}
