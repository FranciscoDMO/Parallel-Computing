import java.nio.channels.MulticastChannel;


class Counter {
    private int value ;
    synchronized void increment(){value+=1;}
    synchronized int value(){return value;}
}

class  Printer extends Thread{
    final int I;
    final Counter c;

    public Printer(int I, Counter c){
        this.I= I;
        this.c = c;
    }

    public void run(){
        for (int i =0 ; i<I ;++i)
            c.increment();
    }
}
public class main {
    public static void main (String [] args) throws  InterruptedException{
        final int N = Integer.parseInt(args[0]);
        final int I = Integer.parseInt(args[1]);
        Thread a[] = new  Thread[N];
        Counter c = new Counter();

        for(int i =0; i<N;++i)
            a[i] = new Printer(I,c);


        for(int i=0; i<N; ++i) a[i].start();
        for(int i=0; i<N; ++i) a[i].join();

        System.out.println(c.value());


    }
}
