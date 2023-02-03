
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

/**
 * Used to create files of integers in ascending order, reverse order and random order
 * Size array calls out the number of integers in the file
 * 
 * 
 * @author Tim Jarvis
 * @version 12/9/2022
 */
public class FileCreation {
	
	public static void main(String [] args){
		String ascending;
		String random;
		String reverse;
		
		Random rand = new Random();
		ReadWrite read = new ReadWrite();
		
		int [] size = {50, 1000, 2000, 5000, 10000, 20000};
		
		//  Open the files that will be used for input and output.
		for (int i = 0; i < size.length; i++) {
			BufferedWriter  outputRandom = null;
			BufferedWriter  outputAscending = null;
			BufferedWriter  outputReverse = null;
			
			if (size[i] > 999) {
				random = "ran" + size[i] / 1000 + "K.txt";
				ascending = "asc" + size[i] / 1000 + "K.txt";
				reverse = "rev" + size[i] / 1000 + "K.txt";
			}
			else {
				random = "ran" + size[i] + ".txt";
				ascending = "asc" + size[i] + ".txt";
				reverse = "rev" + size[i] + ".txt";
			}
	        try {
	            outputRandom = new BufferedWriter(new FileWriter(random));
	            outputAscending = new BufferedWriter(new FileWriter(ascending));
	            outputReverse = new BufferedWriter(new FileWriter(reverse));
	        } catch (Exception ioe) {
	            System.err.println(ioe.toString());
	            return;
	        }
	        
	        for (int j = size[i]; j >= 1; j--) {
	        	read.writeResult(rand.nextInt(size[i] * 5) + "", outputRandom);
	        	read.writeResult(j + "", outputReverse);
	        }
	        
	        for (int j = 1; j <= size[i]; j++) {
	        	read.writeResult(j + "", outputAscending);
	        }
	        
	        //  Clean up and return to the operating system.
	        try {
	            outputRandom.close();
	            outputAscending.close();
	            outputReverse.close();
	        } catch (Exception x) {
	            System.err.println(x.toString());
	        }
		}

        return;
	}
}
