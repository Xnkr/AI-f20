package aima.core.search.informed;

import java.util.*;

import aima.core.agent.Action;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.26, page
 * 99.<br>
 * <br>
 * 
 * <pre>
 * function RECURSIVE-BEST-FIRST-SEARCH(problem) returns a solution, or failure
 *   return RBFS(problem, MAKE-NODE(problem.INITIAL-STATE), infinity)
 *   
 * function RBFS(problem, node, f_limit) returns a solution, or failure and a new f-cost limit
 *   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
 *   successors &lt;- []
 *   for each action in problem.ACTION(node.STATE) do
 *       add CHILD-NODE(problem, node, action) into successors
 *   if successors is empty then return failure, infinity
 *   for each s in successors do // update f with value from previous search, if any
 *     s.f &lt;- max(s.g + s.h, node.f)
 *   repeat
 *     best &lt;- the lowest f-value node in successors
 *     if best.f &gt; f_limit then return failure, best.f
 *     alternative &lt;- the second-lowest f-value among successors
 *     result, best.f &lt;- RBFS(problem, best, min(f_limit, alternative))
 *     if result != failure then return result
 * </pre>
 * 
 * Figure 3.26 The algorithm for recursive best-first search.
 * 
 * <br>
 * This version additionally provides an option to avoid loops. States on the
 * current path are stored in a hash set if the loop avoidance option is enabled.
 * 
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */
public class RecursiveBestFirstSearch implements SearchForActions {

	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
	public static final String METRIC_MAX_RECURSIVE_DEPTH = "maxRecursiveDepth";
	public static final String METRIC_PATH_COST = "pathCost";

	private static final Double INFINITY = Double.MAX_VALUE;

	private final EvaluationFunction evaluationFunction;
	private boolean avoidLoops;
	private final NodeExpander nodeExpander;
	
	// stores the states on the current path if avoidLoops is true.
	Set<Object> explored = new HashSet<Object>();
	private Metrics metrics;
	private int step; //Step counter
	Set<String> frontier = new HashSet<>();

	public RecursiveBestFirstSearch(EvaluationFunction ef) {
		this(ef, false);
	}

	/** Constructor which allows to enable the loop avoidance strategy. */
	public RecursiveBestFirstSearch(EvaluationFunction ef, boolean avoidLoops) {
		this(ef, avoidLoops, new NodeExpander());
	}
	
	public RecursiveBestFirstSearch(EvaluationFunction ef, boolean avoidLoops, NodeExpander nodeExpander) {
		evaluationFunction = ef;
		this.avoidLoops = avoidLoops;
		this.nodeExpander = nodeExpander;
		metrics = new Metrics();
	}
	

	// function RECURSIVE-BEST-FIRST-SEARCH(problem) returns a solution, or
	// failure
	@Override
	public List<Action> findActions(Problem p) {
		List<Action> actions = new ArrayList<Action>();
		explored.clear();

		clearInstrumentation();

		// RBFS(problem, MAKE-NODE(INITIAL-STATE[problem]), infinity)
		Node n = nodeExpander.createRootNode(p.getInitialState());
		step = SearchUtils.PRINT_STEPS ? 0 : 5;
		SearchResult sr = rbfs(p, n, evaluationFunction.f(n), INFINITY, 0);
		if (sr.hasSolution()) {
			Node s = sr.getSolutionNode();
			actions = SearchUtils.getSequenceOfActions(s);
			metrics.set(METRIC_PATH_COST, s.getPathCost());
		}

		// Empty List can indicate already at Goal
		// or unable to find valid set of actions
		return actions;
	}

	public EvaluationFunction getEvaluationFunction() {
		return evaluationFunction;
	}
	
