import java.nio.channels.MulticastChannel;

class MinhaThread extends Thread{
    public void run(){
        for(int i =0; i<10;++i)
            System.out.println("Ola");
    }
}

class MeuRunnable implements Runnable{
    public void run(){
        try{
            for(int i =0; i<10;++i)


                System.out.println("Ola");
                Thread.sleep(500);
        }catch (InterruptedException e){
            System.out.println("Thread interrompida");
        }
    }
}


class MeuRunnable1 implements Runnable{
    public void run(){

        for(int i =0; i<10;++i)
            System.out.println("Ola");


    }
}

class MeuRunnable2 implements Runnable{
    public void run(){

        for(int i =0; i<1000;++i)
            System.out.println("bye");


    }
}
class Stack{
    void push(int i) throws  InterruptedException{

    }
}
public class main {
    public static void main (String [] args) throws  InterruptedException{
        //MinhaThread t = new MinhaThread();
        //t.run();

        //MeuRunnable r= new MeuRunnable();
        //Thread t =  new Thread(r);

        Thread t1  = new Thread(new MeuRunnable());
        Thread t2   = new Thread(new MeuRunnable());

        t1.start();
        t2.start();
        for (int i=0;i<10;i++){
            System.out.println("main");

        }

        t1.join();
        t2.join();

        System.out.println("depois");


    }
}
