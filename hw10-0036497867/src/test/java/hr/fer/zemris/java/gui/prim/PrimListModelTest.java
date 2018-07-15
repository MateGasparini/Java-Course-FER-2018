package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimListModelTest {
	
	@Test
	public void startingContains1() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		assertEquals(Integer.valueOf(1), model.getElementAt(0));
	}
	
	@Test
	public void generatesPrimes() {
		PrimListModel model = new PrimListModel();
		model.next();
		assertEquals(2, model.getSize());
		assertEquals(Integer.valueOf(2), model.getElementAt(1));
		model.next();
		assertEquals(3, model.getSize());
		assertEquals(Integer.valueOf(3), model.getElementAt(2));
		model.next();
		assertEquals(4, model.getSize());
		assertEquals(Integer.valueOf(5), model.getElementAt(3));
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		assertEquals(11, model.getSize());
		assertEquals(Integer.valueOf(29), model.getElementAt(10));
	}
}
