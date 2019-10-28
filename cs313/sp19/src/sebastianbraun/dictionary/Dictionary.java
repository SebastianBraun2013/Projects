package sebastianbraun.dictionary;

import org.apache.commons.collections4.list.FixedSizeList;

/**
 *
 * @author sebastian.braun
 */
public interface Dictionary<Key, E> {
public void clear();
public void insert(Key k, E e);
public E remove(Key k);
public E find(Key k);
public int size();
}
