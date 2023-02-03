import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Natural Merge Adapted from 
 * https://leetcode.com/problems/sort-list/solutions/997518/java-natural-merge-sort-of-linked-list-o1-space/
 * 
 * @author Timothy Jarvis
 *
 */
public class NaturalMerge {
	private Node Top;
	private ReadWrite read = new ReadWrite();
	private Segment segmentPartitions;
	private BufferedWriter output;
	private boolean print;

	private Node temp;
	
	String text;
	
	public NaturalMerge(String file) throws IOException {
		this.Top = read.readFileMerge(file);
		
		if (file.substring(3, 5).equals("50")) {
			text = "Natural Merge, File Size 50, " + file.substring(0, 3) + ".txt";
			output = new BufferedWriter(new FileWriter(text, true));
			print = true;
			read.writeResult("Natural Merge File Size 50 using file:  " + file, output);
			
		}
		else {
			print = false;
		}
		temp = Top;
		
	    if (print) {
		    read.writeResult("\nUnSorted List: ", output);
			while (temp != null) { 
				read.writeResult(temp.value + "", output);
				temp = temp.next; 
			}
			read.writeResult("", output);
	    }
		
		segmentPartitions = sortList(Top);
	}
	
	public Segment naturalMergeSort() {
	    
	    Segment seg1 = segmentPartitions;
	    Segment seg2 = segmentPartitions.next;
	    
	    Segment Top = seg1;
	    Segment merged = new Segment();
	    
	    
	    while (Top.next != null) {
	    	Segment temp = new Segment();
	    	seg2 = Top.next;
	    	seg1 = Top;
	    	
		    while (seg2 != null) {
				merged = merge(seg1, seg2);
		        
				
		        temp.next = merged;
		        temp = merged;
		        
		        if (Top == seg1) {
		        	Top = merged;
		        }
		        
		        seg1 = seg2.next;
		        
		        if (seg1 == null) {
		        	seg2 = null;
		        }
		        else {
		        	seg2 = seg1.next;
		        }
		        
			    if (seg2 == null && seg1 != null) {
			    	if (Top.next == null) {
			    		Top = merge(Top, seg1);
			    	}
			    	else {
			    		temp.next = seg1;
			    	}
			    }
		    }
	    }
	    
	    if (print) {
		    Node node = Top.head;
		    read.writeResult("\nSorted List: ", output);
			while (node != null) { 
				read.writeResult(node.value + "", output);
				node = node.next; 
			}
			read.writeResult("\nComparison: " + Top.comparisons + "\tExchanges: " + Top.comparisons + "\t Merges: " + Top.merges + "\n\n\n", output);

	        try {
	            output.close();
	        } catch (Exception x) {
	            System.err.println(x.toString());
	        }
	    }
	    	    
	    return Top;
	}

	
	private Segment sortList(Node Head) {
		Segment seg1;
		Segment seg2;
		Segment temp = null;
		Segment segmentHead = null;
		
		
		int count = 0;
		
		if (Head == null) {
			return null;
		}
	    Node nextHead = Head;
	    
	    do {
	        seg1 = getSortedSegment(nextHead);
	        seg2 = getSortedSegment(seg1.tail.next);
	        seg1.tail.next = null;
	        
	        // record head for next merging
	        if (seg2 == null) {
	        	nextHead = null;
	        }
	        else {
	        	seg1.merges = 0;
		        seg2.merges = 0;
	        	nextHead = seg2.tail.next;
	        	seg2.tail.next = null;
	        }
	        if (count == 0) {
	        	segmentHead = seg1;
		        segmentHead.next = seg2;
		        count++;
	        }
	        else {
	        	temp.next = seg1;
		        seg1.next = seg2;
		        count++;
	        }
	        temp = seg2;
	    }
	    while (nextHead != null);
	    
	   
	return segmentHead;
	}
	
	
	private Segment getSortedSegment(Node head) {
		
	    if (head == null) return null;
	    Node tail = head;
	    int size = 1;
	    
	    while (tail.next != null && tail.next.value >= tail.value) {
		  if (print && tail.next.value != 0) {
			  text = tail.next.value + " Compared to " + tail.value + "\t" 
					  + Math.max(tail.next.value, tail.value) + " Selected for Exchange";
			  read.writeResult(text, output);
		  }
	      tail = tail.next;
	      size++;
	    }
	    
	    if(tail.next != null && print) {
	    	text = tail.next.value + " Compared to " + tail.value + "\t" 
					  + Math.max(tail.next.value, tail.value) + " Selected for Exchange";
			read.writeResult(text, output);
	    }
	    
	    Segment segment = new Segment(head, tail);
	    
	    if (tail.next == null) {
	    	segment.comparisons = size - 1;
	    }
	    else {
	    	segment.comparisons = size;
	    }
	    
	    return segment;
	  }
	
	
	private Segment merge(Segment seg1, Segment seg2) {
	    if (seg2 == null) 
	    	return seg1;
	    if (seg1 == null) 
	    	return seg2;
	    
	    Segment newSegment = new Segment();
	    newSegment.merges = seg1.merges + seg2.merges;
	    
	    Node node1 = seg1.head;
	    Node node2 = seg2.head;
	    
	    int comparisons = seg1.comparisons + seg2.comparisons;
	    
	    while (node1 != null && node2 != null) {
	      if (node1.value <= node2.value) {
	    	  if (newSegment.head == null) {
	    		  newSegment.head = node1;
			      newSegment.tail = node1;
	    	  }
	    	  else {
	    		  newSegment.tail.next = node1;
	    		  newSegment.tail = node1;
	    	  }
			  if (print) {
				  if (node2 != null & node1 != null) {
					  text = node1.value + " Compared to " + node2.value + "\t" 
							  + Math.max(node1.value, node2.value) + " Selected for Exchange";
					  read.writeResult(text, output);
				  }
			  }
	          node1 = node1.next;
	      } 
	      else {
			  if (print) {
				  if (node2 != null & node1 != null) {
					  text = node1.value + " Compared to " + node2.value + "\t" 
							  + Math.max(node1.value, node2.value) + " Selected for Exchange";
					  read.writeResult(text, output);
				  }
			  }
	    	  if (newSegment.head == null) {
	    		  newSegment.head = node2;
			      newSegment.tail = node2;
	    	  }
	    	  else {
	    		  newSegment.tail.next = node2;
	    		  newSegment.tail = node2;
	    	  }
	    	  node2 = node2.next;
	      }
	      comparisons++;
	    }
	    
	    // if there are no more node 1 values; node 2 is added to the segment (it is already ordered)
	    if (node1 == null) {
	    	newSegment.tail.next = node2;
	      	newSegment.tail = seg2.tail;
	    }
	    // if there are no more node 2 values; node 1 is added to the segment (it is already ordered)
	    else {
	    	newSegment.tail.next = node1;
	    	newSegment.tail = seg1.tail;
	    }
	    newSegment.tail.next = null;
	    
	    newSegment.comparisons = comparisons;
	    newSegment.merges++;
	    
	    return newSegment;
	}
}
