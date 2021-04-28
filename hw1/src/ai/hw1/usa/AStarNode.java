package ai.hw1.usa;

import aima.core.search.framework.Node;

/**
 * Object for representing result in expected format
 */
public class AStarNode {
    String currentNode;
    double evalCurrent;

    public AStarNode(Node currentNode, double evalCurrent) {
        this.currentNode = currentNode.getState().toString();
        this.evalCurrent = evalCurrent;
    }

    @Override
    public String toString() {
        return "{" + currentNode + ", f=" + evalCurrent +'}';
    }
}