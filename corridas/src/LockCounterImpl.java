import java.util.concurrent.locks.ReentrantLock;

public class LockCounterImpl {
    private long value;
    private ReentrantLock lock = new ReentrantLock();

    public long getAndIncrement1() {
        try {
            lock.lock();

            long temp = value;
            value = temp;
        } finally {
            lock.unlock();
        }
        return value;
    }
}


