package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {
	
	@Test
	public void prefferedSize1Test() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(new Dimension(152, 158), dim);
	}
	
	@Test
	public void prefferedSize2Test() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(new Dimension(152, 158), dim);
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionRowLower() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(0, 1));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionRowHigher() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(6, 1));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionColumnLower() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(1, 0));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionColumnHigher() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(1, 8));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionBigCellAt2() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(1, 2));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionBigCellAt5() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(1, 5));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void exceptionMultipleComponentsWithSameConstraints() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), new RCPosition(1, 1));
		p.add(new JLabel(), new RCPosition(1, 1));
	}
}
