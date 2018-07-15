package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Implementation of the {@code IFilter} interface.<br>
 * It accepts a {@code StudentRecord} if all specified
 * conditional expressions are satisfied for it.
 * 
 * @author Mate Gasparini
 */
public class QueryFilter implements IFilter {
	
	/**
	 * A list of all specified conditional expressions.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructor specifying the list of conditional expressions.
	 * 
	 * @param expressions The list of specified conditional expressions.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			boolean satisfied = expression.getComparisonOperator().satisfied(
					expression.getFieldGetter().get(record),
					expression.getStringLiteral()
			);
			
			if (!satisfied) {
				return false;
			}
		}
		
		return true;
	}
}
