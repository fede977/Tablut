package it.unibo.ai.didattica.competition.tablut.lastminute.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.List;

public class TablutGame implements Game<MyStateTablut, XYOld, State.Turn> {

    private MyStateTablut initialState;

    public TablutGame(int depth){
        this.initialState = new MyStateTablut(depth);
    }

    @Override
    public List<XYOld> getActions(MyStateTablut state){
        return state.getAllMoves();
    }

    @Override
    public MyStateTablut getInitialState(){
        return this.initialState;
    }

    @Override
    public State.Turn getPlayer(MyStateTablut state){
        return state.getTurn();
    }

    @Override
    public State.Turn[] getPlayers(){
        return new State.Turn[] {State.Turn.WHITE, State.Turn.BLACK};
    }

    @Override
    public int getCurrentDepth(MyStateTablut state){
        return state.getCurrentDepth();
    }

    @Override
    public void setCurrentDepth(MyStateTablut state, int depth){
        state.setCurrentDepth(depth);
    }

    @Override
    public boolean isTerminal(MyStateTablut state){
        return state.getCurrentDepth()==0;
    }

    @Override
    public double getUtility(MyStateTablut state, State.Turn player) {
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
    public MyStateTablut getResult(MyStateTablut state, XYOld action) {
        MyStateTablut result = state.clone();
        result.applyMove(action);
        return result;
    }
}
