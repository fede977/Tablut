package it.unibo.ai.didattica.competition.tablut.lastminute.game;

import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TablutGame implements Game<TablutState, XYWho, Turn> {

	private TablutState initialState;
	
	public TablutGame(int depth) {
		this.initialState = new TablutState(depth);
	}

	@Override
	public List<XYWho> getActions(TablutState state) {
		return state.getAllLegalMoves();
	}

	@Override
	public TablutState getInitialState() {
		return this.initialState;
	}

	@Override
	public Turn getPlayer(TablutState state) {
		return state.getTurn();
	}

	@Override
	public Turn[] getPlayers() {
		return new Turn[] {Turn.WHITE, Turn.BLACK};
	}

	@Override
	public double getUtility(TablutState state, Turn player) {
		double toreturn = 0;
		if(state.getCurrentDepth() == 0) {
			if(player.equals(Turn.WHITE)) {
				toreturn = 200000*state.isTheKingOnEscapeArea()
						+ 200000*state.playerCannotMoveAnyPawn(Pawn.BLACK)
						+ 100*(state.getDistanceFromKingToClosestEscapeArea())
						+ 6*state.getNumberOf(Pawn.WHITE)
						+ 20*state.getDistanceFromKingToAllBlacks()
						+ 2*state.getNumberCloseToTheKingOf(Pawn.WHITE)
						- 2*state.isTheKingInTheThrone()
						- 10*state.getNumberCloseToTheKingOf(Pawn.BLACK)
						- 2*state.getNumberOfCampsCloseToKing()
						- 4*state.getNumberOf(Pawn.BLACK)
						- 200000*state.hasBlackWon();
				
			} else if(player.equals(Turn.BLACK)) {
				toreturn = 200000*state.hasBlackWon() 
						+ 200000*state.playerCannotMoveAnyPawn(Pawn.WHITE)
						+ 50*state.getNumberCloseToTheKingOf(Pawn.BLACK)
						+ 10*state.getNumberOf(Pawn.BLACK)
						+ 10*state.getNumberOfCampsCloseToKing()
						- 100*state.getDistanceFromKingToClosestEscapeArea()
						- 25*state.getDistanceFromKingToAllBlacks()
						- 25*state.getNumberOf(Pawn.WHITE)
						- 200000*state.isTheKingOnEscapeArea();
			}
		}
		return toreturn;
	}

	@Override
	public boolean isTerminal(TablutState state) {
		return state.getCurrentDepth() == 0;
	}

	@Override
	public TablutState getResult(TablutState state, XYWho action) {
		TablutState result = state.clone();
		result.applyMove(action);
		return result;
	}

	
	@Override
	public int getCurrentDepth(TablutState state) {
		return state.getCurrentDepth();
	}

	@Override
	public void setCurrentDepth(TablutState state, int depth) {
		state.setCurrentDepth(depth);
		
	}
	
}
