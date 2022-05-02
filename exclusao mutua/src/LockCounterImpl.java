import java.util.concurrent.locks.ReentrantLock;

public class LockCounterImpl {
    private long value;
    private ReentrantLock lock = new ReentrantLock();

    public long getAndIncrement1() {
        try {
            lock.lock();

            long temp = value;
            value=value+1;
            return  temp;
        } finally {
            lock.unlock();
        }

    }
}