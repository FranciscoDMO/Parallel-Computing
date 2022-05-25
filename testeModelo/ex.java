import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

interface Jogo{
	Partida participa() throws InterruptedException;
}

interface Partida{
	String adivinha(int n);
}

class Timer extends Thread{
	Match p;

	Timer(Match p){
		this.p = p;
	}

	public void run(){

		while(p.incTime() < 60){
		try{
			Thread.sleep(1000);
		}
		catch(Exception e){

		}
		}
	}
}

class Player extends Thread{
	Partida p;
	int n;

	Player(Partida p, int n){
		this.p = p;
		this.n = n;
	}

	public void run(){
		String res = "MENOR";
		Random rand = new Random();
		while(res == "MENOR" || res == "MAIOR"){
			int t = 0;
			System.out.println("Sou o Jogador " + n + " e vou tentar " + t);
			res = p.adivinha(t);

		}
		System.out.println("Jogador " + n + " " + res);
	}
}

class Jogador extends Thread{
	Game g;
	int n;

	Jogador(Game g, int n){
		this.g = g;
		this.n = n;
	}

	public void run(){
		String res = "MENOR";
		int i = 0;

		try{
		Partida p = g.participa();		
		Random rand = new Random();
		while(res == "MENOR" || res == "MAIOR"){
			int k = rand.nextInt(1001);
			res = p.adivinha(k);
			System.out.println("Jogador " + this.n + " vou testar " + k);
			this.sleep(5000);
			}
			System.out.println("Jogador " + this.n + " " + res);
		}

		catch(InterruptedException e){}
		
		
	}
}

class Match implements Partida{
	int k;
	ReentrantLock l;
	int numberOfPlayers;
	Timer timer;
	int time;
	int tentativas;
	int state; //0 -> ainda não acabou / 1 -> um jogador ganhou / 2 -> tempo esgotou / 3 -> tentativas acabaram

	Match(){
		Random rand = new Random();
		this.numberOfPlayers = 0;
		this.timer = new Timer(this);
		this.k = rand.nextInt(1001);
		System.out.println("k: " + this.k);
		this.l = new ReentrantLock();
		this.time = 0;
		this.tentativas = 100;
		this.state = 0;
		//this.timer.start();
	}

	public void startTimer(){
		this.timer.start();
	}

	public synchronized String adivinha(int n){
		if(state != 0){
			if(state == 1) return "PERDEU";

			else if(state == 2) return "TEMPO";

			else if(state == 3) return "TENTATIVAS";
		}

		if(k < n){
			if (this.tentativas > 0) {
			this.tentativas -= 1;
			return "MENOR";
			}
			else{
				this.state = 3;
				return "TENTATIVAS";
			}
		}

		if(k > n){
			if (this.tentativas > 0) {
			this.tentativas -= 1;
			return "MAIOR";
			}
			else{
				this.state = 3;
				return "TENTATIVAS";
			}

		}

		
		state = 1;
		return "GANHOU";
	}

	public int incTime(){
		this.time += 1;
		System.out.println(this.time + " segundos passaram");

	 

		if(this.time == 60){
			l.lock();
			try{
				state = 2;
			}
			finally{
				l.unlock();
			}
		}
		return this.time;
	}
}

class Game implements Jogo {
	int numberOfPlayers;
	Match m;
	int phase;

	Game(){
		this.numberOfPlayers = 0;
		this.m = new Match();
		this.phase = 1;
	}

	public synchronized Partida participa() throws InterruptedException{

		if(this.numberOfPlayers == 0){
			this.phase = 1;
			this.m = new Match();
		}
		this.numberOfPlayers++;
		while(this.numberOfPlayers < 4 && (this.phase == 1)) wait();

		if(this.numberOfPlayers == 4){
			this.phase = 0;
			this.numberOfPlayers =0;
			this.m.startTimer(); //this.m.startTimer();
			notifyAll();
		}
	

		return this.m;
	}
} 


class Main{

public static void main(String[] args) {
	/*Match m = new Match();
	Player p1 = new Player(m,1);
	Player p2 = new Player(m,2);
	Player p3 = new Player(m,3);
	Player p4 = new Player(m,4);

	p1.start();
	p2.start();
	p3.start();
	p4.start();*/

	Game g = new Game();

	Jogador j1 = new Jogador(g,1);
	Jogador j2 = new Jogador(g,2);
	Jogador j3 = new Jogador(g,3);
	Jogador j4 = new Jogador(g,4);

	Jogador j5 = new Jogador(g,5);
	Jogador j6 = new Jogador(g,6);
	Jogador j7 = new Jogador(g,7);
	Jogador j8 = new Jogador(g,8);

	j1.start();
	j2.start();
	j3.start();
	j4.start();

	j5.start();
	j6.start();
	j7.start();
	j8.start();
}

}