Name: Shankaranarayanan Kallidaikuruchi Ramakrishnan
Email: shankar.ramakrishnan@utdallas.edu

Homework 2 - CS 6364 - Artificial Intelligence

Problems Solved: 1A, 1B, 1-Extra credit(C)

Programming Language Used: Java

Supporting files from AIMA-Java:
- None

Code description:

To execute the code, extract and open the project in IDE (Intellij recommended) and run main methods in FourPlayerGame class

Set JVM options for increasing stack memory by using the JVM option -Xss48M

Example to run all problems: java -Xss48M FourPlayerGame

Problem 1A - Multi-player Game - Game Tree:

- FourPlayerBoard.java - Contains class and methods for maintaining the state of the game
- FourPlayerGame.java - Contains main method that is used to run method to produce game tree

The initial state is formulated in the constructor and explore method is used to generate the game tree.

To run this problem individually, pass 1A as program argument. Example: java -Xss48M FourPlayerGame 1A

Problem 1B - Multi-player Game - Minimax:

- Same files as Problem 1A

Minimax method is used to recursively compute max utility of each player in each state.

To run this problem individually, pass 1B as program argument. Example: java -Xss48M FourPlayerGame 1B

Problem 1 - Extra credit - Multi-player Game - Game Tree with Jumps:

- Same files as Problem 1A

FourPlayerGame.canJump member is set to true and explore method is used to generate the game tree with jumps.

To run this problem individually, pass 1C as program argument. Example: java -Xss48M FourPlayerGame 1C 



