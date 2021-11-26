package it.unibo.ai.didattica.competition.tablut.lastminute.client;

import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.lastminute.algorithms.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.LMStateTablut;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.Positions;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.TablutGame;

import java.io.IOException;
import java.net.UnknownHostException;

public class LastMinuteClient extends TablutClient {

    private int depth = 4;
    private TablutGame tablutGame = new TablutGame(this.depth);
    private AlphaBetaSearch<LMStateTablut, Positions, State.Turn> abSearch = new AlphaBetaSearch<LMStateTablut, Positions, State.Turn>(this.tablutGame,this.depth);

    public LastMinuteClient(String player, String name, int timeout, String ipAddress) throws IOException, UnknownHostException {
        super(player, name, timeout, ipAddress);
    }

    @Override
    public void run() {

        try {
            this.writeName();
        } catch (Exception e) {
        }

        Game rules = null;
        State state = new StateTablut();
        state.setTurn(State.Turn.WHITE);
        rules = new GameAshtonTablut(99,0,"garbage","white","black");
        System.out.println("Player: " + this.getPlayer().toString() + "!\n");

        while (true) {
            try {
                this.readState();
            } catch (ClassNotFoundException | IOException e) {
                System.exit(1);
            }

            state = this.getCurrentState();
            LMStateTablut s = new LMStateTablut(4).stateAdapter(this.getCurrentState());
            s.printBoard();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            if (this.getPlayer().equals(State.Turn.WHITE)) {
                if (state.getTurn().equals(StateTablut.Turn.WHITE)) {

                    Action a = null;
                    Positions tempA = this.abSearch.makeDecision(s);
                    String start = state.getBox(tempA.getOld()[0], tempA.getOld()[1]);
                    String finish = state.getBox(tempA.getX(), tempA.getY());
                    try {
                        a = new Action(start, finish, StateTablut.Turn.WHITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Move: " + a.toString()+"\n");
                    try {
                        this.writeAction(a);
                    } catch (ClassNotFoundException | IOException e) {
                    }
                }
            } else {
                if (state.getTurn().equals(StateTablut.Turn.BLACK)) {

                    Action a = null;
                    Positions tempA = this.abSearch.makeDecision(s);
                    String start = state.getBox(tempA.getOld()[0], tempA.getOld()[1]);
                    String finish = state.getBox(tempA.getX(), tempA.getY());
                    try {
                        a = new Action(start, finish, StateTablut.Turn.BLACK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Move: " + a.toString()+"\n");
                    try {
                        this.writeAction(a);
                    } catch (ClassNotFoundException | IOException e) {
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String role = "";
        String name = "LastMinute";
        int timeout = 0;
        String serverIpAddress = "";

        if(args.length < 1){
            System.out.println("Please write either WHITE or BLACK");
            System.exit(-1);
        }
        if(args.length<3){
            System.out.println("Please write which player are you in the following order: (WHITE | BLACK), timeout (in seconds) and server IP");
            System.exit(-1);
        }else if(args.length == 3){
            role = args[0];
            name = name+args[0];
            timeout = Integer.valueOf(args[1]);
            serverIpAddress = args[2];
        }

        System.out.println("Selected client: " + args[0]);
        LastMinuteClient client = new LastMinuteClient(role,name,timeout,serverIpAddress);
        client.run();
    }
}
