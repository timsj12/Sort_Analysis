

/**
 * 
 * Segment class required for linked list of linked list natural merge
 * Linked list of nodes contained in each segment
 * 
 * @author Tim Jarvis
 * @version 12/9/2022
 *
 */
public class Segment {
	public Node head;
	public Node tail;
	public Segment next;
	
	public int comparisons;
	public int merges;
	
	public Segment() {
		this(null, null);
	}
	
	/**
	 * 
	 * @param head  head of segment
	 * @param tail tail of segment
	 */
	public Segment(Node head, Node tail) {
	    this.head = head;
	    this.tail = tail;
	    this.next = null;
	}
}