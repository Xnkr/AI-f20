package ai.hw1.epuzzle;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.BidirectionalEightPuzzleProblem;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.problem.StepCostFunction;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.util.datastructure.XYLocation;

public class EightPuzzle {

    public static StepCostFunction getCostFunc(){
        return (s, a, sDelta) -> {
            EightPuzzleBoard newBoard = (EightPuzzleBoard) sDelta;
            XYLocation absPos = newBoard.getLocationOf(0);
            int cost = 0;
            int x = absPos.getXCoOrdinate();
            int y = absPos.getYCoOrdinate();
            XYLocation newLoc;
            if (x - 1 >= 0){
                newLoc = new XYLocation( x - 1, y);
                cost += newBoard.getValueAt(newLoc);
            }
            if (x + 1 <= 2) {
                newLoc = new XYLocation(x + 1, y);
                cost += newBoard.getValueAt(newLoc);
            }

            if (y - 1 >= 0){
                newLoc = new XYLocation(x , y -1);
                cost += newBoard.getValueAt(newLoc);
            }
            if (y + 1 <= 2){
                newLoc = new XYLocation(x , y + 1);
                cost += newBoard.getValueAt(newLoc);
            }
            return cost;
        };
    }

    public static void main(String[] args) throws Exception {
        EightPuzzleBoard eightPuzzleBoard = new EightPuzzleBoard(new int[]{7,4,2,1,3,5,0,8,6});
        Problem puzzleProblem = new Problem(eightPuzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
                EightPuzzleFunctionFactory.getResultFunction(),
                new DefaultGoalTest(new EightPuzzleBoard(new int[] { 1,2,3,8,0,4,7,6,5 })), getCostFunc());
        EvaluationFunction evaluationFunction = new AStarEvaluationFunction(new ManhattanHeuristicFunction());
        AStarSearch starSearch = new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction());
        SearchUtils.PRINT_STEPS = true;
        SearchUtils.PRINT_STATS = true;
        RecursiveBestFirstSearch recursiveBestFirstSearch = new RecursiveBestFirstSearch(evaluationFunction);
        SearchAgent searchAgent = new SearchAgent(puzzleProblem, recursiveBestFirstSearch);
        System.out.println(searchAgent.getActions());
    }
}
