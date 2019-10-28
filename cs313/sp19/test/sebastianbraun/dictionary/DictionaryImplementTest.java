
package sebastianbraun.dictionary;

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
public class DictionaryImplementTest<Key, E> {

    E element;
    DictionaryImpl d;

    public DictionaryImplementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        d = new DictionaryImpl(10);
        element = (E) new Object();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void Clear() {
        this.d.insert(element.hashCode(), element);
        d.clear();
        assert (d.size() == 0);
    }

    @Test
    public void Insert() {
        this.d.insert(element.hashCode(), element);
        assertEquals(d.find(element.hashCode()), element);
        assert (d.size() == 1);
    }

    @Test
    public void Remove(){
        this.d.insert(element.hashCode(), element);
        assertEquals(this.d.remove(element.hashCode()), element);
        assertEquals(this.d.find(element.hashCode()), null);
    }
    @Test
    public void Find() {
        this.d.insert(element.hashCode(), element);
        assertEquals(d.find(element.hashCode()), element);
    }

    @Test
    public void Size(){
        assert (d.size() == 0);
        this.d.insert(element.hashCode(), element);
        assert (d.size() == 1);
        this.d.remove(element.hashCode());
        assert (d.size() == 0);
    }
}
