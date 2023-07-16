//import java.util.ArrayDeque;
import java.util.*;
public class HuffmanTree 
{
	//maybe create an ArrayList ?
	private HuffmanNode root;
	private PriorityQueue <HuffmanNode> pq;
	private  ArrayList<HuffmanNode> list;
	private Character [] keySet;
	private HashMap <Character, Integer> map;
	public HuffmanTree (Character [] keySet, HashMap <Character, Integer> map) {
		this.keySet = keySet;
		this.map = map;
		this.list = new ArrayList <HuffmanNode> ();
		createOrderedList ();
		makeHuffmanTree();
		createOrderedListWithCode();
		//createTreeWithTwoSmallestNodes (list);
	}
	public ArrayList<HuffmanNode> getListOfNodes () {
		return list;
	}
	private void createOrderedListWithCode () {
		this.list = new ArrayList <HuffmanNode> ();
		 if (root == null) {
	            return;
	        }
	        Stack<HuffmanNode> stack = new Stack<>();
	        stack.push(root);
	        while (!stack.isEmpty()) {
	            HuffmanNode currNode = stack.pop();
	            if (currNode.getLeft () == null && currNode.getRight () == null) {
	                list.add(currNode);
	            } else {
	                if (currNode.getRight () != null) {
	                    stack.push(currNode.getRight());
	                }
	                if (currNode.getLeft() != null) {
	                    stack.push(currNode.getLeft());
	                }
	            }
	        }
	}
	private void createOrderedList () {
		for (Character c : keySet) {
			HuffmanNode node = new HuffmanNode (c);
			node.setFrequency((Integer)map.get(c));
			list.add(node);
		}
	}
	private PriorityQueue<HuffmanNode> makePQ ()
	{
		PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode> ();
		for (Character c : keySet) {
			HuffmanNode node = new HuffmanNode (c);
			node.setFrequency((Integer)map.get(c));
			pq.add(node);
		}
		return pq;
	}
	private void makeHuffmanTree () {
		this.pq = makePQ ();
		while(pq.size () > 1) {
			HuffmanNode leftChild = pq.poll ();
			//list.add(leftChild);
			HuffmanNode rightChild = pq.poll ();
			//list.add(rightChild);
			HuffmanNode parent = new HuffmanNode (leftChild.getFrequency() + rightChild.getFrequency());
			parent.setLeft(leftChild);
			parent.setRight(rightChild);
			pq.add(parent);
		}
		//more than 1 distinct char in the text file
		if (list.size() > 1) {
			this.root = pq.poll();
			fillCode(root, "");
		}
		else {
			this.root = pq.poll ();
			root.setCode("0");
		}
	}

	
    private void fillCode(HuffmanNode node, String code) {
    	//if the node that was traversed to was null
        if (node == null) {
            return;
        }
        //if the node is a leaf
        else if (node.getLeft() == null && node.getRight() == null) {
            //System.out.println(node.getChar() + ": " + code);
            node.setCode(code);
        }
        fillCode(node.getLeft(), code + "0");
        fillCode(node.getRight(), code + "1");
    }

}
