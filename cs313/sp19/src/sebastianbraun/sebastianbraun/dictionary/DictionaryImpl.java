package sebastianbraun.dictionary;

import org.apache.commons.collections4.list.FixedSizeList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class DictionaryImpl<Key extends Number, E> implements Dictionary<Key, E> {

    FixedSizeList<HashTableSlot<Key, E>> F;
    int prime;
    int size = 0;

    public DictionaryImpl(int start) {
        this.prime = getPrime(start);
        List<HashTableSlot<Key, E>> arrayBase = new ArrayList<>();
        for (int i = 0; i < prime; i++) {
            arrayBase.add(new HashTableSlot<Key, E>());
        }
        this.F = FixedSizeList.fixedSizeList(arrayBase);
    }

    @Override
    public void clear() {
        for (int i = 0; i < prime; i++) {
            this.F.get(i).setHashTableSlot(null, null);
        }
        this.size = 0;
    }

    @Override
    public void insert(Key k, E e) {
        int i = 0;
        int hashKey = getHash(k, i);
        while (this.F.get(hashKey).getKey() != null) {
            i++;
            hashKey = getHash(k, i);
        }
        HashTableSlot<Key, E> entry = this.F.get(hashKey);
        entry.setHashTableSlot(k, e);
        entry.filled();
        this.size++;
    }

    @Override
    public E remove(Key k) {
        int i = 0;
        int hashKey = getHash(k, i);
        HashTableSlot<Key, E> entry = this.F.get(hashKey);
        while (!entry.getKey().equals(k)) {
            i++;
            hashKey = getHash(k, i);
            entry = this.F.get(hashKey);
        }
        E value = entry.getValue();
        entry.setHashTableSlot(null, null);
        this.size--;
        return value;

    }

    @Override
    public E find(Key k) {
        int i = 0;
        int hashKey = getHash(k, i);
        HashTableSlot<Key, E> entry = this.F.get(hashKey);
        while (!entry.getKey().equals(k)) {
            i++;
            hashKey = getHash(k, i);
            entry = this.F.get(hashKey);
        }
        E value = entry.getValue();

        return value;

    }

    @Override
    public int size() {
        return this.size;
    }

    // https://www.baeldung.com/java-generate-prime-numbers   
    // Doesn't give us the next largest prime after the desired size. Scale
    public static List<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    public int getPrime(int start) {
        List<Integer> primeNum = sieveOfEratosthenes(start * 2);
        int thePrime;
        for (int i = 0; i < primeNum.size(); i++) {
            thePrime = primeNum.get(i);
            if (thePrime > start) {
                return thePrime;
            }

        }
        return -1;
    }

    public int getHash(Key k, int i) {
        int hash = (k.hashCode() + i) % prime;
        return hash;
    }
}
