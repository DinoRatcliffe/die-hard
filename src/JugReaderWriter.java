import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * JugReaderWriter
 * 	
 * This class is used to get JugProblems from a file and also print jug problems to
 * an output file.
 * 
 * file should be in the form of one problem per line with 8 integers e.g:
 * 
 * 8 5 3 8 0 0 4 4 0
 * 
 * First 3 ints define the maximum capacities of the jugs.
 * Middle 3 ints define the initial contents of the jugs.
 * Last 3 ints define the goal state.
 * 
 * @author DinoRatcliffe
 * 
 */
public class JugReaderWriter {
	//hasNext = true if there is another problem available from file.
	//inputOpen = true if inputScanner is still open.
	private boolean hasNext, inputOpen;
	
	//Scanner used for getting ints from the inputFile
	private Scanner inputScanner;
	
	//Name of input and output files.
	private String inputFile, outputFile;
	
	/**
	 * Constructor that sets the inputFile and outputFile names as well 
	 * as initialising both flags.
	 * 
	 * @param inputFile filename of the file to get the problems from. 
	 * @param outputFile filename of the file to output to.
	 */
	public JugReaderWriter(String inputFile, String outputFile){
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		inputOpen = false;
		hasNext = true;
	}
	
	/**
	 * attempts to open the input scanner using the file name stored in
	 * the variable inputFile
	 */
	private void openInput(){
		try {
			inputScanner = new Scanner(new File(inputFile));
			inputOpen = true;
		} catch (FileNotFoundException e) {
			System.out.println("Error opening input file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * closes the inputScanner object
	 */
	public void closeInput(){
		inputScanner.close();
		inputOpen = false;
	}
	
	/**
	 * opens (if needed) and reads the next problem from the input file.
	 * 
	 * @return returns a JugProblem object that represents the problem
	 * 		   read from the input file.
	 */
	public JugProblem getNextGame(){
		if (!inputOpen) openInput();
		
		int[] problem = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i = 0; i<9; i++){
			problem[i] = inputScanner.nextInt();
		}
		
		if (!inputScanner.hasNextInt()){
			hasNext = false;
			closeInput();
		}
		
		return new JugProblem(problem);
		
	}
	
	/**
	 * deletes the given output file
	 */
	public void clearOutputFile(){
		File file = new File(outputFile);
		file.delete();
	}
	
	/**
	 * Takes a jug problem and then outputs its current state in text form to
	 * the output file.
	 * 
	 * @param output JugProblem that you wish to be output to file.
	 */
	public void writeToOutput(JugProblem output){
		PrintStream p;
		try {
			p = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(outputFile), true)));
			p.print("\nNext Problem:\n" + output);
		    p.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error opening output file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Setters and Getters
	 */
	public boolean hasNext(){
		return hasNext;
	}
}
