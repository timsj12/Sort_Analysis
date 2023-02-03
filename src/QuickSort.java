import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * 
 * @author Tim Jarvis
 *
 */
public class QuickSort {
	
	private ReadWrite read = new ReadWrite();
	private BufferedWriter output;
	private int [] numbers;
	
	private int pivotIndex;
	private int maxSize;
	
	private int comparisons = 0;
	private int exchanges = 0;
	
	private boolean insert;
	private boolean print;
	
	private String text;
	private String file;
	
	
	public QuickSort(String file) throws IOException {
		this(file, false);
	}
	
	/**
	 * 
	 * @param file  file name
	 * @param insert  true if insertion sort is desired to be implemented with quick sort
	 * @throws IOException 
	 */
	public QuickSort(String file, boolean insert) throws IOException {
		numbers = read.readFileQuick(file);
		this.insert = insert;
		this.file = file;
	}
	
	/**
	 * Implement quick sort based on the given inputs: insertion sort? pivot method? 
	 * 
	 * 
	 * @param pivotIndex sets the index of the pivot for each partition; 0 for first value; 3 for median of 3
	 * @param maxSize MaxSize of file if insertion sort is used;  maxSize is irrelevant if insertion sort is false
	 * @return sorted array
	 * @throws IOException 
	 */
	public int [] performSort(int pivotIndex, int maxSize) throws IOException {		
		
		this.pivotIndex = pivotIndex;
		this.maxSize = maxSize;
		
		String pivotMethod = "";
		
		if (file.substring(3, 5).equals("50")) {
			print = true;
			
			if (pivotIndex == 0) {
				pivotMethod = "First Item";
			}
			else {
				pivotMethod = "median-of-three";
			}
			
			if (insert) {
				if (maxSize > 1000) {
					text = "Insertion Sort " + file.substring(0, 3) + ".txt";
				}
				else {
					text = "Quick, Insert " + insert + ", Partition Size  " + maxSize + ", Pivot " + pivotMethod + ", " + file.substring(0, 3) + ".txt";
				}
			}
			else {
				text = "Quick, Insert " + insert + ", Pivot " + pivotMethod + ", " + file.substring(0, 3) + ".txt";
			}
			
			output = new BufferedWriter(new FileWriter(text, true));
			
			read.writeResult("Merge Sort File Size 50, Insertion Sort: " + 
			insert + " using file:  " + file + "\n", output);
		}
		else {
			print = false;
		}
		
		if (print) {
			read.writeResult("Pivot: " + pivotMethod + "\n", output);
			read.writeResult("UnSorted List: ", output);
			for (int i = 0; i < numbers.length; i++) { 
				read.writeResult(numbers[i] + "", output);
			}
			read.writeResult("", output);
		}
		
		int [] unsorted = this.numbers;
		int highIndex = this.numbers.length - 1;
		int lowIndex = 0;
		
		int [] sorted = sort(unsorted, lowIndex, highIndex);
		
		if (print) {
			read.writeResult("\nSorted List: ", output);
			for (int i = 0; i < numbers.length; i++) { 
				read.writeResult(numbers[i] + "", output);
			}
			read.writeResult("", output);
		}
		
		if (print) {
			read.writeResult("Comparison: " + comparisons + "\tExchanges: " + exchanges, output);
			
	        try {
	            output.close();
	        } catch (Exception x) {
	            System.err.println(x.toString());
	        }
		}
		
		return sorted;
		
	}
	
	
	/**
	 * Creates partitions of the given array using quick sort partitioning method
	 * 
	 * @param numbers  input array
	 * @param lowIndex  lower bound of partition
	 * @param highIndex  higher bound of partition
	 * @return  new high index value
	 */
	private int partition(int[] numbers, int lowIndex, int highIndex) {
		int pivotPoint = 0;
		int midPoint = (highIndex - lowIndex) / 2;
		boolean done = false;
		
		
		if (pivotIndex == 0) {
			pivotPoint = lowIndex;
		}

		else if (pivotIndex == 3) {
			pivotPoint = medianPivotSelect(numbers, lowIndex, highIndex, midPoint);
		}
		
		int pivot = numbers[pivotPoint];
		
		
		while (!done) {
			
			// Increment lowIndex while numbers[lowIndex] < pivot
		    while (numbers[lowIndex] < pivot) {
		    	comparisons++;
		    	if (print) {
				    text = "Low Index Value: " + numbers[lowIndex] + " Compared to Pivot Value: " + pivot;
				    read.writeResult(text, output);
				}
		    	lowIndex++;
		    }
		    
		    //needed to increase comparison when while loop exited
	    	comparisons++;
	    	if (print) {
			    text = "Low Index Value: " + numbers[lowIndex] + " Compared to Pivot Value: " + pivot;
			    read.writeResult(text, output);
			}
		    
		    // Decrement highIndex while pivot < numbers[highIndex]
		    while (pivot < numbers[highIndex]) {
		    	comparisons++;
		    	if (print) {
				    text = "High Index Value: " + numbers[highIndex] + " Compared to Pivot Value: " + pivot;
				    read.writeResult(text, output);
				}
		    	highIndex--;
		    }
		    
		    // needed for when above while loop is exited
	    	comparisons++;
	    	if (print) {
			    text = "High Index Value: " + numbers[highIndex] + " Compared to Pivot Value: " + pivot;
			    read.writeResult(text, output);
			}
		      
		    // If zero or one elements remain, then all numbers are 
		    // partitioned. Return highIndex.
		    if (lowIndex >= highIndex) {
		    	done = true;
		    	comparisons++;
		    	if (print) {
				    text = "Low Index: " + lowIndex + " Compared to High Index: " + highIndex;
				    read.writeResult(text, output);
				}
		    }
		    else {
		    	//needed if above if statement is not entered; comparison still made
		    	comparisons++;
		    	if (print) {
				    text = "Low Index: " + lowIndex + " Compared to High Index: " + highIndex + " SWAP NEEDS TO TAKE PLACE!!";
				    read.writeResult(text, output);
				}
		    	
		    	exchanges++;
		    	if (print) {
				    text = "Low Index Value: " + numbers[lowIndex] + " EXCHANGED With High Index Value: " + numbers[highIndex];
				    read.writeResult(text, output);
				}
		    	
		    	// Swap numbers[lowIndex] and numbers[highIndex]
		    	int temp = numbers[lowIndex];
		        numbers[lowIndex] = numbers[highIndex];
		        numbers[highIndex] = temp;
		         
		        // Update lowIndex and highIndex
		        lowIndex++;
		        highIndex--;
		    }
		}
		return highIndex;
	}
	
