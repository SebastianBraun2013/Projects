package sebastianbraun.queue;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kevin.coogan
 */
public class QueueTest {
    
    private Queue<Integer> Q1;

    public QueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Before All Tests");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("After All Tests");
    }
    
    @Before
    public void setUp() {
        System.out.println("Before each test");
        //Test both types of Queues!
     //   Q1 = new ArrayQueue<>();
        Q1 = new ListQueue<>();
    }
    
    @After
    public void tearDown() {
        System.out.println("After Each Test");
    }

    
    @Test
    public void testEnqueue() {
        System.out.println("running my testEnqueue");
        Q1.enqueue(10);
        Q1.enqueue(20);
        Q1.enqueue(15);
        assertEquals("<10 20 15>", Q1.toString());
    }
    
    @Test
    public void testDequeue() {
        System.out.println("running my testDequeue");
        Q1.enqueue(10);
        Q1.enqueue(20);
        Q1.enqueue(15);
        assertEquals("<10 20 15>", Q1.toString());
        Q1.dequeue();
        assertEquals("<20 15>", Q1.toString());
        Q1.dequeue();
        assertEquals("<15>", Q1.toString());
        Q1.dequeue();
        assertEquals("<>", Q1.toString());
    }
    @Test
    public void testFrontValue() {
        Q1.enqueue(3);
        assertEquals(3, (int)Q1.frontValue());
        
        Q1.enqueue(9);
        assertEquals(3, (int)Q1.frontValue());
        
        Q1.enqueue(12);
        assertEquals(3, (int)Q1.frontValue());
        
        Q1.dequeue();
        assertEquals(9, (int)Q1.frontValue());
        
        Q1.dequeue();
        assertEquals(12, (int)Q1.frontValue()); 
    }
    @Test
    public void testLength() {
        Q1.enqueue(3);
        assertEquals(1, Q1.length());
        Q1.enqueue(9);
        assertEquals(2, Q1.length());
        Q1.enqueue(12);
        assertEquals(3, Q1.length());
        Q1.dequeue();
        assertEquals(2, Q1.length());
        Q1.dequeue();
        assertEquals(1, Q1.length());
        Q1.dequeue();
        assertEquals(0, Q1.length());
    }
    @Test
    public void testClear() {
        Q1.enqueue(3);
        Q1.enqueue(9);
        Q1.enqueue(12);
        Q1.clear();
        assertEquals("<>", Q1.toString());
        assertEquals(0, Q1.length());
    }
}
