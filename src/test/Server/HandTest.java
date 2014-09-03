package test.Server;

import Server.Card;
import Server.Deck;
import Server.Hand;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Hand Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 3, 2014</pre>
 */
public class HandTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getId()
     */
    @Test
    public void testGetId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: display()
     */
    @Test
    public void testDisplay() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getCards()
     */
    @Test
    public void testGetCards() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getValues()
     */
    @Test
    public void testGetValues() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * Method: handEvaluate()
     */
    @Test
    public void testHandEvaluate() throws Exception {
        //TODO: Test goes here...

        int[] v;

        //StraightFlush
        Hand h = new Hand(new Card(1,1), new Card(1,2), new Card(1,3), new Card(1,4), new Card(1,5),
            new Card(1,7), new Card(1,8));
        v = h.getValues();

        Assert.assertEquals(9, v[0]);

        //StraightFlush
        h = new Hand(new Card(1,10), new Card(1,9), new Card(1,8), new Card(1,7), new Card(1,6),
                new Card(2,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(9, v[0]);

        //4 of a kind
        h = new Hand(new Card(1,8), new Card(2,8), new Card(3,8), new Card(4,8), new Card(3,1),
                new Card(2,7), new Card(2,4));
        v = h.getValues();

        Assert.assertEquals(8, v[0]);

        //4 of a kind
        h = new Hand(new Card(1,9), new Card(2,9), new Card(3,9), new Card(4,9), new Card(3,1),
                new Card(2,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(8, v[0]);

        //4 of a kind
        h = new Hand(new Card(1,1), new Card(2,1), new Card(3,1), new Card(4,1), new Card(3,12),
                new Card(2,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(8, v[0]);

        //4 of a kind
        h = new Hand(new Card(1,4), new Card(2,4), new Card(3,4), new Card(4,4), new Card(3,1),
                new Card(2,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(8, v[0]);

        //Full House
//        h = new Hand(new Card(1,4), new Card(2,4), new Card(3,4), new Card(1,5), new Card(4,5),
//                new Card(2,7), new Card(3,8));
//        v = h.getValues();
//
//        Assert.assertEquals(7, v[0]);

        //Flush
        h = new Hand(new Card(1,4), new Card(1,5), new Card(1,11), new Card(1,7), new Card(1,8),
                new Card(2,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(6, v[0]);

        //Straight
        h = new Hand(new Card(3,1), new Card(2,2), new Card(3,3), new Card(1,4), new Card(1,5),
                new Card(1,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(5, v[0]);

        //Three of the kind
        h = new Hand(new Card(3,1), new Card(2,2), new Card(2,1), new Card(1,1), new Card(1,5),
                new Card(1,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(4, v[0]);

        //Two pairs
        h = new Hand(new Card(3,1), new Card(2,2), new Card(2,1), new Card(1,2), new Card(1,5),
                new Card(1,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(3, v[0]);

        //One pairs
        h = new Hand(new Card(3,1), new Card(2,10), new Card(2,1), new Card(1,9), new Card(1,5),
                new Card(1,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(2, v[0]);

        //Height cards
        h = new Hand(new Card(3,1), new Card(2,10), new Card(2,12), new Card(1,9), new Card(1,5),
                new Card(1,7), new Card(2,8));
        v = h.getValues();

        Assert.assertEquals(1, v[0]);
    }

} 
