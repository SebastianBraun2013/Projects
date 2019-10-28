package sebastianbraun.profile;

/**
 *
 * @author sebastian.braun
 */
public class Runner {

    public static void main(String[] args) {
        Searcher searcher = new Searcher();
        int[] listToSearch = new int[90];
        for (int i = 0; i < listToSearch.length; i++) {
            listToSearch[i] = i + 1;
        }
        System.out.println(searcher.linearSearch(listToSearch, 0));
        System.out.println(searcher.runBinarySearchIteratively(listToSearch, 0));
        
        int a[] = new int[10];
        
        System.out.println(a.length);
        System.out.println(a.toString());
    }

}
