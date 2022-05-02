import java.time.Duration;
import java.time.Instant;

public class Primes {


    public static boolean IsPrime(long n){
        for (int d =2 ; d<n ; d++){
            if (n%d==0){
                return false ;
            }
        }
        return true ;
    }
}


