public class CounterMain {

    public static void main (String [] args) throws Exception{
        CounterImpl c = new CounterImpl();
        PrimesWithCounter.showPrimes(c,400000);

    }
}



