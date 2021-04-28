package ai.hw1.epuzzle;

import ai.hw1.usa.ConsistentHeurisitcChecker;
import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class UGraphOne extends ExtendableMap {

    public UGraphOne() {
        initMap(this);
    }

    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String E = "E";
    public static final String G = "G";
    public static final String F = "F";
    public static final String H = "H";
    public static final String S = "S";

    public static void initMap(ExtendableMap map) {
        map.clear();

        // Creating the graph
        map.addUnidirectionalLink(S, A, 1.0);
        map.addUnidirectionalLink(S, B, 2.0);
        map.addUnidirectionalLink(S, C, 3.0);
        map.addUnidirectionalLink(A, H,5.0);
        map.addUnidirectionalLink(A, D,1.0);
        map.addUnidirectionalLink(B, D,2.0);
        map.addUnidirectionalLink(B, E,1.0);
        map.addUnidirectionalLink(C, E,3.0);
        map.addUnidirectionalLink(C, F,2.0);
        map.addUnidirectionalLink(D, H,3.0);
        map.addUnidirectionalLink(D, G,3.0);
        map.addUnidirectionalLink(E, G,4.0);
        map.addUnidirectionalLink(F, E,6.0);
        map.addUnidirectionalLink(H, G,1.0);
    }

    public static void main(String[] args) throws Exception {
        UGraphOne graphOne = new UGraphOne();
        Problem problem = new Problem(UGraphOne.S, MapFunctionFactory.getActionsFunction(graphOne),
                MapFunctionFactory.getResultFunction(), new DefaultGoalTest(UGraphOne.G), new MapStepCostFunction(graphOne));
        SearchUtils.PRINT_STEPS = true;
        UniformCostSearch uniformCostSearch = new UniformCostSearch(new GraphSearch());
        SearchAgent searchAgent = new SearchAgent(problem, uniformCostSearch);
        System.out.println(searchAgent.getInstrumentation());
    }



}
