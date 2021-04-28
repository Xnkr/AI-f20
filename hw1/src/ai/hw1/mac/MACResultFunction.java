package ai.hw1.mac;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;

import static ai.hw1.mac.MACEnvironment.*;

public class MACResultFunction implements ResultFunction {
    /**
     * Returns new state based on the action performed on the given state
     * @param s
     *            a particular state.
     * @param a
     *            an action to be performed in state s.
     * @return MACEnvironment
     */
    @Override
    public Object result(Object s, Action a) {
        MACEnvironment macState = (MACEnvironment) s;
        int missionariesLeft = macState.getMissionariesLeft();
        int cannibalsLeft = macState.getCannibalsLeft();
        int boatLeft = macState.getBoatLeft();
        if (boatLeft == 1) {
            boatLeft = 0;
            if (a.equals(M)) {
                missionariesLeft -= 1;
            } else if (a.equals(MM)) {
                missionariesLeft -= 2;
            } else if (a.equals(MC)) {
                missionariesLeft -= 1;
                cannibalsLeft -= 1;
            } else if (a.equals(C)) {
                cannibalsLeft -= 1;
            } else if (a.equals(CC)) {
                cannibalsLeft -= 2;
            }
        } else {
            boatLeft = 1;
            if (a.equals(M)) {
                missionariesLeft += 1;
            } else if (a.equals(MM)) {
                missionariesLeft += 2;
            } else if (a.equals(MC)) {
                missionariesLeft += 1;
                cannibalsLeft += 1;
            } else if (a.equals(C)) {
                cannibalsLeft += 1;
            } else if (a.equals(CC)) {
                cannibalsLeft += 2;
            }
        }
        return new MACEnvironment(missionariesLeft, cannibalsLeft, boatLeft);
    }
}
