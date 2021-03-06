package it.polimi.se2018.test_model.cards;

import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.server.model.cards.Card;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import junit.framework.TestCase;

/**
 * class Card Tester
 * @author Anton Ghobryal
 */

public class TestCard extends TestCase {

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestCard(String name){
        super(name);
    }

    private Card card;

    /**
     * setup a new card
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        card = new PrivateObjectiveCard("testCard", "testCardDescription", Color.BLUE);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        card = null;
        System.gc();
        super.tearDown();
    }

    /**
     * tests getTitle method in class Card
     */
    public void testGetTitle(){
        assertEquals(card.getTitle(),"testCard");
        assertNotSame(card.getTitle(),"testcard");
    }

    /**
     * test getDescription in class Card
     */
    public void testGetDescription(){
        assertEquals(card.getDescription(),"testCardDescription");
        assertNotSame(card.getDescription(),"testcardDescription");
    }
}
