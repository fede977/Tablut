package it.unibo.ai.didattica.competition.tablut.lastminute.client;

import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.lastminute.algorithms.AlphaBetaSearch;

import java.io.IOException;
import java.net.UnknownHostException;

public class LastMinuteClient extends TablutClient{

    private int d = 4;
    private TablutGame tablutGame = new TablutGame(this.d);
    private AlphaBetaSearch<MyStateTablut, XTWho, Turn>(this.tablutGame,this.d);

    public LastMinuteClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player,name,timeout,ipAddress);
    }

    @Override
    public void run(){

        try{
            this.declareName();
        }catch(Exception e){}

        State state;
        Game rules = null;
        state = new StateTablut();
        state.setTurn(State.Turn.WHITE);
        rules = null;
        System.out.println("Player: " + this.getPlayer().toString() + "!");

        while(true){
            try{
                this.read;
            }catch(ClassNotFoundException | IOException e){
                System.exit(1);
            }

            state = this.getCurrentState();


        }
    }
}
