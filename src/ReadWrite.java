
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * Methods for reading and writing files through out the project
 * 
 * @author Tim Jarvis
 * @version 12/9/2022
 *
 */
public class ReadWrite {
    
    /**
     * 
     *  Reads the next expression from the input file for merge sort
     *  @param  input is the input file string
     *  @return The next prefix expression from the input file.
     *  @throws FileNotFoundException 
     */
    public Node readFileMerge(String input) throws FileNotFoundException {
    	
    	Scanner scanner = new Scanner(new File(input));
    	Node head = new Node();
    	head.value = scanner.nextInt();
        
    	Node temp = new Node();
    	temp.value = scanner.nextInt();
    	head.next = temp;
    	
    	while (scanner.hasNextInt()) {
    		Node newNode = new Node();
    		newNode.value = scanner.nextInt();
    		temp.next = newNode;
    		temp = newNode;
    	}
    	
        return head;
    }
    
    /**
     * Converts input file into an array for quick sort method
     * 
     * @param file input
     * @return integer array
     * @throws FileNotFoundException
     */
	public int[] readFileQuick(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		
		String fileSize;
		
		int size;
		
		if (file.contains("K")) {
			fileSize = file.substring(3, file.length() - 5);
			size = Integer.parseInt(fileSize) * 1000;
		}
		else {
			fileSize = file.substring(3, file.length() - 4);
			size = Integer.parseInt(fileSize);
		}
		
		int [] numbers = new int [size];

		for(int i = 0; i < size; i++) {
			numbers[i] = scanner.nextInt();
		}
		
		return numbers;
	}
	
    /**      
     *  Write a string to the output stream.
     *  @param text   The text to write.
     *  @param output The output stream to write the text to.
     */
    public void writeResult(String text, BufferedWriter output) {
    
        try {
            output.write(text, 0, text.length());  
            output.newLine();
        } catch (IOException iox) {
            System.err.println(iox.toString());
            System.exit(3);
        }
        return;
    }   
}
