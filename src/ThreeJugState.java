import java.util.Arrays;

/**
 * ThreeJugState
 *
 *  This class is used to represent states that arise in the solution of three jug problems
 *  such as those discussed in the CE213 lectures and classes.
 *
 *  The three jugs are indexed 0, 1 and 2.
 *  The class has a static array variable 'capacity' that holds the capacities of the jugs.
 *  Each instance has an array variable 'content' that holds the contents of the jugs.
 *
 *  All operators are implemented by a single method 'decant' which takes the indices of the
 *  source and destination jugs as arguments.
 *
 *  'cloneState' is a method that returns a new state identical to the current state.
 *
 *  'equals' is a method that returns true if and only if the contents and capacities of
 *  the current state and the state specified as an argument are identical.
 *
 *  Created by P. D. Scott. October 2012
 *  
 *  Edited by Dino Ratcliffe. November 2012
 *  added hashCode() to make it compatible with hash set.
 */

public class ThreeJugState{

    private static int[] capacity = new int[3]; // Holds the capacities of each jug
    private int[] content = new int[3]; // Hold the current contents of each jug


    /** Constructors ---------------------------------------------------------------------- */

    /**
     * Constructor that creates a new state leaving the capacities of each jug unchanged.
     * x, y, and z are the contents of each jug
     */
    public ThreeJugState(int x, int y, int z) {
        content[0] = x;
        content[1] = y;
        content[2] = z;
    }

    /**
     * Constructor that creates a new state and resets the capacities.
     * (Note that default values of capacities are zero so this constructor should be used
     * when a new problem is begun).
     * x, y, and z are the contents of each jug
     */
    public ThreeJugState(int capX, int capY, int capZ, int x, int y, int z) {
        this(x, y, z);
        capacity[0] = capX;
        capacity[1] = capY;
        capacity[2] = capZ;
    }


    /**
     * Setting and Getting --------------------------------------------------------------------
     */

    public int getContent(int j) { return content[j]; }

    public void setContent(int j, int vol) { content[j] = vol; }

    public int getCapacity(int j) { return capacity[j]; }

    public void setCapacity(int j, int vol) { capacity[j] = vol; }


    /**
     *  equals returns true if and only if the contents of the current state
     *  and the state specified as an argument are identical. (NB. Because, capacity
     *  is a static variable, the capacities are necessarily equal)
     *
     * @param stateObject    Should be an instance of ThreeJugState
     * @return  true iff contents of both states are identical
     */
    public boolean equals(Object stateObject) {
        ThreeJugState state = (ThreeJugState)stateObject;
        for (int j = 0; j < 3; j++) {
            if (this.content[j] != state.content[j]) return false;
        }
        return true;
    }


    /**
     * cloneState creates a new state identical to the current state
     *
     * @return a new state whose content values are the same as those of the current state.
     */
    public ThreeJugState cloneState() {
        return new ThreeJugState(content[0], content[1], content[2]);
    }


    /**
     * decant implements pouring the contents of one jug into another.
     *
     * @param source The index of jug from which liquid is poured. Must be in range 0..2
     * @param dest   The index of jug into which liquid is poured. Must be in range 0..2
     * @return A ThreeJugState that is the state after the pouring is completed
     *
     *         Pouring is continued until no more liquid can be transferred, either
     *         because the source jug is empty or because the destination jug is full.
     *
     *         If the contents of the jugs change, the state returned is a new one. If no
     *         change occurs (either because source is empty or dest is already full),
     *         the state returned is the original one, not a copy of the original.
     *
     *         Source and dest should denote different jugs. If they are the same, a
     *         warning is printed on standard output and the original state returned.
     */
    public ThreeJugState decant(int source, int dest) {
        if (source == dest) { // Source and dest are the same jug
            System.out.println("WARNING: Attempt to decant from and to the same jug: " + source);
            return this;
        } else if (content[source] == 0) { // Nothing to pour
            return this;
        } else if (content[dest] == capacity[dest]) { // Destination already full
            return this;
        } else {
            int remainingVolume = capacity[dest] - content[dest];
            int newDestContent, newSourceContent;
            if (remainingVolume >= content[source]) {  // Empty source into destination
                newSourceContent = 0;
                newDestContent = content[dest] + content[source];
            } else {        // Fill up destination from source leaving remainder in source
                newDestContent = capacity[dest];
                newSourceContent = content[source] - remainingVolume;
            }
            ThreeJugState newState = this.cloneState();
            newState.setContent(source, newSourceContent);
            newState.setContent(dest, newDestContent);
            return newState;
        }
    }
    
    /**
     * Simple implementation of hashCode that uses the content array of
     * this class
     * 
     * @return int the hash code for the content array.
     */
	public int hashCode() {
		return Arrays.hashCode(content);
	}
}
