Name: Shankaranarayanan Kallidaikuruchi Ramakrishnan
Email: shankar.ramakrishnan@utdallas.edu

Homework 1 - CS 6364 - Artificial Intelligence

Problems Solved: 2, 3

Programming Language Used: Java

Supporting files from AIMA-Java:
- aima.core.agent.Action.java
- aima.core.search.framework.problem.ActionsFunction.java
- aima.core.agent.impl.DynamicAction.java
- aima.core.search.framework.problem.GoalTest.java
- aima.core.search.framework.evalfunc.HeuristicFunction.java
- aima.core.search.framework.problem.ResultFunction.java
- aima.core.search.framework.SearchAgent.java
- aima.core.search.framework.SearchForActions.java
- aima.core.search.framework.SearchUtils.java
- aima.core.search.framework.problem.Problem.java
- aima.core.search.framework.qsearch.GraphSearch.java
- aima.core.search.informed.AStarEvaluationFunction.java
- aima.core.search.informed.AStarSearch.java
- aima.core.search.informed.GreedyBestFirstSearch.java
- aima.core.search.informed.RecursiveBestFirstSearch.java
- aima.core.search.uninformed.IterativeDeepeningSearch.java
- aima.core.search.uninformed.DepthLimitedSearch.java
- aima.core.search.uninformed.UniformCostSearch.java

Code description:

To execute the code, extract and open the project in IDE (Intellij recommended) and run main methods in MissionariesAndCannibals/USASearch class (or)
Navigate to the extracted hw1 folder in the terminal (the directory containing the src folder)
Run the commands
`find -name *.java > sources.txt`
`mkdir build`
`javac -d build @sources.txt`
`cd build/`
The java code should be compiled inside the build directory
To run the code for Problem 2 - Missionaries and cannibals, use the command
`java ai.hw1.mac.MissionariesAndCannibals`
To run the code for Problem 3 - Road Map of USA, use the command
`java ai.hw1.usa.USASearch`


Problem 2 - Missionaries and Cannibals:

- MACActionFunction.java - Returns possible actions for particular state
- MACEnvironment.java - Simulates Missionaries and Cannibals problem environment
- MACGoalTest.java - Checks for Goal state with current state
- MACHeurisiticFunction.java - Returns heuristic of a particular state
- MACResultFunction.java - Returns new state after performing action
- MissionariesAndCannibals.java - Contains main method that is used to run various search strategies

Code can be run by executing the main method in MissionariesAndCannibals class. The initial state, goal state and problem is formulated in the constructor and AIMA search implementations are used to find the solution path.

Problem 3 - Road map of USA:

- AStarNode.java - POJO for printing result
- AStarStat.java - POJO for printing result
- ConsistentHeurisitcChecker.java - Checks for consistent heuristics
- RoadMapOfUSA.java - Representation of USA graph
- USASearch.java - Contains main method that is used to run given search strategies

Code can be run by executing the main method in USASearch class. The map and problem is formulated in the constructor and AIMA search implementations are used to find the solution path. 



