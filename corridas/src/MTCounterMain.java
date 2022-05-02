import java.time.Duration;
import java.time.Instant;

public class MTCounterMain {
    public static void main(String[] args )throws Exception{
        long max = 400000 ;
        int thr=12;

        Thread threads [] = new  Thread[thr];

        CounterImpl  c = new CounterImpl();

        for (int i =0; i< thr; i++){
            threads [i] = new Thread(()->{
                 PrimesWithCounter.showPrimes(c , max);

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
