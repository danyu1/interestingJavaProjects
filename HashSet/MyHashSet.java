// Implements a singly-linked list.

public class MyHashSet {
	private ListNode[] buckets; //k
	private int objectCount; //n
	private double loadFactorLimit;

	// Constructor: creates an empty hash set with default parameters
	public MyHashSet() {
		this.buckets = new ListNode[10];
		this.objectCount = 0;
		this.loadFactorLimit = 0.75;
	}

	// Constructor: creates a hash set with the given initial bucket size and load factor limit
	public MyHashSet(int bucketCount, double loadFactorLimit) {
		this.buckets = new ListNode[bucketCount];
		this.objectCount = 0;
		this.loadFactorLimit = loadFactorLimit;
	}

	// Return a pointer to the bucket array
	public ListNode[] getBuckets() {
		return this.buckets;
	}

	// Returns true if this set is empty; otherwise returns false.
	public boolean isEmpty() {
		return (objectCount == 0);
	}

	// Returns the number of elements in this set.
	public int size() {
		return objectCount;
	}
	
	// Return the bucket index for the object
	public int whichBucket(Object obj) {
		return (0x7FFFFFFF & obj.hashCode()) % this.buckets.length;
	}

	// Returns the current load factor (objCount / buckets)
	public double currentLoadFactor() {
		/* -- IMPLEMENT THIS -- */
		return ((double)objectCount / this.buckets.length);
	}


	// Return true if the object exists in the set, otherwise false.
	// Use the .equals method to check equality.
	public boolean contains(Object obj) {
		/* -- IMPLEMENT THIS -- */
		//for loop should be i = current(ListNode)
		if (obj == null) {
			return false;
		}
		int index = whichBucket (obj);
		ListNode head = buckets[index];
		if (head == null) {
			return false;
		}
		if (head.getValue ().equals(obj)) {
			return true;
		}
		for (ListNode current = head; current != null; current = current.getNext ()) {
			if (current.getValue().equals(obj)) {
				return true;
			}
		}
		return false;
	}

	// Add an object to the set.
	// If the object already exists in the set you should *not* add another.
	// Return true if the object was added; false if the object already exists.
	// If an item should be added, add it to the beginning of the bucket.
	// After adding the element, check if the load factor is greater than the limit.
	// - If so, you must call rehash with double the current bucket size.
	public boolean add(Object obj) {
		/* -- IMPLEMENT THIS -- */
		if (contains(obj) || obj == null) {
			return false;
		}
		int index = whichBucket (obj);
		ListNode headAtBucket = buckets[index];
		if (headAtBucket == null) {
			headAtBucket = new ListNode (obj);
			buckets [index] = headAtBucket;
			objectCount++;
		}
		else {
			ListNode newNode = new ListNode (obj);
			newNode.setNext(headAtBucket);
			buckets[index] = newNode;
			objectCount++;
			
		}
		if (currentLoadFactor () > loadFactorLimit) {
			rehash (buckets.length * 2);
		}
		return true;
	}

	// Remove the object.  Return true if successful, false if the object did not exist
	public boolean remove(Object obj) {
		/* -- IMPLEMENT THIS -- */
	    if (!contains(obj) || obj == null) {
	        return false;
	    }
	    int index = whichBucket(obj);
	    ListNode head = buckets[index];
	    if (head.getValue().equals(obj)) {
	    	ListNode temp = head;
	        buckets[index] = head.getNext();
	        temp.setNext(null);
	        objectCount--;
	        return true;
	    }
	    ListNode prev = head;
	    ListNode current = head.getNext();
	    while (current != null) {
	        if (current.getValue().equals(obj)) {
	            prev.setNext(current.getNext());
	            objectCount--;
	            return true;
	        }
	        prev = current;
	        current = current.getNext();
	    }
	    return false;
	}


	// Rehash the set so that it contains the given number of buckets
	// Loop through all existing buckets, from 0 to length
	// rehash each object into the new bucket array in the order they appear on the original chain.
	public void rehash(int newBucketCount) {
		/* -- IMPLEMENT THIS -- */
		if (newBucketCount > 0) {
			ListNode [] newBuckets = new ListNode [newBucketCount];
			ListNode [] temp = buckets;
			buckets = newBuckets;
			objectCount = 0;
			for (int i = 0; i < temp.length; i++) {
				ListNode current = temp[i];
				while (current != null) {
					add (current.getValue());
					current = current.getNext ();
				}	
			}
		}
	}


	// The output should be in the following format:
	// [ #1, #2 | { b#: v1 v2 v3 } { b#: v1 v2 } ]
	// #1 is the objCount
	// #2 is the number of buckets
	// For each bucket that contains objects, create a substring that indicates the bucket index
	// And list all of the items in the bucket (in the order they appear)
	public String toString() {
		/* -- IMPLEMENT THIS -- */
		StringBuilder sb = new StringBuilder ("[ ");
		sb.append(objectCount + ", ");
		sb.append(buckets.length + " | ");
		int index = 0;
		for (ListNode bucket : buckets) {
			sb.append (bucketFormat (index, bucket));
			index++;
		}
		sb.append("]");
		return sb.toString ();
	}
	private String bucketFormat (int index, ListNode bucket) {
		String format = "{ b" + index + ": ";
		ListNode current = bucket;
		if (current == null) {
			return "";
		}
		if (current.getNext () == null) {
			format += current.getValue () + " ";
			format += "} ";
			return format;
		}
		while (current != null) {
			format += current.getValue () + " ";
			current = current.getNext ();
		}
		format += "} ";
		return format;
	}

}
//bad for hash set, order is lost and nothing is kept near each other, hard to find min/max search
//good for hash, add, remove and contains are essentially big O of (n/k) < O(0.75) = O(1)