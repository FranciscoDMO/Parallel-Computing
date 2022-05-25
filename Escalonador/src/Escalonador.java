
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Escalonador  {
    private int N;
    private int numThreadsEmExecucao;
    private int numThreadsPrioridade1Esperando;
    private int numThreadsPrioridade2Esperando;
    private final Lock lock = new ReentrantLock();
    private final Condition prioridade1Disponivel;
    private final Condition prioridade2Disponivel;

    public Escalonador(int n) {
        this.N = n;
        this.numThreadsEmExecucao = 0;
        this.numThreadsPrioridade1Esperando = 0;
        this.numThreadsPrioridade2Esperando = 0;

        this.prioridade1Disponivel = this.lock.newCondition();
        this.prioridade2Disponivel = this.lock.newCondition();
    }


    public void executa(Runnable tarefa, int prioridade) {
        switch(prioridade) {
            case 3:
                this.executaPrioridade3(tarefa);
                break;
            case 2:
                this.executaPrioridade2(tarefa);
                break;
            case 1:
                this.executaPrioridade1(tarefa);
                break;
        }
    }

    private void executaPrioridade3(Runnable tarefa) {
        this.lock.lock();
        this.numThreadsEmExecucao++;

        Thread thread = new Thread(() -> {
            tarefa.run();

            this.lock.lock();
            this.numThreadsEmExecucao--;
            this.lock.unlock();

            this.checkIfNewThreadNeedsExecution();
        });

        thread.start();

        this.lock.unlock();
    }

    private void executaPrioridade2(Runnable tarefa) {
        this.lock.lock();

        if(this.numThreadsEmExecucao >= this.N) {
            this.numThreadsPrioridade2Esperando++;

            do {
                this.prioridade2Disponivel.awaitUninterruptibly();
            } while(this.numThreadsEmExecucao >= this.N);

            this.numThreadsPrioridade2Esperando--;
        }

        this.numThreadsEmExecucao++;

        Thread t = new Thread(() -> {
            tarefa.run();

            this.lock.lock();
            this.numThreadsEmExecucao--;
            this.lock.unlock();

            this.checkIfNewThreadNeedsExecution();
        });
        t.start();

        this.lock.unlock();
    }

    private void executaPrioridade1(Runnable tarefa) {
        this.lock.lock();

        if(this.numThreadsEmExecucao >= this.N || this.numThreadsPrioridade2Esperando > 0) {
            this.numThreadsPrioridade1Esperando++;

            do {
                this.prioridade1Disponivel.awaitUninterruptibly();
            } while(this.numThreadsEmExecucao >= this.N || this.numThreadsPrioridade2Esperando > 0);

            this.numThreadsPrioridade1Esperando--;
        }

        this.numThreadsEmExecucao++;

        Thread t = new Thread(() -> {
            tarefa.run();

            this.lock.lock();
            this.numThreadsEmExecucao--;
            this.lock.unlock();

            this.checkIfNewThreadNeedsExecution();
        });
        t.start();

        this.lock.unlock();
    }

    private void checkIfNewThreadNeedsExecution() {
        this.lock.lock();

        // Check priority 2 threads
        if(this.numThreadsPrioridade2Esperando > 0 && this.numThreadsEmExecucao < this.N) {
            this.prioridade2Disponivel.signal();
        } else if(this.numThreadsPrioridade2Esperando == 0 && this.numThreadsPrioridade1Esperando > 0 && this.numThreadsEmExecucao < this.N) {
            this.prioridade1Disponivel.signal();
        }

        this.lock.unlock();
    }
}