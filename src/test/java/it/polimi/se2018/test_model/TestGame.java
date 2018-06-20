package it.polimi.se2018.test_model;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.util.DiceBox;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * class Game Tester
 * @author Anton Ghobryal
 */

public class TestGame extends TestCase {

    private Game game;
    private List<ToolCard> toolCards;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private DiceBox diceBox;
    private RoundSchemeCell[] roundSchemeMap;
    private List<Player> players;
    private List<Dice> stock;
    private List<Map> maps;
    private List<Dice> dices;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestGame(String name){
        super(name);
    }


    /**
     * setup a new game model
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        game = new Game();
        toolCards = game.getToolCards();
        publicObjectiveCards = game.getPublicObjCard();
        diceBox = game.getDiceBag();
        roundSchemeMap = game.getRoundSchemeMap();
        players = new ArrayList<>();
        players.add(new Player("Anton"));
        stock = game.getStock();
        maps = game.getMaps();
        dices = diceBox.getBox();
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        players.clear();
        stock.clear();
        dices.clear();
        toolCards=null;
        publicObjectiveCards=null;
        diceBox=null;
        players=null;
        stock=null;
        maps=null;
        dices=null;
        try {
            game.finalize();
        } catch (Throwable throwable) {
            //salta
        }
        game=null;
        System.gc();
        super.tearDown();
    }

    /**
     * tests getToolCards method in Game class
     */
    public void testGetToolCards(){
        assertNotNull(game.getToolCards());
        assertEquals(toolCards.size(), 3);
        assertNotSame(toolCards.size(), 4);
    }

    /**
     * tests searchToolCard method in Game class
     */
    public void testSearchToolCard(){
        ToolCard a = toolCards.get(0);
        assertNotNull(game.searchToolCard(a.getTitle()));
        assertEquals(game.searchToolCard(a.getTitle()), a);
        assertNull(game.searchToolCard("Anton"));
    }

    /**
     * tests getMaxRound method in Game class
     */
    public void testGetMaxRound(){
        assertEquals(game.getMaxRound(), 10);
        assertNotSame(game.getMaxRound(), 9);
    }

    /**
     * tests getDiceBag method in Game class
     */
    public void testGetDiceBag(){
        assertNotNull(game.getDiceBag());
        assertEquals(game.getDiceBag().getBox().size(), 90);
        assertNotSame(game.getDiceBag().getBox().size(), 91);
    }

    /**
     * tests getPublicObjCard method in Game class
     */
    public void testGetPublicObjCard(){
        assertNotNull(game.getPublicObjCard());
        assertEquals(publicObjectiveCards.size(), 3);
        assertNotSame(publicObjectiveCards.size(), 4);
    }

    /**
     * tests getRoundSchemeMap method in Game class
     */
    public void testGetRoundSchemeMap(){
        assertNotNull(game.getRoundSchemeMap());
        assertEquals(roundSchemeMap.length, game.getMaxRound());
        assertNotSame(roundSchemeMap.length, 0);
    }

    /**
     * tests setPrivateObjectiveCard method in Game class
     */
    public void testSetPrivateObjectiveCard(){
        game.setPrivateObjectiveCard(players);
        assertNotNull(players.get(0).getCardPrivateObj());
    }

    /**
     * tests set and get of stock methods in Game class
     */
    public void testSetGetStock(){
        assertEquals(game.getStock().size(), 0);
        game.setStock(dices);
        assertNotSame(stock.size(), dices.size());
        assertEquals(stock.size(), 0);
        assertEquals(game.getStock().size(), dices.size());
    }

    /**
     * tests set and get of map methods in Game class
     */
    public void testSetGetMaps(){
        assertNotNull(maps);
        assertEquals(game.getMaps().size(), 24);
        assertNotSame(game.getMaps().size(), 12);
    }

    /**
     * tests removeDiceStock method in Game class
     */
    public void testRemoveDiceStock(){
        Dice a = new Dice();
        Dice b = new Dice();
        try {
            a.setValue(1);
            b.setValue(2);
        } catch (InvalidValueException e) {
            fail();
        }
        a.setColor(Color.BLUE);
        a.setColor(Color.BLUE);
        stock.add(a);
        assertTrue(stock.contains(a));
        assertFalse(stock.contains(b));
        game.removeDiceStock(b);
        game.removeDiceStock(a);
        assertFalse(stock.contains(a));
    }

}
