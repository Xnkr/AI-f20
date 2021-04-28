package ai.hw1.midterm;

import ai.hw1.usa.RoadMapOfUSA;
import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;

public class AminoAcid extends ExtendableMap {

    public static final String AM1 = "AM1";
    public static final String AM2 = "AM2";
    public static final String AM3 = "AM3";
    public static final String AM4 = "AM4";
    public static final String AM5 = "AM5";
    public static final String AM6 = "AM6";
    public static final String AM7 = "AM7";
    public static final String AM8 = "AM8";
    public static final String AM9 = "AM9";
    public static final String AM10 = "AM10";
    public static final String AM11 = "AM11";

    private static void initMap(ExtendableMap map) {
        map.clear();

        // Creating the graph
        map.addBidirectionalLink(AM11, AM4, 50.0);
        map.addBidirectionalLink(AM11, AM10, 150.0);
        map.addBidirectionalLink(AM11, AM9, 15.0);
        map.addBidirectionalLink(AM4, AM7, 40.0);
        map.addBidirectionalLink(AM7, AM8, 180.0);
        map.addBidirectionalLink(AM7, AM6, 110.0);
        map.addBidirectionalLink(AM9, AM8, 70.0);
        map.addBidirectionalLink(AM10, AM2, 30.0);
        map.addBidirectionalLink(AM8, AM2, 45.0);
        map.addBidirectionalLink(AM10, AM3, 80.0);
        map.addBidirectionalLink(AM3, AM5, 50.0);
        map.addBidirectionalLink(AM5, AM1, 40.0);
        map.addBidirectionalLink(AM1, AM6, 70.0);
        map.addBidirectionalLink(AM6, AM8, 20.0);
        map.addBidirectionalLink(AM1, AM4, 350.0);

        map.setPosition(AM2, 0, 0);
        map.setPosition(AM1, 160, 0);
        map.setPosition(AM3, 100, 0);
        map.setPosition(AM4, 200, 0);
        map.setPosition(AM5, 120, 0);
        map.setPosition(AM6, 80, 0);
        map.setPosition(AM7, 250,0);
        map.setPosition(AM8, 40, 0);
        map.setPosition(AM9, 60, 0);
        map.setPosition(AM10, 25, 0);
        map.setPosition(AM11, 100, 0);

    }

    AminoAcid(){
        initMap(this);
    }

    public static void main(String[] args) {
        String goalCity = AminoAcid.AM2;
        ExtendableMap map = new AminoAcid();
        Problem problem = new Problem(AminoAcid.AM1,
                MapFunctionFactory.getActionsFunction(map),
                MapFunctionFactory.getResultFunction(),
                new DefaultGoalTest(goalCity),
                new MapStepCostFunction(map)
        );
        HeuristicFunction heuristicFunction = MapFunctionFactory.getSLDHeuristicFunction(goalCity, map);
        EvaluationFunction evaluationFunction = new AStarEvaluationFunction(heuristicFunction);
        SearchUtils.PRINT_STATS = true;
        SearchUtils.PRINT_STEPS = true;

        AStarSearch search = new AStarSearch(new GraphSearch(evaluationFunction), heuristicFunction);
        try {
            SearchAgent sa = new SearchAgent(problem, search);
            System.out.println();
            System.out.println("Path cost = " + sa.getInstrumentation().get("pathCost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
