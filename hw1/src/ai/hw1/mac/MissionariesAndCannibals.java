package ai.hw1.mac;

import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class MissionariesAndCannibals {

    private Problem problem;

    /**
     * Initializes initial and goal states. The problem is formulated using action and result functions.
     */
    MissionariesAndCannibals() {
        MACEnvironment initialState = new MACEnvironment(3, 3, 1);
        MACEnvironment goalState = new MACEnvironment(0, 0, 0);
        problem = new Problem(initialState, new MACActionFunction(), new MACResultFunction(), new MACGoalTest(goalState));
        SearchUtils.PRINT_STEPS = true;
    }

    /**
     * Generalized method for executing given search strategy
     * @param searchForActions - Search strategy to execute
     */
    public void runSearch(SearchForActions searchForActions) {
        try {
            SearchAgent sa = new SearchAgent(problem, searchForActions);
            System.out.println();
            System.out.println("Path cost = " + sa.getInstrumentation().get("pathCost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs Uniform cost search
     */
    public void runUniformCostSearch() {
        System.out.println("==== Uniform cost search");
        UniformCostSearch ucs = new UniformCostSearch(new GraphSearch());
        runSearch(ucs);
    }

    /**
     * Performs Iterative Deepening search
     */
    public void runIterativeDeepeningSearch() {
        System.out.println("==== Iterative Deepening Search");
        IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
        runSearch(ids);
    }

    /**
     * Performs Greedy BFS
     */
    public void runGreedyBFSearch() {
        System.out.println("==== Greedy Best first Search");
        GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch(new GraphSearch(), new MACHeurisiticFunction());
        runSearch(greedyBestFirstSearch);
    }

    /**
     * Performs A* Search
     */
    public void runAStarSearch() {
        System.out.println("==== A* search");
        AStarSearch aStarSearch = new AStarSearch(new GraphSearch(), new MACHeurisiticFunction());
        runSearch(aStarSearch);
    }

    /**
     * Performs RBFS
     */
    public void runRBFSearch() {
        System.out.println("==== RBFS");
        RecursiveBestFirstSearch recursiveBestFirstSearch = new RecursiveBestFirstSearch(new AStarEvaluationFunction(new MACHeurisiticFunction()), true);
        runSearch(recursiveBestFirstSearch);
    }

    public static void main(String[] args) {
        MissionariesAndCannibals missionariesAndCannibals = new MissionariesAndCannibals();
        missionariesAndCannibals.runUniformCostSearch();
        System.out.println();
        missionariesAndCannibals.runAStarSearch();
        System.out.println();
        missionariesAndCannibals.runGreedyBFSearch();
        System.out.println();
        missionariesAndCannibals.runRBFSearch();
        System.out.println();
        missionariesAndCannibals.runIterativeDeepeningSearch();
    }

}
