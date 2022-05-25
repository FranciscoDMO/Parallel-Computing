interface Partida {
    boolean aposta(int n, int soma);
}


public class ThePartida implements Partida {

    private final int numberOfPlayers;
    private int waitingPlayers;
    private int soma;

    public ThePartida(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.waitingPlayers = this.soma = 0;

    }


    @Override
    public synchronized boolean aposta(int n, int apostaDaSoma) {

        waitingPlayers++;
        soma += n;
        while (numberOfPlayers != waitingPlayers) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        notify();

        return soma == apostaDaSoma;
    }
}
