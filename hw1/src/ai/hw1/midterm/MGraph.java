package ai.hw1.midterm;

import ai.hw1.epuzzle.UGraphOne;
import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class MGraph extends ExtendableMap {

    MGraph(){
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

    private static void initMap(ExtendableMap map) {
        map.clear();

        map.addBidirectionalLink(S, A, 2.0);
        map.addBidirectionalLink(S, B, 4.0);
        map.addBidirectionalLink(S, C, 3.0);
        map.addBidirectionalLink(A, B, 1.0);
        map.addBidirectionalLink(A, C, 2.0);
        map.addBidirectionalLink(B, C, 4.0);
        map.addBidirectionalLink(B, G, 2.0);
        map.addBidirectionalLink(C, G, 5.0);


    }

    public static void main(String[] args) throws Exception {
        MGraph graphOne = new MGraph();
        Problem problem = new Problem(UGraphOne.S, MapFunctionFactory.getActionsFunction(graphOne),
                MapFunctionFactory.getResultFunction(), new DefaultGoalTest(UGraphOne.G), new MapStepCostFunction(graphOne));
        SearchUtils.PRINT_STEPS = true;
        UniformCostSearch uniformCostSearch = new UniformCostSearch();
        SearchAgent searchAgent = new SearchAgent(problem, uniformCostSearch);
        System.out.println(searchAgent.getInstrumentation());
    }

}
