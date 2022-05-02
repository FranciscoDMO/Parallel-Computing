import java.time.Duration;
import java.time.Instant;


public class PrimesIntervals {

    public static int countPrimes (long from , long to ){
        int total =0;
        for (long n=from ; n <to;n++){
            if (Primes.IsPrime(n))
                total++;
        }
        return total;
    }

    public static void showPrimes (long from , long to ){

        System.out.println(String.format(  "searching in [%d,%d[...", from  , to    ));
        Instant start = Instant.now();
        int total = countPrimes(from , to );
        Instant finish = Instant.now();
        System.out.println(String.format(  "Found %d primes in %d sec.", total  , Duration.between(start, finish).toSeconds()));

    }

}

