interface Jogo{

    Partida participa () throws InterruptedException;
}


public class Game implements Jogo{


    private int numPlayers;
    private Match m;

    Game(){
        this.numPlayers =0;
        this.m = new Match();


    }


    public synchronized Partida participa() throws InterruptedException {

        this.numPlayers++;
        while(numPlayers!=4){
            wait();

        }
        if(this.numPlayers==4)
            notifyAll();


        return m ;
    }
}
