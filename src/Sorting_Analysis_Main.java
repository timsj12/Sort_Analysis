
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Sorting_Analysis_Main {
	
	 /**
     *  Main entry point for the program.
     *  @param args[]   Holds one command line arguments:  the input filename

	 * @throws IOException 
     */
	public static void main (String [] args) throws IOException {

		ReadWrite read = new ReadWrite();
		
		BufferedWriter output;
		
		String text;
		
        //  Check for command line arguments.
        if (args.length != 18) {
            System.exit(1);
        }
        
        output = new BufferedWriter(new FileWriter("ANALYSIS.txt", true));
        
        read.writeResult("File, Sort Type, Comparisons, Exchanges, Merges", output);
        
        for (int i = 0; i < args.length; i++) {
        	System.out.println("\n" + args[i]);

            //Natural Merge Sort
            NaturalMerge merge = new NaturalMerge(args[i]); 
            Segment sortedMerge = merge.naturalMergeSort();
            
            text = args[i] + ", Natural Merge Sort, " + sortedMerge.comparisons + ", " + sortedMerge.comparisons + ", " + sortedMerge.merges;
            read.writeResult(text, output);
            
            // Quick Sort with insertion sort for partitions less than 100; pivot is first value in partition
            QuickSort sort = new QuickSort(args[i], true);
            sort.performSort(0, 100);
            
            text = args[i] + ", QuickSort Insertion Size 100 First Value Pivot, " + sort.getComparisons() + ", " + sort.getExchanges();
            read.writeResult(text, output);
            
           // Quick Sort with insertion sort for partitions less than 50; pivot is first value in partition
            QuickSort sort1 = new QuickSort(args[i], true);
            sort1.performSort(0, 50);
            
            text = args[i] + ", QuickSort Insertion Size 50 First Value Pivot, " + sort1.getComparisons() + ", " + sort1.getExchanges();
            read.writeResult(text, output);
            
            // QuickSort with no insertion sort;  Pivot is first value in partition
            QuickSort sort2 = new QuickSort(args[i]);
            sort2.performSort(0, 50);
            
            text = args[i] + ", QuickSort Only First Value Pivot, " + sort2.getComparisons() + ", " + sort2.getExchanges();
            read.writeResult(text, output);
            
            // QuickSort with no insertion sort; pivot is median of three in the partition
            QuickSort sort3 = new QuickSort(args[i]);
            sort3.performSort(3, 50);
            
            text = args[i] + ", QuickSort Only Median of 3 Pivot, " + sort3.getComparisons() + ", " + sort3.getExchanges();
            read.writeResult(text, output);
            
            // Quick Sort with insertion sort for partitions less than 50; pivot is first value in partition
            QuickSort sort4 = new QuickSort(args[i], true);
            sort4.performSort(0, Integer.MAX_VALUE);
            
            text = args[i] + ", Insertion Sort, " + sort4.getComparisons() + ", " + sort4.getExchanges();
            read.writeResult(text, output);

        }
        try {
            output.close();
        } catch (Exception x) {
            System.err.println(x.toString());
        }
	}
}
        


