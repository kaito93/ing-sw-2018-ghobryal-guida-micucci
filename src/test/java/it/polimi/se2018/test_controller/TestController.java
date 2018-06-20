package it.polimi.se2018.test_controller;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * class Controller Tester
 * @author Anton Ghobryal
 */
public class TestController extends TestCase {
    private Controller controller;
    private List<Player> players;
    private Game game;
    private Map map;

    /**
     * setup a new controller
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        players = new ArrayList<>();
        players.add(new Player("Anton"));
        players.add(new Player("Samu"));
        controller = new Controller(new VirtualView(), players);
        game = controller.getGame();
        map = game.getThatMap("Virtus");
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        try {
            controller.finalize();
            game.finalize();
            map.finalize();
        } catch (Throwable throwable) {
            //salta
        }
        System.gc();
        super.tearDown();
    }

    /**
     * tests map method in Controller class
     */
    public void testMap(){
        controller.map("Mik", map);
        assertNull(players.get(0).getMap());
        assertEquals(players.get(0).getFavSig(), 0);
        assertNotSame(players.get(0).getFavSig(), map.getDifficultyLevel());
        controller.map("Anton", map);
        assertNotNull(players.get(0).getMap());
        assertEquals(players.get(0).getFavSig(), map.getDifficultyLevel());
    }


}
