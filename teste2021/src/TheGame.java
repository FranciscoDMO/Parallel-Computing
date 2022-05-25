interface Jogo{
     Partida participa();
}


public class TheGame implements Jogo{

    private int numWaitingPlayers;
    private int timer ;
    private Partida atual ;

    TheGame(){
        this.atual= null;
        this.timer=0;
        this.numWaitingPlayers=0;
    }

    @Override
    public synchronized Partida participa() {

        numWaitingPlayers++;
        while((numWaitingPlayers < 2 && timer <5) ||  numWaitingPlayers < 9 ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        notifyAll();

        if(atual == null)
            atual =  new ThePartida(numWaitingPlayers);

        return atual;
    }

}
