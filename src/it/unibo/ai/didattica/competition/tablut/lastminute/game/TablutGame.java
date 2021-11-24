package it.unibo.ai.didattica.competition.tablut.lastminute.game;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.exceptions.*;

import java.util.List;

public class TablutGame implements Game {

    private MyStateTablut initialState;

    public TablutGame(int depth){
        this.initialState = new MyStateTablut(depth);
    }

    @Override
    public List<XYOld> getActions(MyStateTablut state){
        return state.getLegalMoves();
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
    public State checkMove(State state, Action a) throws BoardException, ActionException, StopException, PawnException, DiagonalException, ClimbingException, ThroneException, OccupitedException, ClimbingCitadelException, CitadelException {
        return null;
    }

    @Override
    public void endGame(State state) {

    }
}
