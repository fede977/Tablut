package it.unibo.ai.didattica.competition.tablut.lastminute.client;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.lastminute.algorithms.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.TablutGame;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.XYOld;

import java.io.IOException;
import java.net.UnknownHostException;

public class LastMinuteClient extends TablutClient {

    private int depth = 4;
    private TablutGame tablutGame = new TablutGame(this.depth);

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
        rules = null;
        System.out.println("Player: " + this.getPlayer().toString() + "!");

        while (true) {
            try {
                this.readState();
            } catch (ClassNotFoundException | IOException e) {
                System.exit(1);
            }

            state = this.getCurrentState();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            if (this.getPlayer().equals(State.Turn.WHITE)) {
                if (state.getTurn().equals(StateTablut.Turn.WHITE)) {

                    Action a = null;
                    try {
                        a = findMove(tablutGame,state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Mossa: " + a.toString());
                    try {
                        this.writeAction(a);
                    } catch (ClassNotFoundException | IOException e) {
                    }
                } else if (state.getTurn().equals(StateTablut.Turn.BLACK)){
                    System.out.println("Witing for opponent...\n");
                } else if(state.getTurn().equals(StateTablut.Turn.WHITEWIN)){
                    System.out.println("You Won!!");
                    System.exit(0);
                } else if(state.getTurn().equals(StateTablut.Turn.BLACKWIN)){
                    System.out.println("You Lost!!");
                    System.exit(0);
                } else if(state.getTurn().equals(State.Turn.DRAW)){
                    System.out.println("Draw");
                    System.exit(0);
                }
            } else {

                if (state.getTurn().equals(StateTablut.Turn.BLACK)) {

                    Action a = null;
                    try {
                        a = findMove(tablutGame,state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Mossa: " + a.toString());
                    try {
                        this.writeAction(a);
                    } catch (ClassNotFoundException | IOException e) {
                    }
                } else if (state.getTurn().equals(StateTablut.Turn.WHITE)){
                    System.out.println("Witing for opponent...\n");
                } else if(state.getTurn().equals(StateTablut.Turn.WHITEWIN)){
                    System.out.println("You Lost!!");
                    System.exit(0);
                } else if(state.getTurn().equals(StateTablut.Turn.BLACKWIN)){
                    System.out.println("You Won!!");
                    System.exit(0);
                } else if(state.getTurn().equals(State.Turn.DRAW)){
                    System.out.println("Draw");
                    System.exit(0);
                }
            }
        }
    }



    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
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

    private Action findMove(TablutGame tablutGame, State state) throws IOException {
        AlphaBetaSearch<State, XYOld, State.Turn> abSearch = new AlphaBetaSearch<State, XYOld, State.Turn>(this.tablutGame,4);
        XYOld tempA = abSearch.makeDecision(state);
        String from = state.getBox(tempA.getOld()[0], tempA.getOld()[1]);
        String to = state.getBox(tempA.getX(), tempA.getY());
        Action a = new Action(from,to, state.getTurn());
        return a;
    }
}
