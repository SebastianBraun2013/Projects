package sebastianbraun.sorting;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebastian.braun
 */
public class SorterTest {

    private Sorter s;
    private int[] TEST_ARRAY;
    private int[] TEMP;

    public SorterTest() {
        TEST_ARRAY = new int[100000];
        for (int i = 0; i < TEST_ARRAY.length; i++) {
            TEST_ARRAY[i] = (int) (Math.random() * 100000 + 1);
            TEMP = Arrays.copyOf(TEST_ARRAY, TEST_ARRAY.length);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        s = new Sorter();
        TEST_ARRAY = TEMP;
    }

    @After
    public void tearDown() {
    }

    private boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testSelectionSort() {
        System.out.println("Testing Selection Sort");
        s.selectionSort(TEST_ARRAY);
        assert (isSorted(TEST_ARRAY));
    }

    @Test
    public void testInsertionSort() {
        System.out.println("Testing Insertion Sort");
        s.insertionSort(TEST_ARRAY);
        assert (isSorted(TEST_ARRAY));
    }

    @Test
    public void testShellSort() {
        System.out.println("Testing Shell Sort");
        s.shellSort(TEST_ARRAY);
        assert (isSorted(TEST_ARRAY));
    }

    @Test
    public void testMergeSort() {
        System.out.println("Testing Merge Sort");
        s.mergeSort(TEST_ARRAY);
        assert (isSorted(TEST_ARRAY));
    }

    @Test
    public void testQuickSort() {
        System.out.println("Testing Quick Sort");
        s.quickSort(TEST_ARRAY);
        assert (isSorted(TEST_ARRAY));
    }
}