	// sort the array
	private int [] sort(int [] numbers, int lowIndex1, int highIndex1) {
		
    	if (print) {
		    text = "Low Index: " + lowIndex1 + 
		    		" Compared to " + "High Index: " + highIndex1;
		    read.writeResult(text, output);
		}
    	
    	// comparison placed before if statement as return terminates method and comparison still made
    	comparisons++;
    	
		if(lowIndex1 >= highIndex1) {
			return numbers;
		}
		
		// if quick sort requires insertion sort if partitions below a certain size
		if (insert) {
			int difference = Math.abs(lowIndex1 - highIndex1);
			comparisons++;
			if (difference <= maxSize) {
				if(print) {
					text = "Partition Size: " + difference + 
				    		" Compared to " + "Partition Max Size: " + maxSize;
				    read.writeResult(text, output);
				}
				insertionSort(numbers, lowIndex1, highIndex1);
				return numbers;
			}
		}
		
		// partition array into sections
		int lowEndIndex = partition(numbers, lowIndex1, highIndex1);
		
		sort(numbers, lowIndex1, lowEndIndex);
		sort(numbers, lowEndIndex + 1, highIndex1);
		
		return numbers;
	}
	
	/**
	 * 
	 * Implements insertion sort on the input array between low index and high index value
	 * 
	 * @param numbers  overall array
	 * @param lowIndex  lower index bound of array 
	 * @param highIndex high index bound of array
	 */
	private void insertionSort(int [] numbers, int lowIndex, int highIndex) {

		for (int i = lowIndex; i <= highIndex; ++i) {
		   int j = i;
		   
		   if (j > 0 && print && numbers[j] >= numbers[j - 1]) {
			    text = numbers[j] + 
			    		" Compared to " + numbers[j - 1] + "\t No Insertion Sort Exchange!!";
			    read.writeResult(text, output);
		   }
		   
		   
		   // Insert numbers[i] into sorted part
		   // stopping once numbers[i] in correct position
		   while (j > 0 && numbers[j] < numbers[j - 1]) {
		      comparisons++;
		      exchanges++;
		      
			   if (print) {
				    text = numbers[j] + " Compared to " + numbers[j - 1] + "\t " + numbers[j] +
				    		" Exchanged with " + numbers[j - 1] + " via Insertion Sort";
				    read.writeResult(text, output);
			   }
		      // Swap numbers[j] and numbers[j - 1]
		      int temp = numbers[j];
		      numbers[j] = numbers[j - 1];
		      numbers[j - 1] = temp;
		      --j;
		   }
		   // while loop terminated at final comparison needs to be checked causing loop exit
		   if (print) {
			    text = numbers[i] + 
			    		" Compared to " + numbers[j] + "\t No Insertion Sort Exchange!!";
			    read.writeResult(text, output);
		   }
		   // needed for last check of for lip when condition not met
		   comparisons++;

		}
	}
	
