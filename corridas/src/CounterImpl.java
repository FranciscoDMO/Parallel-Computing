public class CounterImpl  {
    private long value ;


    public long getAndIncrement1(){
        long temp = value;
        value = value +1;
        return temp;
    }
}
