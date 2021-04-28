package ai.hw1.mac;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;

import java.util.HashSet;
import java.util.Set;

import static ai.hw1.mac.MACEnvironment.*;

public class MACActionFunction implements ActionsFunction {

    /**
     * Returns set of actions possible given a particular state
     * @param s - a particular state.
     * @return Set<Action>
     */
    @Override
    public Set<Action> actions(Object s) {
        MACEnvironment macState = (MACEnvironment) s;
        int missionariesLeft = macState.getMissionariesLeft();
        int cannibalsLeft = macState.getCannibalsLeft();
        int boatLeft = macState.getBoatLeft();
        Set<Action> actions = new HashSet<>();
        if (boatLeft == 1) {
            getBoatLeftActions(missionariesLeft, cannibalsLeft, actions);
        } else if (boatLeft == 0) {
            getBoatRightActions(missionariesLeft, cannibalsLeft, actions);
        }
        return actions;
    }

    /**
     * Results all actions possible when boat in on the right
     * @param missionariesLeft - Missionaries on the left
     * @param cannibalsLeft - Cannibals on the left
     * @param actions - Set of actions
     */
    private void getBoatRightActions(int missionariesLeft, int cannibalsLeft, Set<Action> actions) {
        if (missionariesLeft == 3) {
            if (cannibalsLeft == 2) {
                actions.add(C);
            } else {
                actions.add(CC);
                actions.add(C);
            }
        } else if (missionariesLeft == 2) {
            if (cannibalsLeft == 2) {
                actions.add(MC);
                actions.add(C);
                actions.add(M);
            }
        } else if (missionariesLeft == 1) {
            if (cannibalsLeft == 1) {
                actions.add(CC);
                actions.add(M);
                actions.add(C);
                actions.add(MC);
                actions.add(MM);
            }
        } else {
            if (cannibalsLeft == 3) {
                actions.add(MM);
                actions.add(M);
            } else if (cannibalsLeft == 2) {
                actions.add(MC);
                actions.add(MM);
                actions.add(M);
                actions.add(C);
            } else {
                actions.add(M);
                actions.add(CC);
                actions.add(MC);
                actions.add(MM);
            }
        }
    }

    /**
     * Results all actions possible when boat in on the left
     * @param missionariesLeft - Missionaries on the left
     * @param cannibalsLeft - Cannibals on the left
     * @param actions - Set of actions
     */
    private void getBoatLeftActions(int missionariesLeft, int cannibalsLeft, Set<Action> actions) {
        if (missionariesLeft == 3) {
            if (cannibalsLeft >= 2) {
                actions.add(M);
                actions.add(CC);
                actions.add(C);
                actions.add(MC);
                actions.add(MM);
            } else if (cannibalsLeft == 1) {
                actions.add(MM);
                actions.add(M);
                actions.add(C);
                actions.add(MC);
            } else {
                actions.add(MM);
                actions.add(M);
            }
        } else if (missionariesLeft == 2) {
            if (cannibalsLeft == 2) {
                actions.add(MM);
                actions.add(CC);
                actions.add(M);
                actions.add(C);
                actions.add(MC);
            }
        } else if (missionariesLeft == 1) {
            if (cannibalsLeft == 1) {
                actions.add(M);
                actions.add(C);
                actions.add(MC);
            }
        } else {
            if (cannibalsLeft >= 2) {
                actions.add(CC);
            }
            actions.add(C);
        }
    }
}
