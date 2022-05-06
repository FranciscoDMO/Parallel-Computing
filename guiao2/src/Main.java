import java.util.Random;

class Transferer extends Thread {
    final Bank b;

    Transferer(Bank b) {
        this.b = b;
    }

    public void run() {
        try {
            Random r = new Random();
            while (true) {
                int i = r.nextInt(5);
                int j = r.nextInt(5);
                b.transfer(i, j, 1);
            }
        } catch (Exception e) { }
    }
}

class Checker extends Thread {
    final Bank b;
    Checker(Bank b) { this.b = b; }
    public void run() {
        int i=0;
        try {
            int[] a = {0,1,2,3,4};
            while (true) {
                i++;
                int sum = b.totalBalance(a);
                if(sum !=50000)
                    System.out.println(sum);
                if(i%100000==0)
                    System.out.println("vivo");

            }
        } catch (Exception e) { }
    }
}



class TesteBanco {
    public static void main(String[] args) throws InvalidAccount {
        int N = 10;
        Bank b = new BankImpl(N);
        for (int i = 0; i < N ; ++i)
            b.deposit(i,10000);
        new Transferer(b).start();
        new Transferer(b).start();
        new Checker(b).start();
    }
}