import java.time.Duration;
import java.time.Instant;

public class PrimesWithCounter {

    public static  int countPrimes (CounterImpl c , long to){
        int total=0;


        long n = c.getAndIncrement1();
        while(n<to){
            if(Primes.IsPrime(n))
                total++;
            n=  c.getAndIncrement1();

        }
        return total;

    }

    public static void showPrimes (CounterImpl c , long to ){

        System.out.println(String.format("searching until %d..",to));
        Instant start = Instant.now();
        int total = countPrimes(c,to);
        Instant finish = Instant.now();

        System.out.println(String.format("Found %d primes in %d sec.", total , Duration.between(start , finish).toSeconds()));


    }
}
