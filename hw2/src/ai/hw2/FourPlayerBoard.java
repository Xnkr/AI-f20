package ai.hw2;

import java.util.*;

import static ai.hw2.FourPlayerBoard.*;

/**
 * Class to represent player coordinates
 */
class Coordinate {
    int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x +
                "," + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() &&
                getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    /**
     * Move the player based on the given direction
     * @param direction - Direction to move the player
     */
    public void move(String direction) {
        if (LEFT.equals(direction)) {
            setX(getX() - 1);
        } else if (RIGHT.equals(direction)) {
            setX(getX() + 1);
        } else if (UP.equals(direction)) {
            setY(getY() - 1);
        } else if (DOWN.equals(direction)) {
            setY(getY() + 1);
        }
    }

    /**
     * Checks is player is within the board
     * @return boolean
     */
    public boolean isOutOfBounds() {
        return getX() == 0 || getY() == 0 || getX() == 5 || getY() == 5;
    }
}

/**
 * Class to represent utility values in the game
 */
class Utility {

    private Map<Integer, Integer> utilityMap;
    public static Utility ZERO_UTIL = new Utility(new int[]{0,0,0,0});

    /**
     * Initialize utility value of each players
     * @param utilities - Array of utility values
     */
    Utility(int[] utilities) {
        utilityMap = new HashMap<>();
        for (int i = 0; i < utilities.length; i++){
            utilityMap.put(i+1, utilities[i]);
        }
    }

    public int getUtilityOfPlayer(int player){
        return utilityMap.get(player);
    }

    @Override
    public String toString() {
        return utilityMap.values().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utility utility = (Utility) o;
        return Objects.equals(utilityMap, utility.utilityMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilityMap);
    }
}

/**
 * Class to represent the game board
 */
public class FourPlayerBoard {

    private HashMap<Integer, Coordinate> state;

    /**
     * Define goal states
     */
    Coordinate player1Goal = new Coordinate(4,4);
    Coordinate player2Goal = new Coordinate(1, 4);
    Coordinate player3Goal = new Coordinate(1,1);
    Coordinate player4Goal = new Coordinate(4, 1);

    /**
     * Define possible actions
     */
    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";
    public static final String UP = "Up";
    public static final String DOWN = "Down";
    public static final String[] DIRECTIONS = new String[]{LEFT, UP, DOWN, RIGHT};

    public FourPlayerBoard(Map<Integer, Coordinate> state){
        this.state = (HashMap<Integer, Coordinate>) state;
    }

    public FourPlayerBoard(int[][] initialState){
        this.state = new HashMap<>();
        for (int i = 0; i < initialState.length; i++) {
            int[] point = initialState[i];
            this.state.put(i+1, new Coordinate(point[0], point[1]));
        }
    }

    /**
     * Move given player in the given direction
     * @param player - Which player to move
     * @param action - Which direction to move the player
     * @param jump - Whether to execute jump movements
     * @return FourPlayerBoard with updated state
     */
    public FourPlayerBoard movePlayer(int player, String action, boolean jump){
        Map<Integer, Coordinate> stateCopy = new HashMap<>(getState());
        Coordinate current = new Coordinate(stateCopy.get(player));
        current.move(action);
        if (jump)
            current.move(action);
        stateCopy.put(player, current);
        return new FourPlayerBoard(stateCopy);
    }

    /**
     * Checks if a player can move in the given direction
     * @param player - Player to move
     * @param direction - Which direction to move the player
     * @param jump - Whether to execute jump actions
     * @return int - 0 if move is not possible, 1 if move is possible, 2 if jump move is possible
     */
    public int canMove(int player, String direction, boolean jump){
        Coordinate current = new Coordinate(this.state.get(player));
        current.move(direction);
        int i = 0;
        while (i < 2) {
            if (current.isOutOfBounds()) {
                return 0;
            }
            boolean collision = false;
            for (Integer key: this.state.keySet()) {
                if (key == player) continue;
                Coordinate other = this.state.get(key);
                if (other.equals(current)){
                    if (jump && i == 0) {
                        collision = true;
                        current.move(direction);
                        break;
                    } else {
                        return 0;
                    }
                }
            }
            i++;
            if (!jump || !collision)
                break;
        }
        return i;
    }

    /**
     * Checks current state with goal states
     * @return int - Winning player
     */
    public int getWinner(){
        if(this.state.get(1).equals(player1Goal)){
            return 1;
        }
        if (this.state.get(2).equals(player2Goal)) {
            return 2;
        }
        if (this.state.get(3).equals(player3Goal)) {
            return 3;
        }
        if (this.state.get(4).equals(player4Goal)) {
            return 4;
        }
        return 0;
    }

    public Map<Integer, Coordinate> getState(){
        return this.state;
    }

    @Override
    public String toString() {
        return state.values().toString();
    }
}
