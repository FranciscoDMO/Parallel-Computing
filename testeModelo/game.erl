-module(game).
-export([start/0]).

game_manager(Waiting_Players)->
	receive

		{enter,Pid} when length(Waiting_Players) == 3 -> spawn(fun()-> match([Pid | Waiting_Players],100) end),
					  									 game_manager([]);

		{enter,Pid} -> game_manager([Pid | Waiting_Players])

	end.



match(Players,Tentativas)->

	X = rand:uniform(1000),
	lists:foreach(fun(Pid)-> Pid ! {match,self()} end,Players),
	timer:send_after(60000,self(),time),

	receive

		{guess,_,_} when Tentativas == 0 -> lists:foreach(fun(Pid) -> Pid ! tentativas end, Players);

		time -> lists:foreach(fun(Pid)-> Pid ! tempo end, Players);

		{guess,N,Pid} when N == X ->
									Pid ! ganhou,
									lists:foreach(fun(P) -> P ! perdeu end, Players--[Pid]);

		{guess,_,Pid} -> Pid ! errado,
						 match(Players,Tentativas-1)

	end.

player(Num)->

	game_manager ! {enter,self()},

	receive
		{match,Pid} -> Match = Pid
	end,

	guesser(Match,Num).


guesser(Match,Num)->
	X = rand:uniform(1000),
	Match ! {guess,X,self()}, 
	
	receive
		ganhou -> io:format("Sou o jogador ~p e GANHEI ~n",[Num]);

		perdeu -> io:format("Sou o jogador ~p e PERDI ~n",[Num]);

		time -> io:format("Sou o jogador ~p e TEMPO ACABOU ~n",[Num]);

		tentativas -> io:format("Sou o jogador ~p e TENTATIVAS ACABARAM ~n",[Num]);

		errado -> guesser(Match,Num)

	end.


start()-> register(game_manager,spawn(fun()->game_manager([]) end)),
		  spawn(fun()->player(1) end),
		  spawn(fun()->player(2) end),
		  spawn(fun()->player(3) end),
		  spawn(fun()->player(4) end),
		  spawn(fun()->player(5) end),
		  spawn(fun()->player(6) end),
		  spawn(fun()->player(7) end),
		  spawn(fun()->player(8) end),
		  spawn(fun()->player(9) end).