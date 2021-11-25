package it.unibo.ai.didattica.competition.tablut.lastminute.algorithms;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.competition.tablut.lastminute.game.Game;

public class AlphaBetaSearch<S,A,P> implements AdversarialSearch<S,A>{

    public final static String METRICS_NODE_EXPANDED = "nodesExpanded";
    Game<S,A,P> game;
    private Metrics metrics = new Metrics();
    private int depth;

    public AlphaBetaSearch(Game<S,A,P> game, int depth){
        this.game = game;
        this.depth = depth;
    }

    @Override
    public A makeDecision(S state){
        metrics = new Metrics();
        A result = null;
        int depth = this.depth;
        double resultValue = Double.NEGATIVE_INFINITY;
        P player = game.getPlayer(state);
        for (A action: game.getActions(state)){
            double currentValue = minValue(game.getResult(state,action), player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth-1);
            if (currentValue > resultValue){
                result = action;
                resultValue = currentValue;
            }
        }
        return result;
    }

    public double maxValue(S state, P player, double alpha, double beta, int depth){
        metrics.incrementInt(METRICS_NODE_EXPANDED);
        this.game.setCurrentDepth(state,depth);
        if (game.isTerminal(state)){
            return game.getUtility(state, player);
        }
        double currentValue = Double.NEGATIVE_INFINITY;
        for (A action:game.getActions(state)){
            currentValue = Math.max(currentValue,minValue(game.getResult(state,action),player,alpha,beta,depth-1));
            if (currentValue>=beta&&(currentValue!=Double.POSITIVE_INFINITY)){
                return currentValue;
            }
            alpha = Math.max(alpha,currentValue);
        }
        if(currentValue!=Double.POSITIVE_INFINITY){
            return currentValue;
        }
        return 0;
    }

    public double minValue(S state, P player, double alpha, double beta, int depth){
        metrics.incrementInt(METRICS_NODE_EXPANDED);
        this.game.setCurrentDepth(state,depth);
        if(game.isTerminal(state)){
            return game.getUtility(state,player);
        }
        double currentValue = Double.POSITIVE_INFINITY;
        for (A action: game.getActions(state)){
            currentValue=Math.min(currentValue,maxValue(game.getResult(state,action),player,alpha,beta,depth-1));
            if(currentValue<=alpha&&(currentValue!=Double.NEGATIVE_INFINITY)){
                return currentValue;
            }
            beta=Math.min(beta,currentValue);
        }
        if(currentValue!= Double.NEGATIVE_INFINITY){
            return currentValue;
        }
        return 0;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }
}
