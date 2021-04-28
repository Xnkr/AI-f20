package ai.hw1.usa;

import aima.core.environment.map.Map;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;

public class USASearch {

    Problem problem;
    Map map;
    HeuristicFunction heuristicFunction;
    EvaluationFunction evaluationFunction;

    /**
     * Initializes map, specifies initial and goal states. The problem is formulated using AIMA provided action and result functions.
     * Straight line distance is used as the heuristic
     */
    USASearch() {
        String initialCity = RoadMapOfUSA.SEATTLE;
        String goalCity = RoadMapOfUSA.DALLAS;
        map = new RoadMapOfUSA();
        problem = new Problem(initialCity,
                MapFunctionFactory.getActionsFunction(map),
                MapFunctionFactory.getResultFunction(),
                new DefaultGoalTest(goalCity),
                new MapStepCostFunction(map)
        );
        heuristicFunction = MapFunctionFactory.getSLDHeuristicFunction(RoadMapOfUSA.DALLAS, map);
        evaluationFunction = new AStarEvaluationFunction(heuristicFunction);
        SearchUtils.PRINT_STATS = true;
    }

    /**
     * Generalized method for executing given search strategy
     * @param search - Search strategy to execute
     */
    public void runSearch(SearchForActions search) {
        try {
            SearchAgent sa = new SearchAgent(problem, search);
            System.out.println();
            System.out.println("Path cost = " + sa.getInstrumentation().get("pathCost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs A* Search
     */
    public void runAStarSearch() {
        System.out.println("==== A* search");
        AStarSearch search = new AStarSearch(new GraphSearch(evaluationFunction), heuristicFunction);
        runSearch(search);
    }

    /**
     * Performs RBFS
     */
    public void runRBFS() {
        System.out.println("==== RBFS");
        RecursiveBestFirstSearch search = new RecursiveBestFirstSearch(evaluationFunction);
        runSearch(search);
    }

    public static void main(String[] args) {
        USASearch usaSearch = new USASearch();
        usaSearch.runAStarSearch();
        System.out.println();
        usaSearch.runRBFS();

        System.out.println("Checking for Consistent Heuristic");
        ConsistentHeurisitcChecker.checkConsistency(usaSearch.map, usaSearch.problem, usaSearch.evaluationFunction);
    }

}


