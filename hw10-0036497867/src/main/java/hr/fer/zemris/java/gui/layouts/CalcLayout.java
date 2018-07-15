package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * A layout manager used for making a simple calculator with a display.
 * It contains a 5 rows and 7 columns grid of same sized cells,
 * exception being the first (upper-left) cell which takes the position
 * of first five cells of the first row.<br>
 * Also, the gap length between the cells (in pixels) can be specified
 * when constructing the layout.
 * 
 * @author Mate Gasparini
 */
public class CalcLayout implements LayoutManager2 {
	
	/* CONSTRAINTS */
	/**
	 * First row index.
	 */
	private static int MIN_ROW = 1;
	/**
	 * Last row index.
	 */
	private static int MAX_ROW = 5;
	/**
	 * First column index.
	 */
	private static int MIN_COLUMN = 1;
	/**
	 * Last column index.
	 */
	private static int MAX_COLUMN = 7;
	/**
	 * Column index of the last cell that the biggest cell contains.
	 */
	private static int LAST_BIG_CELL_COLUMN = 5;
	
	/**
	 * Pixel length of the inner gap between all cells.
	 */
	private int gap;
	/**
	 * Map of all components and their corresponding positions.
	 */
	private Map<Component, RCPosition> components = new HashMap<>();
	
	/**
	 * Default constructor.
	 */
	public CalcLayout() {
		//this(0);
	}
	
	/**
	 * Constructor specifying the gap length.
	 * 
	 * @param gap The specified gap length.
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		// Do nothing.
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension preffered = new Dimension();
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension current = entry.getKey().getPreferredSize();
			
			if (current.height > preffered.height) {
				preffered.height = current.height;
			}
			
			RCPosition position = entry.getValue();
			if (position.getColumn() == 1 && position.getRow() == 1) {
				int scaledWidth = (current.width - (LAST_BIG_CELL_COLUMN-1) * gap)
									/ LAST_BIG_CELL_COLUMN;
				if (scaledWidth > preffered.width) {
					preffered.width = scaledWidth;
				}
			} else if (current.width > preffered.width) {
				preffered.width = current.width;
			}
		}
		
		preffered.height = preffered.height * MAX_ROW + (MAX_ROW - 1) * gap;
		preffered.width = preffered.width * MAX_COLUMN + (MAX_COLUMN - 1) * gap;
		
		return preffered;
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension minimum = new Dimension();
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension current = entry.getKey().getMinimumSize();
			
			if (current.height > minimum.height) {
				minimum.height = current.height;
			}
			
			RCPosition position = entry.getValue();
			if (position.getColumn() == 1 && position.getRow() == 1) {
				int scaledWidth = (current.width - (LAST_BIG_CELL_COLUMN - 1) * gap)
									/ LAST_BIG_CELL_COLUMN;
				if (scaledWidth > minimum.width) {
					minimum.width = scaledWidth;
				}
			} else if (current.width > minimum.width) {
				minimum.width = current.width;
			}
		}
		
		minimum.height = minimum.height * MAX_ROW + (MAX_ROW - 1) * gap;
		minimum.width = minimum.width * MAX_COLUMN + (MAX_COLUMN - 1) * gap;
		
		return minimum;
	}
	
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - insets.left - insets.right;
		int maxHeight = parent.getHeight() - insets.top - insets.bottom;
		int cellWidth = (maxWidth + gap - MAX_COLUMN * gap) / MAX_COLUMN;
		int cellHeight = (maxHeight + gap - MAX_ROW * gap) / MAX_ROW;
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component component = entry.getKey();
			RCPosition position = entry.getValue();
			
			int column = position.getColumn();
			int row = position.getRow();
			
			int x = (column - 1) * (cellWidth + gap);
			int y = (row - 1) * (cellHeight + gap);
			int width = (column == 1 && row == 1) ?
				LAST_BIG_CELL_COLUMN * (cellWidth + gap) - gap : cellWidth;
			
			component.setBounds(x, y, width, cellHeight);
		}
	}
	
	/**
	 * @throws IllegalArgumentException If the given constraints reference
	 * 			is not of type {@code RCPosition} or {@code String}.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position;
		
		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		} else {
			throw new IllegalArgumentException(
				"Constraints must be either of type RCPosition or String."
			);
		}
		
		checkConstraints(position);
		
		components.put(comp, position);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension maximum = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension current = entry.getKey().getMaximumSize();
			
			if (current.height < maximum.height) {
				maximum.height = current.height;
			}
			
			RCPosition position = entry.getValue();
			if (position.getColumn() == 1 && position.getRow() == 1) {
				int scaledWidth = (current.width - (LAST_BIG_CELL_COLUMN - 1) * gap)
									/ LAST_BIG_CELL_COLUMN;
				if (scaledWidth < maximum.width) {
					maximum.width = scaledWidth;
				}
			} else if (current.width < maximum.width) {
				maximum.width = current.width;
			}
		}
		
		maximum.height = maximum.height * MAX_ROW + (MAX_ROW - 1) * gap;
		maximum.width = maximum.width * MAX_COLUMN + (MAX_COLUMN - 1) * gap;
		
		return maximum;
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}
	
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}
	
	@Override
	public void invalidateLayout(Container target) {
		// Do nothing.
	}
	
	/**
	 * Checks if the given constraints are valid and inside borders.
	 * 
	 * @param constraints The given constraints.
	 * @throws CalcLayoutException If the given constraints are invalid.
	 */
	private void checkConstraints(RCPosition constraints) {
		int row = constraints.getRow();
		if (row < MIN_ROW || row > MAX_ROW) {
			throw new CalcLayoutException("The row is out of bounds: " + row);
		}
		
		int column = constraints.getColumn();
		if (column < MIN_COLUMN || column > MAX_COLUMN) {
			throw new CalcLayoutException("The column is out of bounds: " + column);
		}
		
		if (row == MIN_ROW && column > MIN_COLUMN && column <= LAST_BIG_CELL_COLUMN) {
			throw new CalcLayoutException(
				"The cell cannot be taken, as it is already taken by (1,1): "
				+ row + ", " + column
			);
		}
		
		if (components.containsValue(constraints)) {
			throw new CalcLayoutException(
				"The position is already taken: "
				+ row + ", " + column
			);
		}
	}
}