	@Override
	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}
	
	/**
	 * Returns all the search metrics.
	 */
	@Override
	public Metrics getMetrics() {
		metrics.set(METRIC_NODES_EXPANDED, nodeExpander.getNumOfExpandCalls());
		return metrics;
	}

	/**
	 * Sets all metrics to zero.
	 */
	private void clearInstrumentation() {
		nodeExpander.resetCounter();
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_MAX_RECURSIVE_DEPTH, 0);
		metrics.set(METRIC_PATH_COST, 0.0);
	}

	//
	// PRIVATE METHODS
	//
	// function RBFS(problem, node, f_limit) returns a solution, or failure and
	// a new f-cost limit
	private SearchResult rbfs(Problem p, Node node, double node_f, double fLimit, int recursiveDepth) {
		updateMetrics(recursiveDepth);

		// if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
		if (SearchUtils.isGoalState(p, node))
			return getResult(null, node, fLimit);

		// successors <- []
		// for each action in problem.ACTION(node.STATE) do
		// add CHILD-NODE(problem, node, action) into successors
		List<Node> successors = expandNode(node, p);

		if (step < 5){
			List<String> tmpChildren = new ArrayList<>();
			for (Node successor : successors) {
				tmpChildren.add(successor.getState().toString());
				frontier.add(successor.getState().toString());
			}
			frontier.remove(node.getState().toString());
			System.out.println("Step " + (step+1) + " Current Node = " + node.getState());
			System.out.println("Step " + (step+1) + " Path = " + node.getPathCost());
			System.out.println("Step " + (step+1) + " Children of current node = " + tmpChildren);
//			System.out.println("Step " + (step+1) + " Frontier = " + frontier);
//			System.out.println("Step " + (step+1) + " Explored = " + explored);
			step++;
		}
		if(SearchUtils.PRINT_STATS && step < 5){
			System.out.println("Step " + (step) + "fLimit = " + (fLimit == INFINITY ? "INFINITY" : fLimit));
			System.out.println("Step " + (step) +"Current City = " + node.getState().toString());
		}

		// if successors is empty then return failure, infinity
		if (successors.isEmpty())
			return getResult(node, null, INFINITY);

		double[] f = new double[successors.size()];
		double[] pf = new double[successors.size()];
		double[] nf = new double[successors.size()];
		// for each s in successors do
		// update f with value from previous search, if any
		int size = successors.size();

		for (int s = 0; s < size; s++) {
			// s.f <- max(s.g + s.h, node.f)
			f[s] = Math.max(evaluationFunction.f(successors.get(s)), node_f);
			nf[s] = Math.max(evaluationFunction.f(successors.get(s)), 0);
			pf[s] = successors.get(s).getPathCost();
		}

		// repeat
		while (true) {
			// best <- the lowest f-value node in successors
			int bestIndex = getBestFValueIndex(f);
			// if best.f > f_limit then return failure, best.f
			if (f[bestIndex] > fLimit) {
				if (step < 5) {
					System.out.println("Step " + (step) + "f = " + Arrays.toString(nf));
					System.out.println("Step " + (step) + "p = " + Arrays.toString(pf));
					System.out.println("Step " + (step) +"Fail = " + successors.get(bestIndex).getState());
				}
				return getResult(node, null, f[bestIndex]);
			}
			// if best.f > f_limit then return failure, best.f
			int altIndex = getNextBestFValueIndex(f, bestIndex);
			// result, best.f <- RBFS(problem, best, min(f_limit, alternative))
			if (SearchUtils.PRINT_STATS && step < 5) {
				System.out.println("Step " + (step) + "f = " + Arrays.toString(nf));
				System.out.println("Step " + (step) + "p = " + Arrays.toString(pf));
				System.out.println("Step " + (step) +"Best = " + f[bestIndex]);
				System.out.println("Step " + (step) +"Alternative = " + f[altIndex]);
				System.out.println("Step " + (step) +"Next-city = " + successors.get(bestIndex).getState().toString());
				System.out.println("---------------------------------------------");
			}
			SearchResult sr = rbfs(p, successors.get(bestIndex), f[bestIndex], Math.min(fLimit, f[altIndex]),
					recursiveDepth + 1);
			f[bestIndex] = sr.getFCostLimit();
			// if result != failure then return result
			if (sr.hasSolution()) {
				return getResult(node, sr.getSolutionNode(), sr.getFCostLimit());
			}
		}
	}

	// the lowest f-value node
	private int getBestFValueIndex(double[] f) {
		int lidx = 0;
		Double lowestSoFar = INFINITY;

		for (int i = 0; i < f.length; i++) {
			if (f[i] < lowestSoFar) {
				lowestSoFar = f[i];
				lidx = i;
			}
		}

		return lidx;
	}

	// the second-lowest f-value
	private int getNextBestFValueIndex(double[] f, int bestIndex) {
		// Array may only contain 1 item (i.e. no alternative),
		// therefore default to bestIndex initially
		int lidx = bestIndex;
		Double lowestSoFar = INFINITY;

		for (int i = 0; i < f.length; i++) {
			if (i != bestIndex && f[i] < lowestSoFar) {
				lowestSoFar = f[i];
				lidx = i;
			}
		}

		return lidx;
	}

	private List<Node> expandNode(Node node, Problem problem) {
		List<Node> result = nodeExpander.expand(node, problem);
		if (avoidLoops) {
			explored.add(node.getState());
			for (Iterator<Node> ni = result.iterator(); ni.hasNext();)
				if (explored.contains(ni.next().getState())) {
					ni.remove();
				}
		}
		return result;
	}

	private SearchResult getResult(Node currNode, Node solutionNode, double fCostLimit) {
		if (avoidLoops && currNode != null)
			explored.remove(currNode.getState());
		return new SearchResult(solutionNode, fCostLimit);
	}

	/**
	 * Increases the maximum recursive depth if the specified depth is greater
	 * than the current maximum.
	 * 
	 * @param recursiveDepth
	 *            the depth of the current path
	 */
	private void updateMetrics(int recursiveDepth) {
		int maxRdepth = metrics.getInt(METRIC_MAX_RECURSIVE_DEPTH);
		if (recursiveDepth > maxRdepth) {
			metrics.set(METRIC_MAX_RECURSIVE_DEPTH, recursiveDepth);
		}
	}

	static class SearchResult {

		private Node solNode;
		private final double fCostLimit;

		public SearchResult(Node solutionNode, double fCostLimit) {
			this.solNode = solutionNode;
			this.fCostLimit = fCostLimit;
		}

		public boolean hasSolution() {
			return solNode != null;
		}

		public Node getSolutionNode() {
			return solNode;
		}

		public Double getFCostLimit() {
			return fCostLimit;
		}
	}
}
