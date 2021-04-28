package ai.hw1.mac;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

public class MACEnvironment {

    private int missionariesLeft;
    private int cannibalsLeft;
    private int boatLeft;

    /**
     * Initialize all possible actions
     */
    public static final Action M = new DynamicAction("M");
    public static final Action MM = new DynamicAction("MM");
    public static final Action C = new DynamicAction("C");
    public static final Action CC = new DynamicAction("CC");
    public static final Action MC = new DynamicAction("MC");

    public static final double BOAT_CAPACITY = 2.0;

    /**
     * Represents the problem state
     * @param missionariesLeft - Number of missionaries on the left side
     * @param cannibalsLeft - Number of cannibals on the left side
     * @param boatLeft - 1 if boat is on the left 0 if boat is on the right
     */
    public MACEnvironment(int missionariesLeft, int cannibalsLeft, int boatLeft) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boatLeft = boatLeft;
    }

    public int getCannibalsLeft() {
        return this.cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return this.missionariesLeft;
    }

    public int getBoatLeft() {
        return this.boatLeft;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof MACEnvironment) {
                MACEnvironment aState = (MACEnvironment) obj;
                return this.getMissionariesLeft() == aState.getMissionariesLeft()
                        && this.getCannibalsLeft() == aState.getCannibalsLeft()
                        && this.getBoatLeft() == aState.getBoatLeft();
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "MACState(" + missionariesLeft +
                ", " + cannibalsLeft +
                ", " + boatLeft +
                ')';
    }
}
