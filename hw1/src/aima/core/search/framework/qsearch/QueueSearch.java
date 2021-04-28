package aima.core.search.framework.qsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ai.hw1.usa.AStarNode;
import ai.hw1.usa.AStarStat;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.problem.Problem;
import aima.core.util.CancelableThread;

/**
 * Base class for queue-based search implementations, especially for
 * {@link TreeSearch}, {@link GraphSearch}, and {@link BidirectionalSearch}. It
 * provides a template method for controlling search execution and defines
 * primitive methods encapsulating frontier access. Tree search implementations
 * will implement frontier access straight-forward. Graph search implementations
 * will add node filtering mechanisms to avoid that nodes of already explored
 * states are selected for expansion.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */
public abstract class QueueSearch {
	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
	public static final String METRIC_QUEUE_SIZE = "queueSize";
	public static final String METRIC_MAX_QUEUE_SIZE = "maxQueueSize";
	public static final String METRIC_PATH_COST = "pathCost";

	final protected NodeExpander nodeExpander;
	protected Queue<Node> frontier;
	protected boolean earlyGoalTest = false;
	protected Metrics metrics = new Metrics();
	protected EvaluationFunction ef;

	protected QueueSearch(NodeExpander nodeExpander) {
		this.nodeExpander = nodeExpander;
	}

	protected QueueSearch(NodeExpander nodeExpander, EvaluationFunction ef) {
		this.nodeExpander = nodeExpander;
		this.ef = ef;
	}

	/**
	 * Receives a problem and a queue implementing the search strategy and
	 * computes a node referencing a goal state, if such a state was found.
	 * This template method provides a base for tree and graph search
	 * implementations. It can be customized by overriding some primitive
	 * operations, especially {@link #addToFrontier(Node)},
	 * {@link #removeFromFrontier()}, and {@link #isFrontierEmpty()}.
	 * 
	 * @param problem
	 *            the search problem
	 * @param frontier
	 *            the data structure for nodes that are waiting to be expanded
	 * 
	 * @return a node referencing a goal state, if the goal was found, otherwise null;
	 */
	public Node findNode(Problem problem, Queue<Node> frontier) {
		int i = SearchUtils.PRINT_STEPS ? 0 : 5;
		this.frontier = frontier;
		List<String> explored = new ArrayList<>();
		clearInstrumentation();
		// initialize the frontier using the initial state of the problem
		Node root = nodeExpander.createRootNode(problem.getInitialState());
		addToFrontier(root);
		if (earlyGoalTest && SearchUtils.isGoalState(problem, root))
			return getSolution(root);

		while (!isFrontierEmpty() && !CancelableThread.currIsCanceled()) {
			// choose a leaf node and remove it from the frontier
			Node nodeToExpand = removeFromFrontier();
			if (i < 5){
				System.out.println("Step " + (i+1) + " Current Node = " + nodeToExpand.getState());
			}
			explored.add(nodeToExpand.getState().toString());
			// Only need to check the nodeToExpand if have not already
			// checked before adding to the frontier
			if (!earlyGoalTest && SearchUtils.isGoalState(problem, nodeToExpand))
				// if the node contains a goal state then return the
				// corresponding solution
				return getSolution(nodeToExpand);

			// expand the chosen node, adding the resulting nodes to the
			// frontier
			List<String> tmpChildren = new ArrayList<>();
			List<Node> successors = nodeExpander.expand(nodeToExpand, problem);
			for (Node successor : successors) {
				addToFrontier(successor);
				tmpChildren.add(successor.getState().toString());
				if (earlyGoalTest && SearchUtils.isGoalState(problem, successor))
					return getSolution(successor);
			}
			if (i < 5){
				List<String> tmpFrontier = new ArrayList<>();
				frontier.forEach(node -> tmpFrontier.add(node.getState().toString()));
				System.out.println("Step " + (i+1) + " Frontier = " + tmpFrontier);
				System.out.println("Step " + (i+1) + " Explored = " + explored);
				System.out.println("Step " + (i+1) + " Children of current node = " + tmpChildren);
				i++;
			}
			if (SearchUtils.PRINT_STATS){
				List<AStarNode> tmpFrontier = new ArrayList<>();
				AStarNode aStarNode = new AStarNode(nodeToExpand, ef.f(nodeToExpand));
				frontier.forEach(node -> tmpFrontier.add(new AStarNode(node, ef.f(node))));
				List<AStarNode> children = new ArrayList<>();
				successors.forEach(s -> children.add(new AStarNode(s, ef.f(s))));
				System.out.println(new AStarStat(aStarNode, explored, tmpFrontier));
			}
		}
		// if the frontier is empty then return failure
		return null;
	}

	/**
	 * Primitive operation which inserts the node at the tail of the frontier.
	 */
	protected abstract void addToFrontier(Node node);

	/**
	 * Primitive operation which removes and returns the node at the head of the
	 * frontier.
	 * 
	 * @return the node at the head of the frontier.
	 */
	protected abstract Node removeFromFrontier();

	/**
	 * Primitive operation which checks whether the frontier contains not yet
	 * expanded nodes.
	 */
	protected abstract boolean isFrontierEmpty();

	/**
	 * Enables optimization for FIFO queue based search, especially breadth
	 * first search.
	 * 
	 * @param state
	 */
	public void setEarlyGoalTest(boolean state) {
		earlyGoalTest = state;
	}

	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}

	/**
	 * Returns all the search metrics.
	 */
	public Metrics getMetrics() {
		metrics.set(METRIC_NODES_EXPANDED, nodeExpander.getNumOfExpandCalls());
		return metrics;
	}

	/**
	 * Sets all metrics to zero.
	 */
	public void clearInstrumentation() {
		nodeExpander.resetCounter();
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_QUEUE_SIZE, 0);
		metrics.set(METRIC_MAX_QUEUE_SIZE, 0);
		metrics.set(METRIC_PATH_COST, 0);
	}

	protected void updateMetrics(int queueSize) {
		metrics.set(METRIC_QUEUE_SIZE, queueSize);
		int maxQSize = metrics.getInt(METRIC_MAX_QUEUE_SIZE);
		if (queueSize > maxQSize) {
			metrics.set(METRIC_MAX_QUEUE_SIZE, queueSize);
		}
	}

	private Node getSolution(Node node) {
		metrics.set(METRIC_PATH_COST, node.getPathCost());
		return node;
	}
}