package it.unibo.ai.didattica.competition.tablut.lastminute.client;

import java.io.IOException;

import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.lastminute.algorithm.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.lastminute.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.LastMinuteStateTablut;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.TablutGame;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.XYWho;

public class LastMinuteClient extends TablutClient {

	private int depth = 5;
	private TablutGame tablutGame = new TablutGame(this.depth);
	private AlphaBetaSearch<LastMinuteStateTablut, XYWho, Turn> alphabeta = new AlphaBetaSearch<LastMinuteStateTablut, XYWho, Turn> (this.tablutGame, this.depth);
	
	public LastMinuteClient(String player, String name, int timeout, String ip) throws UnknownHostException, IOException {
		super(player, name, timeout, ip);
	}

	/**
	 * Checks the current state, the turn and looks for a new move   
	 */
	@Override
	public void run() {

		try {
			this.serverWriteName();
		} catch (Exception e) {
		}

		State s;
		Game rules = null;
		s = new StateTablut();
		s.setTurn(State.Turn.WHITE);
		rules = new GameAshtonTablut(99, 0, "LastMinuteLogs", "testWhite", "testBlack");
		
		System.out.println("You are player " + this.getPlayer().toString());

		while (true) {
			
			try {
				this.serverReadState();
			} catch (ClassNotFoundException | IOException e1) {
				System.exit(1);
			}
			
			s = this.getCurrentState();
			LastMinuteStateTablut lms = new LastMinuteStateTablut(this.depth).stateAdapter(this.getCurrentState());
			lms.printBoard();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			if (this.getPlayer().equals(Turn.WHITE)) {
				if (s.getTurn().equals(Turn.WHITE)) {
					
					Action a = null;
					XYWho xyw = this.alphabeta.makeDecision(lms);
					String from = s.getBox(xyw.getWho()[0], xyw.getWho()[1]);
					String to = s.getBox(xyw.getX(), xyw.getY());
					try {
						a = new Action(from, to, StateTablut.Turn.WHITE);
					} catch (IOException e1) {
					}
					System.out.println("Chosen move: " + a.toString());
					try {
						this.serverWriteAction(a);
					} catch (ClassNotFoundException | IOException e) {
					}
				

				}
			} else {

				if (s.getTurn().equals(Turn.BLACK)) {
					Action a = null;
					XYWho xyw = this.alphabeta.makeDecision(lms);
					String from = s.getBox(xyw.getWho()[0], xyw.getWho()[1]);
					String to = s.getBox(xyw.getX(), xyw.getY());
					
					try {
						a = new Action(from, to, StateTablut.Turn.BLACK);
					} catch (IOException e1) {
					}
					System.out.println("Chosen move: " + a.toString());
					
					try {
						this.serverWriteAction(a);
					} catch (ClassNotFoundException | IOException e) {
					}
				}
			}
		}	
	}
	
	/**
	 * Runs the lastminute client
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String role = "";
		String name = "LastMinute";
		int timeout = 0;
		String serverIpAddress = "";
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		}
		if (args.length < 3) {
			System.out.println("You must specify in the following order which player you are (WHITE or BLACK), timeout time (in seconds) and server IP address.");
			System.exit(-1);
		} else if (args.length == 3) {
			role = args[0];
			name = name + args[0];
			timeout = Integer.valueOf(args[1]);
			serverIpAddress = args[2];
		} else {
			System.out.println("Too many parameters, you must specify in the following order which player you are (WHITE or BLACK), timeout time (in seconds) and server IP address. ");
			System.exit(-1);	
		}
		
		System.out.println("Selected client: " + args[0]);
		LastMinuteClient client = new LastMinuteClient(role, name, timeout, serverIpAddress);
		client.run();
	}
}