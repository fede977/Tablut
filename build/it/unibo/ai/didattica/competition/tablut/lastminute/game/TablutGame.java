package it.unibo.ai.didattica.competition.tablut.lastminute.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.List;

public class TablutGame implements Game<LMStateTablut, Positions, State.Turn> {

    private LMStateTablut initialState;

    public TablutGame(int depth){
        this.initialState = new LMStateTablut(depth);
    }

    @Override
    public List<Positions> getActions(LMStateTablut state){
        return state.getAllMoves();
    }

    @Override
    public LMStateTablut getInitialState(){
        return this.initialState;
    }

    @Override
    public State.Turn getPlayer(LMStateTablut state){
        return state.getTurn();
    }

    @Override
    public State.Turn[] getPlayers(){
        return new State.Turn[] {State.Turn.WHITE, State.Turn.BLACK};
    }

    @Override
    public int getCurrentDepth(LMStateTablut state){
        return state.getCurrentDepth();
    }

    @Override
    public void setCurrentDepth(LMStateTablut state, int depth){
        state.setCurrentDepth(depth);
    }

    @Override
    public boolean isTerminal(LMStateTablut state){
        return state.getCurrentDepth()==0;
    }

    @Override
    public double getUtility(LMStateTablut state, State.Turn player) {
        double result=0;
        if(player.equals(State.Turn.WHITE)){
            result += 200000*state.isKingEscaped()
                    + 200000*state.noMovesLeft(State.Pawn.BLACK)
                    + 100*(state.getDistanceFromClosestEscape())
                    + 6*state.getNumberOf(State.Pawn.WHITE)
                    + 20*state.getDistanceFromBlacks()
                    + 2*state.getNumberCloseToTheKingOf(State.Pawn.WHITE)
                    - 2*state.isKingInCastle()
                    - 10*state.getNumberCloseToTheKingOf(State.Pawn.BLACK)
                    - 2*state.getNumberOfCampsCloseToKing()
                    - 4*state.getNumberOf(State.Pawn.BLACK)
                    - 200000*state.blackWon();
        } else if(player.equals(State.Turn.BLACK)) {
            result = 200000*state.blackWon()
                    + 200000*state.noMovesLeft(State.Pawn.WHITE)
                    + 50*state.getNumberCloseToTheKingOf(State.Pawn.BLACK)
                    + 10*state.getNumberOf(State.Pawn.BLACK)
                    + 10*state.getNumberOfCampsCloseToKing()
                    - 100*state.getDistanceFromClosestEscape()
                    - 25*state.getDistanceFromBlacks()
                    - 25*state.getNumberOf(State.Pawn.WHITE)
                    - 200000*state.isKingEscaped();
        }
        return result;
    }


    @Override
    public LMStateTablut getResult(LMStateTablut state, Positions action) {
        LMStateTablut result = state.clone();
        result.applyMove(action);
        return result;
    }
}