	/**
	 * 
	 * Pivot Point found if median of 3 quicksort is being performed
	 * pivot point is index where the the value is the median of the 3 input values
	 * 
	 * @param numbers  array to to get median pivot point for
	 * @param lowIndex low Index of array
	 * @param highIndex high Index of array
	 * @param midPoint  midPoint of array
	 * @return index of the median value of low Index, high Index, mid point
	 */
	private int medianPivotSelect(int [] numbers, int lowIndex, int highIndex, int midPoint) {
		if (numbers[lowIndex] > numbers[highIndex]) {
			if (print) {
			    text = "FOR PIVOT: Low Index Value: " + numbers[lowIndex] + " Compared to High Index Value: " + numbers[highIndex];
			    read.writeResult(text, output);
			}
		    comparisons++;

			if (numbers[highIndex] > numbers[midPoint]) {
				if (print) {
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to High Index Value: " + numbers[highIndex]
				    		+ " PIVOT = " + numbers[highIndex];
				    read.writeResult(text, output);
				}
			    comparisons++;
			    return highIndex;
			}
			else if (numbers[lowIndex] > numbers[midPoint]) {
				comparisons++;
				if (print) {
					// Both comparisons are performed if midPoint is the pivot
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to High Index Value: " + numbers[highIndex];
				    read.writeResult(text, output);
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to Low Index Value: " + numbers[lowIndex]
				    		+ " PIVOT = " + numbers[midPoint];
				    read.writeResult(text, output);
				}
			    comparisons++;
			    return midPoint;

			}
			else {
				comparisons++;
				if (print) {
					// Both comparisons are performed if lowIndex is the pivot
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to High Index Value: " + numbers[highIndex];
				    read.writeResult(text, output);
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to Low Index Value: " + numbers[lowIndex]
				    		+ " PIVOT = " + numbers[lowIndex];
				    read.writeResult(text, output);
				}
				comparisons++;
				return lowIndex;
			}
		}
		else {
			if (print) {
				// Needed if first if statement not true
			    text = "FOR PIVOT: Low Index Value: " + numbers[lowIndex] + " Compared to High Index Value: " + numbers[highIndex];
			    read.writeResult(text, output);
			}
		    comparisons++;
			if (numbers[lowIndex] > numbers[midPoint]) {
				if (print) {
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to Low Index Value: " + numbers[lowIndex]
				    		+ " PIVOT = " + numbers[lowIndex];
				    read.writeResult(text, output);
				}
			    comparisons++;
			    return lowIndex;
			}
			else if (numbers[highIndex] > numbers[midPoint]){
				comparisons++;
				if (print) {
					// Both comparisons needed to get to this point
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to Low Index Value: " + numbers[lowIndex];
				    read.writeResult(text, output);
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to High Index Value: " + numbers[highIndex]
				    		+ " PIVOT = " + numbers[midPoint];
				    read.writeResult(text, output);
				}
			    comparisons++;
			    return midPoint;
			}
			else {
				comparisons++;
				if (print) {
					// Both comparisons needed to get to this point
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to Low Index Value: " + numbers[lowIndex];
				    read.writeResult(text, output);
				    text = "FOR PIVOT: Mid-Point Value: " + numbers[midPoint] + " Compared to High Index Value: " + numbers[highIndex]
				    		+ " PIVOT = " + numbers[highIndex];
				    read.writeResult(text, output);
				}
			    comparisons++;
			    return highIndex;
			}
		}
	}
	
	public int getComparisons() {
		return comparisons;
	}
	
	public int getExchanges() {
		return exchanges;
	}
}
