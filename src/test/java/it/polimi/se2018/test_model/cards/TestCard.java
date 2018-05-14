package it.polimi.se2018.test_model.cards;

import it.polimi.se2018.model.cards.Card;
import junit.framework.TestCase;
import org.junit.Test;


public class TestCard extends TestCase {

    public TestCard(String name){
        super(name);
    }

    private Card card;

    @Override
    protected void setUp() throws Exception {
        card = new Card("testCard", "testCardDescription");
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        card = null;
        System.gc();
        super.tearDown();
    }

    @Test
    public void testGetTitle(){
        assertEquals(card.getTitle(),"testCard");
        assertNotSame(card.getTitle(),"testcard");
    }

    @Test
    public void testGetDescription(){
        assertEquals(card.getDescription(),"testCardDescription");
        assertNotSame(card.getDescription(),"testcardDescription");
    }
}
