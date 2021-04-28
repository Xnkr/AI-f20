package ai.hw1.usa;

import aima.core.environment.map.Map;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.QueueFactory;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.problem.Problem;
import aima.core.util.CancelableThread;

import java.util.*;

public class ConsistentHeurisitcChecker {

    /**
     * Performs Queue search and in each step checks for consistent heuristic criteria
     * @param map - Road map of USA
     * @param problem - Problem formulation
     * @param ef - Evaluation function
     */
    public static void checkConsistency(Map map, Problem problem, EvaluationFunction ef) {
        Queue<Node> frontier = QueueFactory.<Node>createFifoQueue();
        NodeExpander nodeExpander = new NodeExpander();
        nodeExpander.useParentLinks(true);

        Node root = nodeExpander.createRootNode(problem.getInitialState());
        Set<Object> explored = new HashSet<>();
        frontier.add(root);
        explored.add(root.getState());
        try {
            while (!frontier.isEmpty()) {
                Node nodeToExpand = frontier.poll();
                String current = nodeToExpand.getState().toString();
                List<Node> successors = nodeExpander.expand(nodeToExpand, problem);
                for (Node successor : successors) {
                    String child = successor.getState().toString();
                    if (!explored.contains(child)) {
                        explored.add(child);
                        frontier.add(successor);
                        System.out.print(String.format("Checking if h(%s) <= h(%s) + c(%s,%s): ", current, child, current, child));
                        if (ef.f(nodeToExpand) > (ef.f(successor) + map.getDistance(current, child))){
                            throw new Exception("Parent Node " + current + " Child node " + child);
                        }
                        System.out.println(" true");
                    }
                }
            }
            System.out.println();
            System.out.println("Heuristic is Consistent");
        } catch (Exception e) {
            System.out.println("False");
            System.out.println();
            System.out.println("Heuristic is Inconsistent");
        }
    }

}
