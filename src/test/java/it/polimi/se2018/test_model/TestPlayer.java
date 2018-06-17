package it.polimi.se2018.test_model;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.exception.notValidMatrixException;
import junit.framework.TestCase;

/**
 * class Player Tester
 * @author Anton Ghobryal
 */

public class TestPlayer extends TestCase {

    private Player player;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestPlayer(String name){
        super(name);
    }

    /**
     * setup a new player with its attributes
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        player = new Player("anton");
        player.setScore(0);
        player.setMap(new Map("map", 3, 4, 5));
        player.setFavorSig();
        player.setPrivateObjectiveCard(new PrivateObjectiveCard("Violet", "description", Color.PURPLE));
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        player.setScore(0);
        player.getMap().finalize();
        player.setMap(null);
        player.setPrivateObjectiveCard(null);
        player.setPrivateObjectiveCard(null);
        System.gc();
        super.tearDown();
    }

    /**
     * tests get and set of CardPrivateObj in class player
     */
    public void testGetSetCardPrivateObj(){
        assertNotNull(player.getCardPrivateObj());
    }

    /**
     * tests get and set of score in class player
     */
    public void testGetSetScore(){
        assertEquals(player.getScore(), 0);
        int a = 1;
        player.setScore(a);
        assertEquals(player.getScore(), a);
        assertNotSame(player.getScore(), a+1);
    }

    /**
     * testsget and set of favour points in class player
     */
    public void testSetGetFavSig(){
        assertEquals(player.getFavSig(), player.getMap().getDifficultyLevel());
        assertNotSame(player.getFavSig(), player.getMap().getDifficultyLevel()/2);
    }

    /**
     * tests get and set of map in class player
     */
    public void testGetSetMap(){
        try {
            Map map = new Map("a", 3, 4, 5);
            player.setMap(map);
            assertSame(player.getMap(), map);
        } catch (notValidMatrixException e) {
            fail();
        }
    }

    /**
     * tests getName in class player
     */
    public void testGetName(){
        assertSame(player.getName(), "anton");
        assertNotSame(player.getName(), "samu");
    }

    /**
     * tests modifyFavorSig in class player
     */
    public void testModifyFavorSig(){
        assertFalse(player.modifyFavorSig(3));
        assertTrue(player.modifyFavorSig(2));
        assertEquals(player.getFavSig(), 1);
        assertTrue(player.modifyFavorSig(1));
        assertEquals(player.getFavSig(), 0);
        assertFalse(player.modifyFavorSig(1));
        assertFalse(player.modifyFavorSig(0));
    }
}
