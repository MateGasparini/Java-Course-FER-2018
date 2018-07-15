package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program that fills an integer-valued
 * binary search tree with user inputs.
 * After that, it prints all the values
 * both in ascending, as well as in
 * descending order.
 * 
 * @author Mate Gasparini
 */
public class UniqueNumbers {
	
	/**
	 * String that, when entered, ends program execution.
	 */
	public final static String END_INPUT = "kraj";
	
	/**
	 * A static nested class that represents a node.
	 * It contains references to its two child nodes
	 * and an <code>int</code> which holds the value.
	 * 
	 * @author Mate Gasparini
	 */
	static class TreeNode {
		private TreeNode left;
		private TreeNode right;
		int value;
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		TreeNode head = null;
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in))) {
			
			while (true) {
				System.out.print("Unesite broj > ");
				String lineOfInput = null;
				
				try {
					lineOfInput = reader.readLine();
					
					int value = Integer.parseInt(lineOfInput);
					
					if (!containsValue(head, value)) {
						head = addNode(head, value);
						System.out.println("Dodano.");
					} else {
						System.out.println("Broj već postoji. Preskačem.");
					}
				} catch (NumberFormatException ex) {
					if (lineOfInput.equals(END_INPUT)) {
						break;
					}
					
					System.out.println("'" + lineOfInput + "' nije cijeli broj.");
				}
			}
		} catch (IOException ex) {
			System.out.println("Unos vrijednosti više nije moguć.");
		}
		
		if (head != null) {
			System.out.print("Ispis od najmanjeg:");
			printAscending(head);
			System.out.print("\n");
			
			System.out.print("Ispis od najvećeg:");
			printDescending(head);
			System.out.print("\n");
		}
	}
	
	/**
	 * Method that creates a new <code>TreeNode</code>,
	 * sets its <code>value</code> and then adds it
	 * to the tree.
	 * 
	 * @param head Root node of the tree.
	 * @param value Value of the new <code>TreeNode</code>.
	 * @return Root node of the updated tree.
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		TreeNode newNode = new TreeNode();
		newNode.value = value;
		
		if (head == null) {
			return newNode;
		}
		
		TreeNode current = head;
		while (true) {
			if (value < current.value) {
				if (current.left != null) {
					current = current.left;
				} else {
					current.left = newNode;
					break;
				}
			} else if (value > current.value) {
				if (current.right != null) {
					current = current.right;
				} else {
					current.right = newNode;
					break;
				}
			} else {
				// The node already exists in tree. Ignore it.
				break;
			}
		}
		
		return head;
	}
	
	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * @param head Root node of the tree.
	 * @return Number of nodes in the tree.
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		
		return 1 + treeSize(head.left) + treeSize(head.right);
	}
	
	/**
	 * Checks if the tree contains the given <code>value</code>.
	 * 
	 * @param head Root node of the tree.
	 * @param value Value which is searched for.
	 * @return <code>true</code> if <code>value</code> is found;
	 * 			<code>false</code> otherwise.
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		
		if (head.value == value) {
			return true;
		}
		
		return containsValue(head.left, value) ||
				containsValue(head.right, value);
	}
	
	/**
	 * Prints all the tree's values starting with the lowest one.
	 * 
	 * @param head Root node of the tree.
	 */
	public static void printAscending(TreeNode head) {
		if (head != null) {
			printAscending(head.left);
			System.out.print(" " + head.value);
			printAscending(head.right);
		}
	}
	
	/**
	 * Prints all the tree's values starting with the highest one.
	 * 
	 * @param head Root node of the tree.
	 */
	public static void printDescending(TreeNode head) {
		if (head != null) {
			printDescending(head.right);
			System.out.print(" " + head.value);
			printDescending(head.left);
		}
	}
}
