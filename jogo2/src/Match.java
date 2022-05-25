interface Partida{
    String adivinha(int n);
}

public class Match implements  Partida{
    private int n ;
    private String a;
    private int totalRespostas;

    Match(){
        this.totalRespostas = 0 ;

    }



    public synchronized String adivinha(int n) {
        n =1;
        return a;
    }
}
