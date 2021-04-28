package ai.hw1.usa;

import java.util.List;

/**
 * Object for representing result in expected format
 */
public class AStarStat {
    AStarNode node;
    List<String> explored;
    List<AStarNode> frontier;

    public AStarStat(AStarNode node, List<String> explored, List<AStarNode> frontier) {
        this.node = node;
        this.explored = explored;
        this.frontier = frontier;
    }

    @Override
    public String toString() {
        return "[" + node +
                ", Explored=" + explored +
                ", Frontier=" + frontier +
                ']';
    }
}


