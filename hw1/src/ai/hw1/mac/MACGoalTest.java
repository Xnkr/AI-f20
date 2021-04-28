package ai.hw1.mac;

import aima.core.search.framework.problem.GoalTest;

public class MACGoalTest implements GoalTest {

    MACEnvironment goalState;

    MACGoalTest(MACEnvironment goalState){
        this.goalState = goalState;
    }

    @Override
    public boolean isGoalState(Object state) {
        MACEnvironment macState = (MACEnvironment) state;
        return macState.equals(this.goalState);
    }
}
