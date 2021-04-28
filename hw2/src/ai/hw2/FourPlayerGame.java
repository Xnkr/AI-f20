package ai.hw2;

import java.util.*;

public class FourPlayerGame {

    FourPlayerBoard root;
    Set<String> explored = new HashSet<>();
    int maxDepth = 0;
    int fastestWin = Integer.MAX_VALUE;
    String fastestWinner = "";
    int numRepeated = 0;
    boolean canJump = false;

    FourPlayerGame() {
        int[][] initialState = new int[][]{
                {1, 1},
                {4, 1},
                {4, 4},
                {1, 4}
        };
        root = new FourPlayerBoard(initialState);
    }

    private Set<String> getExplored() {
        return explored;
    }

    public FourPlayerBoard getRoot() {
        return root;
    }

    /**
     * Recursive method to explore and generate the game tree
     * @param node - Current game node
     * @param parent - Father of the current node
     * @param player - Current player
     * @param actionPerformed - Action performed by father
     * @param depth - Current depth of the game tree
     */
    public void gameTree(FourPlayerBoard node, FourPlayerBoard parent, int player, String actionPerformed, int depth) {
//        String printFmt = "[Current player = %s| Father node = %s| Action= %s| Current game " +
//                "node = %s| %s | %d]";
        String printFmt = "[Current player = %s| Father node = %s| Action= %s| Current game " +
                "node = %s| %s ]";
        explored.add(node.toString());
        int winner = node.getWinner();
        if (winner != 0) {
            if (fastestWin > depth) {
                fastestWin = depth;
                fastestWinner = "P" + winner;
            }
            System.out.println(String.format(printFmt, "P" + player, parent, actionPerformed, node, "WINS[P" + winner + "]", depth));
            return;
        }
        System.out.println(String.format(printFmt, "P" + player, parent, actionPerformed, node, null, depth));
        String[] directions = FourPlayerBoard.DIRECTIONS;
        for (String direction : directions) {
            int canMove = node.canMove(player, direction, canJump);
            if (canMove > 0) {
                String actionPerformedTmp = canMove == 2 ? "Jump " : "";
                actionPerformedTmp += direction;
                FourPlayerBoard next = node.movePlayer(player, direction, canMove == 2);
                if (!explored.contains(next.toString())) {
                    gameTree(next, node, player + 1 == 5 ? 1 : player + 1, actionPerformedTmp, depth + 1);
                } else {
                    numRepeated++;
                    System.out.println(String.format(printFmt, "P" + player, parent, actionPerformedTmp, next, "REPEATED", depth));
                }
            }
        }
        maxDepth = Math.max(maxDepth, depth);
    }

    /**
     * Recursive method to explore and compute minimax at each state
     * @param node - Current game node
     * @param parent - Father of the current node
     * @param player - Current player
     * @param actionPerformed - Action performed by father
     * @param depth - Current depth of the game tree
     * @return Utility - Max utility of current player
     */
    public Utility minimax(FourPlayerBoard node, FourPlayerBoard parent, int player, String actionPerformed, int depth) {
//        String printFmt = "[Current player = %s| Father node = %s| Action= %s| Current game " +
//                "node = %s| %s |  MINIMAX = %s | %d ]";
        String printFmt = "[Current player = %s| Father node = %s| Action= %s| Current game " +
                "node = %s| %s |  MINIMAX = %s ]";
        explored.add(node.toString());
        int winner = node.getWinner();
        if (winner != 0) {
            Utility winnerUtil = getWinnerUtility(winner);
            System.out.println(String.format(printFmt, "P" + player, parent, actionPerformed, node, "WINS[P" + winner + "]", winnerUtil, depth));
            return winnerUtil;
        }
        List<Utility> childUtilities = new ArrayList<>();
        boolean isMoved = false;
        for (String direction : FourPlayerBoard.DIRECTIONS) {
            int canMove = node.canMove(player, direction, canJump);
            if (canMove > 0) {
                isMoved = true;
                FourPlayerBoard next = node.movePlayer(player, direction, canMove == 2);
                if (!explored.contains(next.toString())) {
                    childUtilities.add(minimax(next, node, player + 1 == 5 ? 1 : player + 1, direction, depth + 1));
                }
            }
        }
        if (!isMoved) {
            return Utility.ZERO_UTIL;
        }
        Utility levelUtil = getMaxUtilityForPlayer(childUtilities, player);
        System.out.println(String.format(printFmt, "P" + player, parent, actionPerformed, node, null, levelUtil, depth));
        return levelUtil;
    }

    /**
     * Return utility values based on the winner
     * @param winner - Winning player
     * @return Utility value of the terminal state
     */
    private Utility getWinnerUtility(int winner) {
        if (winner == 1) {
            return new Utility(new int[]{200, 10, 30, 10});
        } else if (winner == 2) {
            return new Utility(new int[]{100, 300, 150, 200});
        } else if (winner == 3) {
            return new Utility(new int[]{150, 200, 400, 300});
        } else if (winner == 4) {
            return new Utility(new int[]{220, 330, 440, 500});
        }
        return Utility.ZERO_UTIL;
    }

    /**
     * Selects Max utility from successors
     * @param childUtilities - List of Utilities of successors
     * @param player - Player to choose max
     * @return Maximum Utility of the given player
     */
    private Utility getMaxUtilityForPlayer(List<Utility> childUtilities, int player) {
        Utility result = null;
        int maxUtility = -1;
        for (Utility child : childUtilities) {
            if (child == null) continue;
            int playerUtil = child.getUtilityOfPlayer(player);
            if (playerUtil > maxUtility) {
                result = child;
                maxUtility = playerUtil;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FourPlayerGame game = new FourPlayerGame();
        if (args.length > 0) {
            String problem = args[0];
            if (problem.equalsIgnoreCase("1A")) {
                System.out.println("Problem 1A - Generate Game tree");
                game.gameTree(game.getRoot(), null, 1, null, 1);
            } else if (problem.equalsIgnoreCase("1B")) {
                System.out.println("Problem 1B - Compute Minimax");
                Utility res = game.minimax(game.getRoot(), null, 1, null, 1);
                System.out.println("Final Minimax " + res);
            } else if (problem.equalsIgnoreCase("1C")) {
                System.out.println("Problem 1 - Extra Credit - Generate Game tree with jumps");
                game.canJump = true;
                game.gameTree(game.getRoot(), null, 1, null, 1);
            }
        } else {
            System.out.println("Problem 1A - Generate Game tree");
            game.gameTree(game.getRoot(), null, 1, null, 1);
            game.getExplored().clear();
            System.out.println("Problem 1B - Compute Minimax");
            Utility res = game.minimax(game.getRoot(), null, 1, null, 1);
            System.out.println("Final Minimax " + res);
            System.out.println("Problem 1 - Extra Credit - Generate Game tree with jumps");
            game.canJump = true;
            game.getExplored().clear();
            game.gameTree(game.getRoot(), null, 1, null, 1);
        }
    }
}
