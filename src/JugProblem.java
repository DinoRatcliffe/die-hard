import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * JugProblem
 * 
 * A class used to represent a Three Jug problem. Provides a method to 
 * attempt to solve the problem using breadth first search.
 * 
 * @author DinoRatcliffe
 *
 */
public class JugProblem {
	
	//allows incorporation of A* and greedy search
	//set to true in order to use the evaluation function for each node
	private static final boolean usingEvaluation = false;
	
	//initial state and goal state of the given problem.
	private ThreeJugState initialState, goalState;
	
	//counters for nodes expanded and not expanded but generated
	private int expandedNodes, unExpandedNodes;
	
	//solution node
	private JugNode solution;
	
	//flags to indicate if the problem has been attempted/solved
	private boolean solved, attempted;

	/**
	 * Constructor that takes in an int array defining the JugProblem to be solved
	 * 
	 * States should be set as:
	 * [0][1][2] Capacity of the three jugs
	 * [3][4][5] Initial content of the three jugs
	 * [6][7][8] Goal content of the three jugs
	 * 
	 * @param states int[] an array of ints defining the JugProblem
	 * 				
	 */
	public JugProblem(int[] states){
		initialState = new ThreeJugState(states[0], states[1], states[2], states[3], states[4], states[5]);
		goalState = new ThreeJugState(states[6], states[7], states[8]);
		
		ArrayList<JugNode> unExpNodes = new ArrayList<JugNode>();
		unExpNodes.add(new JugNode(initialState));
		expandedNodes = unExpandedNodes = 0;
		
	}
	
	/**
	 * Attempts to solve the JugProblem using a form of tree search.
	 * Will not run again if it has already been attempted.
	 */
	public void solveProblem(){
		if (attempted) return;
		
		//init of variables needed inside loop
		ArrayList<JugNode> unExpNodes = new ArrayList<JugNode>();
		unExpNodes.add(new JugNode(initialState));
		
		Set<ThreeJugState> uniqueState = new HashSet<ThreeJugState>();
		uniqueState.add(initialState);
		
		ThreeJugState newState;
		JugNode currentNode;
		ThreeJugState currentState;
		
		expandedNodes = 0;
		attempted = true;

		//loop to perform search
		while (unExpNodes.size()>0){
			expandedNodes++;
			
			//list needs to be resorted if this search is using an evaluation function
			if(usingEvaluation) Collections.sort(unExpNodes);
			
			currentNode = unExpNodes.remove(0);
			currentState = currentNode.getState();
			
			if (currentState.equals(goalState)){
				unExpandedNodes = unExpNodes.size();
				solved = true;
				solution = currentNode;
				return;
			}
			
			//generates child nodes. 
			//adds them to unexpanded nodes if they are a new unique state.
			for (int i = 0; i<3; i++){
				for (int j = 0; j<3; j++){
					if (i != j){
						newState = currentState.decant(i, j);	
										
						if (!uniqueState.contains(newState)){
							unExpNodes.add(new JugNode(newState, i, j, currentNode.getPath()));
							uniqueState.add(newState);
						}
					}
				}
			}
			
			
		}

	}
	
	/**
	 * creates a string representation of the object in the form of:
	 * 
	 * Next Problem:
	 * Capacities        8 5 3
	 * Initial Contents  8 5 3
	 * Goal Contents     4 4 0
	 * 
	 * Following text will be added based on the state of the problem:
	 * 
	 * If solved the toString method is called on the solution node as well
	 * as other details about the success are displayed.
	 * 
	 * If no solution was found or the problem hasn't been attempted then appropriate
	 * messages will be displayed.
	 * 
	 */
	public String toString(){
		String output = "";
		output += String.format("Capacities\t   %d %d %d\n", initialState.getCapacity(0), initialState.getCapacity(1), initialState.getCapacity(2));
		output += String.format("Initial Contents   %d %d %d\n", initialState.getContent(0), initialState.getContent(1), initialState.getContent(2));
		output += String.format("Goal Contnents\t   %d %d %d\n", goalState.getContent(0), goalState.getContent(1), goalState.getContent(2));
		
		if (solved){
			output += String.format("Solution requires %d moves\n", solution.getMoveCount());
			output += String.format("start from %d %d %d\n", initialState.getContent(0), initialState.getContent(1), initialState.getContent(2));
			
			//uses overridden toString method
			output += solution;
			
			output += String.format("Number of nodes expanded: %d\n", expandedNodes);
			output += String.format("Number of unexpanded nodes: %d\n", unExpandedNodes);
		} else if (attempted){
			output += "No solution was found\n";
		} else {
			output += "Problem has not been attempted\n";
		}
		return output;
	}
	
	/**
	 * Setters and getters
	 */
	public boolean attempted(){
		return attempted;
	}
	
	public boolean isSolved(){
		return solved;
	}
}
