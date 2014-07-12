import java.util.LinkedList;

/**
 * JugNode
 * 
 * A class used to represent an node in a tree search of the
 * Three Jug Problem.
 * 
 * Stores state as a ThreeJugState object along with information on moves leading
 * up to that state.
 * 
 * Also contains functionality to implement an evaluation function.
 * 
 * @author DinoRatcliffe
 *
 */
public class JugNode implements Comparable<JugNode> {
	//problem state at this node
	private ThreeJugState state;
	
	//how many moves were made leading up to this state.
	private int moveCount;
	
	//information on the path leading up to this state from the root node
	//gets larger the further you go down the tree.
	private LinkedList<int[]> intPath;
	
	//value the evaluation function gave this node
	private int evaluation;
	
	/**
	 * Constructor sets state of node along with calculating an evaluation function.
	 * 
	 * @param state ThreeJugState of the problem at this node
	 * @param moveCount int How many moves it took to reach this node
	 * @param from int value of jug that the liquid was poured from in reaching this node
	 * @param to int value of jug that the liquid was poured to in reaching this node
	 * @param path LinkedList<int[]> a linked list of int arrays of length 5 in the format
	 * 								 [0] jug the liquid came from to get this state.
	 * 								 [1] jug the liquid went to get this state
	 * 								 [2][3][4] state of jugs 0, 1 and 2 respectively
	 */
	public JugNode(ThreeJugState state, int from, int to, LinkedList<int[]> path){
		this.state = state;
		this.intPath = new LinkedList<int[]>(path);
		this.moveCount = intPath.size() + 1;
		
		//TODO possible to add evaluation function, currently set to produce breadth first search.
		//I can't think of a admissible heuristic for this problem.
		evaluation = moveCount;
		intPath.addLast(new int[] {from, to, state.getContent(0), state.getContent(1), state.getContent(2)});
	}
	
	/**
	 * Constructor to be used when creating the root node
	 * 
	 * @param state ThreeJugState the initial state of the problem
	 */
	public JugNode(ThreeJugState state){
		this.state = state;
		this.intPath = new LinkedList<int[]>();
		
		//TODO will need function if one is found
		evaluation = 0;
	}

	@Override
	public int compareTo(JugNode o) {
		int result = evaluation - o.getEvaluation();
		
		if (result == 0 && o.getState().equals(this.getState())){
			result = 1;
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String output = "";
		// adds all steps stored in intPath and creates a human readable string
		for (int[] i : intPath){
			output += String.format("pour from %d to %d to give %d %d %d\n", i[0], i[1], i[2], i[3], i[4]);
		}
		return output;
	}

	/**
	 * getters and setters
	 */
	public ThreeJugState getState(){
		return state;
	}
	
	public int getMoveCount(){
		return moveCount;
	}
	public LinkedList<int[]> getPath(){
		return intPath;
	}
	public int getEvaluation(){
		return evaluation;
	}
}
