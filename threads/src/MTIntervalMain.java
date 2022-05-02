import java.time.Duration;
import java.time.Instant;

public class MTIntervalMain {
    public static void main (String [] agrs )  throws Exception  {

        long max=400000;
        int thr =12;
        Thread threads [] = new Thread[thr];
        long interval = max / thr;

        for (int i=0 ; i < thr ; i++ ) {
            long slice = i * interval;
            threads[i] = new Thread(() -> {
                PrimesIntervals.showPrimes(slice, slice + interval);
            });
        }

        Instant start = Instant.now();

        for (Thread t:threads)
            t.start();
        for(Thread t : threads)
            t.join();

        Instant finish = Instant.now();


        System.out.println(String.format(  "Threads started and stopped in %d sec.", Duration.between(start, finish).toSeconds()));



    }



}
