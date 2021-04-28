package ai.hw1.mac;

import aima.core.search.framework.evalfunc.HeuristicFunction;

public class MACHeurisiticFunction implements HeuristicFunction {
    /**
     * Heuristic is taken as = (Number of people on the left/Boat capacity)
     * @param state
     * @return
     */
    @Override
    public double h(Object state) {
        MACEnvironment macEnvironment = (MACEnvironment) state;
        return (macEnvironment.getCannibalsLeft() + macEnvironment.getMissionariesLeft()) / MACEnvironment.BOAT_CAPACITY;
    }
}
