package sebastianbraun.queue;

/**
 *
 * @author sebastian.braun
 */
public class Runner {
    public static void main(String[] args) {
        ArrayQueue a = new ArrayQueue(10);
        int x = 5;
        a.enqueue(x);
        a.enqueue(x);
        a.enqueue(x);
        a.enqueue(x);
        a.enqueue(x);
        System.out.println(a.toString());
        System.out.println(a.length());
        a.dequeue();
        System.out.println(a.toString());
        System.out.println(a.length());
        a.clear();
        System.out.println(a.toString());
        System.out.println(a.length());
        
        ListQueue l = new ListQueue();
        l.enqueue(x);
        l.enqueue(x);
        l.enqueue(x);
        l.enqueue(x);
        l.enqueue(x);
        System.out.println(l.toString());
        l.dequeue();
        System.out.println(l.toString());
        System.out.println(l.length());
        l.clear();
        System.out.println(l.toString());
        System.out.println(l.length());
        
    }
}
