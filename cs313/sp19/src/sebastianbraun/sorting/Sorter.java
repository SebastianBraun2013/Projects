package sebastianbraun.sorting;

import java.util.Arrays;

/**
 *
 * @author sebastian.braun
 * Things I know I could have improved. 
 *  - Made a print method with comparison and swap class attributes 
 *  - Solved problems with Stack Overflow (Issue disappears when ran in JUnit class)
 *  - Many more..
 */
public class Sorter {

    //adapt this to count the comparisons and swaps. do it for each. analysize the data and youre done.
    //source: https://www.geeksforgeeks.org/selection-sort/ 
    //source is adapted to track comparisons and swaps made
    public void selectionSort(int[] a) {
        int n = a.length;
        long comparisons = 0;
        long swaps = 0;
        // One by one move boundary of unsorted subarray 
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array 
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j] < a[min_idx]) {
                    min_idx = j;
                }
            }
            // Swap the found minimum element with the first 
            // element 
            int temp = a[min_idx];
            a[min_idx] = a[i];
            a[i] = temp;
            swaps++;
        }
        System.out.println("Selection Sort");
        System.out.println("Array length = " + a.length);
        System.out.println("Comparisons = " + comparisons);
        System.out.println("Swaps = " + swaps);
    }

    //source: https://www.geeksforgeeks.org/insertion-sort/
    //source adapted to track comparisons and swaps made
    public void insertionSort(int[] a) {
        int n = a.length;
        long comparisons = 0;
        long swaps = 0;

        for (int i = 1; i < n; ++i) {
            int key = a[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
                comparisons++;
                swaps++;
            }
            a[j + 1] = key;
            
            comparisons++;
        }
        System.out.println("Insertion Sort");
        System.out.println("Array length = " + a.length);
        System.out.println("Comparisons = " + comparisons);
        System.out.println("Swaps = " + swaps);
    }

    //Source: https://rosettacode.org/wiki/Sorting_algorithms/Shell_sort
    //Source adapted to track comparisons and swaps made.
    //I realized as I am finishing that this implementation of shell sort uses
    //gaps that aren't optimized for time complexity and I don't have time to 
    //change it.
    public void shellSort(int[] a) {
        int comparisons = 0;
        int swaps = 0;
        int increment = a.length / 2;
        while (increment > 0) {
            
            for (int i = increment; i < a.length; i++) {
                int j = i;
                int temp = a[i];
                comparisons++;
                while (j >= increment && a[j - increment] > temp) {
                    comparisons++;
                    swaps++;
                    a[j] = a[j - increment];
                    j = j - increment;
                }
                
                a[j] = temp;
                swaps++;
            }
            
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
        System.out.println("Shell Sort");
        System.out.println("Array length = " + a.length);
        System.out.println("Comparisons = " + comparisons);
        System.out.println("Swaps = " + swaps);
    }

    private long mergeSortComparisons = 0;
    private long mergeSortMoves = 0;

    @SuppressWarnings("Duplicates")
    private void merge(int arr[], int low, int mid, int right) {
        int n1 = mid - low + 1;
        int n2 = right - mid;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i) {
            mergeSortMoves++;
            L[i] = arr[low + i];
        }
        for (int j = 0; j < n2; ++j) {
            mergeSortMoves++;
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0;

        int k = low;
        while (i < n1 && j < n2) {
            mergeSortComparisons++;
            mergeSortMoves++;
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            mergeSortMoves++;
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            mergeSortMoves++;
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    //https://www.geeksforgeeks.org/merge-sort/
    private void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    public void mergeSort(int[] a) {
        mergeSort(a, 0, a.length - 1);
        System.out.println("Merge Sort");
        System.out.println("Array length = " + a.length);
        System.out.println("Comparisons = " + mergeSortComparisons);
        System.out.println("Swaps = " + mergeSortMoves);
        mergeSortComparisons = 0;
        mergeSortMoves = 0;
    }

    private int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j <= high - 1; j++) {
            quickSortComparisons++;
            if (arr[j] <= pivot) {
                i++;

                quickSortSwaps++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        quickSortSwaps++;
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private long quickSortComparisons = 0;
    private long quickSortSwaps = 0;

    //https://www.geeksforgeeks.org/iterative-quick-sort/
    //Throws an Stack Overflow due to the load of recursive calls.
    private void quickSort(int arr[], int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    public void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
        System.out.println("Quick Sort");
        System.out.println("Array length = " + a.length);
        System.out.println("Comparisons = " + quickSortComparisons);
        System.out.println("Swaps = " + quickSortSwaps);
        quickSortComparisons = 0;
        quickSortSwaps = 0;
    }

    public void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");

        }
    }

    public static void main(String[] args) {
        Sorter s = new Sorter();
        int[] tenk = new int[10000];
        int[] hundredk = new int[100000];
        int[] onemil = new int[1000000];
        int[] tenmil = new int[10000000];
        int[] hundredmil = new int[100000000];
        for (int i = 0; i < tenk.length; i++) {
            tenk[i] = (int) (Math.random() * 1000000 + 1);
        }
        for (int i = 0; i < hundredk.length; i++) {
            hundredk[i] = (int) (Math.random() * 1000000 + 1);
        }
        for (int i = 0; i < onemil.length; i++) {
            onemil[i] = (int) (Math.random() * 1000000 + 1);
        }
        for (int i = 0; i < tenmil.length; i++) {
            tenmil[i] = (int) (Math.random() * 1000000 + 1);
        }
        for (int i = 0; i < hundredmil.length; i++) {
            hundredmil[i] = (int) (Math.random() * 1000000 + 1);
        }

        int[] temp = Arrays.copyOf(tenk, tenk.length);

        s.selectionSort(tenk);
        tenk = temp;
        System.out.println("");

        s.insertionSort(tenk);
        tenk = temp;
        System.out.println("");

        s.shellSort(tenk);
        tenk = temp;
        System.out.println("");

        s.mergeSort(tenk);
        tenk = temp;
        System.out.println("");

        s.quickSort(tenk);
        tenk = temp;
        System.out.println("");
        
        temp = Arrays.copyOf(hundredk, hundredk.length);
        
        s.selectionSort(hundredk);
        tenk = temp;
        System.out.println("");

        s.insertionSort(hundredk);
        tenk = temp;
        System.out.println("");

        s.shellSort(hundredk);
        tenk = temp;
        System.out.println("");

        s.mergeSort(hundredk);
        tenk = temp;
        System.out.println("");

        s.quickSort(hundredk);
        tenk = temp;
        System.out.println("");
        
        temp = Arrays.copyOf(onemil, onemil.length);
        
        //s.selectionSort(onemil);
        //tenk = temp;
        //System.out.println("");

        //s.insertionSort(onemil);
        //tenk = temp;
        //System.out.println("");

        //s.shellSort(onemil);
        //tenk = temp;
        //System.out.println("");

        //s.mergeSort(onemil);
        //tenk = temp;
        //System.out.println("");

        //s.quickSort(onemil);
        //tenk = temp;
        //System.out.println("");
        
        temp = Arrays.copyOf(tenmil, tenmil.length);
        
        //s.selectionSort(tenmil);
        //tenk = temp;
        //System.out.println("");

        //s.insertionSort(tenmil);
        //tenk = temp;
        //System.out.println("");

        //s.shellSort(tenmil);
        //tenk = temp;
        //System.out.println("");

        //s.mergeSort(tenmil);
        //tenk = temp;
        //System.out.println("");

        //s.quickSort(tenmil);
        //tenk = temp;
       // System.out.println("");
        
        //temp = Arrays.copyOf(hundredmil, hundredmil.length);
        
        //s.selectionSort(hundredmil);
        //tenk = temp;
        //System.out.println("");

        //s.insertionSort(hundredmil);
        //tenk = temp;
        //System.out.println("");

        //s.shellSort(hundredmil);
        //tenk = temp;
        //System.out.println("");

        //s.mergeSort(hundredmil);
        //tenk = temp;
        //System.out.println("");

        //s.quickSort(hundredmil);
        //tenk = temp;
        //System.out.println("");
    }
}
