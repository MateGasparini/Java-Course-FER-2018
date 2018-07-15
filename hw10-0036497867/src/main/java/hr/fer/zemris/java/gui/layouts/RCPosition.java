package hr.fer.zemris.java.gui.layouts;

/**
 * Class representing the coordinates of a component in
 * the {@code CalcLayout}.
 * 
 * @author Mate Gasparini
 */
public class RCPosition {
	
	/**
	 * The index of the row.
	 */
	private int row;
	/**
	 * The index of the column.
	 */
	private int column;
	
	/**
	 * Constructor specifying the row and column indexes.
	 * 
	 * @param row The specified row index.
	 * @param column The specified column index.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns the row index.
	 * 
	 * @return The row index.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column index.
	 * 
	 * @return The column index.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Static factory method which parses the given input text
	 * and returns a reference to a new {@code RCPosition} instance.
	 * 
	 * @param text The given input text.
	 * @return The corresponding {@code RCPosition}.
	 * @throws IllegalArgumentException If the given input text
	 * 			is not parseable.
	 */
	public static RCPosition parse(String text) {
		String[] parts = text.split(",");
		
		if (parts.length != 2) {
			throw new IllegalArgumentException(
				"One comma expected. Was: " + (parts.length-1) + "."
			);
		}
		
		try {
			return new RCPosition(
				Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())
			);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Non-parseable input.");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
