/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sebastianbraun.profile;

/**
 *
 * @author sebastian.braun
 */
public class Searcher {

    /**
     *
     * @param list - array to be searched through
     * @param key - value to be searched for
     * @return
     */
    public int linearSearch(int[] list, int key) {
        for (int i = 0; i < list.length; i++) {
            if (key == list[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Source: https://www.baeldung.com/java-binary-search Adjusted to determine
     * low and high from the given array
     *
     * @param sortedArray - array to be searched through
     * @param key - value to be searched for
     * @return
     */
    public int runBinarySearchIteratively(int[] sortedArray, int key) {
        int low = 0;
        int high = sortedArray.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (sortedArray[mid] < key) {
                low = mid + 1;
            } else if (sortedArray[mid] > key) {
                high = mid - 1;
            } else if (sortedArray[mid] == key) {
                return mid;
            }
        }
        return -1;
    }
}
