package sebastianbraun.profile;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Simple Test class for profiler lab
 * 
 * Note: test Unit class names must end with "Test" in NetBeans
 * @author kevin.coogan
 */
public class SearcherTest {
    private Searcher s;
    private final int[] TEST_ARRAY;
    
    public SearcherTest() {
        TEST_ARRAY = new int[100000];
        for (int i = 0; i < TEST_ARRAY.length; i++) {
            TEST_ARRAY[i] = i;
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("BeforeClass");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("After Class");
    }
    
    @Before
    public void setUp() {
        System.out.println("Before each test");
        s = new Searcher();
    }
    
    @After
    public void tearDown() {
        System.out.println("After each test");
    }

    
    // Test Methods here, must have @Test annotation to be recognized
    @Test
    public void testLinearSearch() {
        System.out.println("Test Linear Search");
        int retVal = s.linearSearch(TEST_ARRAY, 1);
        assertEquals(1, retVal);
        
        retVal = s.linearSearch(TEST_ARRAY, 45987);
        assertEquals(45987, retVal);
        
        retVal = s.linearSearch(TEST_ARRAY, 99999);
        assertEquals(99999, retVal);
        
        retVal = s.linearSearch(TEST_ARRAY, 100000);
        assertEquals(-1, retVal);        
    }

    @Test
    public void testBinarySearch() {
        System.out.println("Test Binary Search");
        int retVal = s.runBinarySearchIteratively(TEST_ARRAY, 1);
        assertEquals(1, retVal);
        
        retVal = s.runBinarySearchIteratively(TEST_ARRAY, 45987);
        assertEquals(45987, retVal);
        
        retVal = s.runBinarySearchIteratively(TEST_ARRAY, 99999);
        assertEquals(99999, retVal);
        
        retVal = s.runBinarySearchIteratively(TEST_ARRAY, 100000);
        assertEquals(-1, retVal);        
    }
}
