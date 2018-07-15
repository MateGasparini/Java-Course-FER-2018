package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing class for the <code>UniqueNumbers</code> class.
 * 
 * @author Mate Gasparini
 */
public class UniqueNumbersTest {
	
	@Test
	public void addOneNode() {
		UniqueNumbers.TreeNode head = null;
		
		head = UniqueNumbers.addNode(head, 42);
		
		Assert.assertEquals(42, head.value);
		Assert.assertTrue(UniqueNumbers.containsValue(head, 42));
		Assert.assertEquals(1, UniqueNumbers.treeSize(head));
	}
	
	@Test
	public void addTwoDifferentNodes() {
		UniqueNumbers.TreeNode head = null;
		
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		
		Assert.assertTrue(UniqueNumbers.containsValue(head, 42));
		Assert.assertTrue(UniqueNumbers.containsValue(head, 76));
		Assert.assertEquals(2, UniqueNumbers.treeSize(head));
	}
	
	@Test
	public void addTwoEqualNodes() {
		UniqueNumbers.TreeNode head = null;
		
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 42);
		
		Assert.assertTrue(UniqueNumbers.containsValue(head, 42));
		Assert.assertEquals(1, UniqueNumbers.treeSize(head));
	}
}
