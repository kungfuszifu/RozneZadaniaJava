import java.util.LinkedList;
import java.util.Queue;

public class SharedResource {
    private final Queue<Long> queue = new LinkedList<>();
    private int max = 0;
    private final Object Full = new Object();
    private final Object Empty = new Object();
    public boolean run = true;


    public SharedResource(int max) {
        this.max = max;
    }

    public void waitOnFull() throws InterruptedException {
        synchronized (Full) {
            Full.wait();
        }
    }

    public void notifyFull() {
        synchronized (Full) {
            Full.notifyAll();
        }
    }

    public void waitOnEmpty() throws InterruptedException {
        synchronized (Empty) {
            Empty.wait();
        }
    }

    public void notifyEmpty() {
        synchronized (Empty) {
            Empty.notifyAll();
        }
    }

    public void Add(Long n) {
        synchronized (queue) {
            queue.add(n);
        }
    }

    public Long Take() {
        synchronized (queue) {
            return queue.remove();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        if (queue.size() == max) {
            return true;
        }
        return false;
    }

}
