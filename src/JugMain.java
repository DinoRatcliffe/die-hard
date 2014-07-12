
public class JugMain {
	
	/**
	 * Program that takes a list of "three jug problems" from a file
	 * and then outputs the results to another file.
	 * 
	 * default:
	 * input file = "data.txt"
	 * output file = "output.txt"
	 * 
	 * @param args String[] can specify an input and output file as:
	 * 						
	 * 						java JugMain input.txt output.txt
	 * 						
	 * 						if no input or output file is given default 
	 * 						filenames are used
	 * 						
	 * 						is possible to provide input file without output
	 * 						file and the default output will be used.
	 * 
	 */
	public static void main(String args[]){
		String inputDir = args.length>0 ? args[0]:"data.txt";
		String outputDir = args.length>1 ? args[1]:"output.txt";
		
		JugReaderWriter readerWriter = new JugReaderWriter(inputDir, outputDir);
		readerWriter.clearOutputFile();
		
		while(readerWriter.hasNext()){
			JugProblem currentProblem = readerWriter.getNextGame();
			currentProblem.solveProblem();
			
			readerWriter.writeToOutput(currentProblem);
			System.out.print("\nNext Problem:\n" + currentProblem);
		}
		
		readerWriter.closeInput();
	}
}
