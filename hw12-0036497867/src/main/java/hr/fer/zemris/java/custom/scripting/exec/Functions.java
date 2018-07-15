package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Utility class which provides functionality of executing different supported functions
 * and updating the specified {@link ObjectStack} and {@link RequestContext}.<br>
 * Each function is stored in a {@code Map} with the function name as its key.
 * 
 * @author Mate Gasparini
 */
public class Functions {
	
	/** Map containing all currently supported functions. */
	private static final Map<String, BiConsumer<ObjectStack, RequestContext>> MAP =
		Map.ofEntries(
			Map.entry("sin", (stack, context) -> {
				Object popped = stack.pop();
				double x = 0;
				if (popped instanceof String) {
					x = Double.parseDouble((String) popped);
				} else if (popped instanceof Integer) {
					x = (Integer) popped;
				} else if (popped instanceof Double) {
					x = (Double) popped;
				}
				stack.push(Math.sin(x*Math.PI/180.0));
			}),
			Map.entry("decfmt", (stack, context) -> {
				Object popped = stack.pop();
				checkIfString(popped, "decimal format");
				DecimalFormat formatter = new DecimalFormat((String) popped);
				popped = stack.pop();
				double x = 0;
				if (popped instanceof String) {
					x = Double.parseDouble((String) popped);
				} else if (popped instanceof Integer) {
					x = (Integer) popped;
				} else if (popped instanceof Double) {
					x = (Double) popped;
				}
				stack.push(formatter.format(x));
			}),
			Map.entry("dup", (stack, context) -> {
				Object popped = stack.pop();
				stack.push(popped);
				stack.push(popped);
			}),
			Map.entry("swap", (stack, context) -> {
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
			}),
			Map.entry("setMimeType", (stack, context) -> {
				Object popped = stack.pop();
				checkIfString(popped, "mime type");
				context.setMimeType((String) popped);
			}),
			Map.entry("paramGet", (stack, context) -> {
				paramGet(stack, context::getParameter);
			}),
			Map.entry("pparamGet", (stack, context) -> {
				paramGet(stack, context::getPersistentParameter);
			}),
			Map.entry("pparamSet", (stack, context) -> {
				paramSet(stack, context::setPersistentParameter);
			}),
			Map.entry("pparamDel", (stack, context) -> {
				paramDel(stack, context::removePersistentParameter);
			}),
			Map.entry("tparamGet", (stack, context) -> {
				paramGet(stack, context::getTemporaryParameter);
			}),
			Map.entry("tparamSet", (stack, context) -> {
				paramSet(stack, context::setTemporaryParameter);
			}),
			Map.entry("tparamDel", (stack, context) -> {
				paramDel(stack, context::removeTemporaryParameter);
			})
	);
	
	/**
	 * Private default constructor.
	 */
	private Functions() {
	}
	
	/**
	 * Executes the function corresponding to the given function name, and
	 * updates the the given stack and/or the given context (if needed).
	 * 
	 * @param function The given function name.
	 * @param stack The given stack.
	 * @param context The given context.
	 * @throws IllegalArgumentException If the given function is not valid/supported.
	 */
	public static void calculate(String function, ObjectStack stack, RequestContext context) {
		if (!MAP.containsKey(function)) {
			throw new IllegalArgumentException("Invalid function: " + function);
		}
		MAP.get(function).accept(stack, context);
	}
	
	/**
	 * Pops the default value and the name of a stored value from the given stack.
	 * Using the popped value name and the given getter, a value is obtained.
	 * The returned value is then pushed onto the given stack (unless it is {@code null};
	 * in that case, the default value is used).
	 * 
	 * @param stack The given stack.
	 * @param getter The given getter.
	 */
	private static void paramGet(ObjectStack stack, Function<String, Object> getter) {
		Object defaultValue = stack.pop();
		Object popped = stack.pop();
		checkIfString(popped, "parameter name");
		Object value = getter.apply((String) popped);
		stack.push(value == null ? defaultValue : value);
	}
	
	/**
	 * Pops the name of a stored value and a value from the given stack.
	 * The popped value is then stored using the given setter with the popped value name
	 * as a key.
	 * 
	 * @param stack The given stack.
	 * @param setter The given setter.
	 */
	private static void paramSet(ObjectStack stack, BiConsumer<String, String> setter) {
		Object popped = stack.pop();
		checkIfString(popped, "parameter name");
		String name = (String) popped;
		popped = stack.pop();
		setter.accept(name, String.valueOf(popped));
	}
	
	/**
	 * Pops the name of a stored value from the given stack and removes
	 * its corresponding mapped value using the given remover.
	 * 
	 * @param stack The given stack.
	 * @param remover The given remover.
	 */
	private static void paramDel(ObjectStack stack, Consumer<String> remover) {
		Object popped = stack.pop();
		checkIfString(popped, "parameter name");
		remover.accept((String) popped);
	}
	
	/**
	 * Checks if the given object is a {@link String} instance.<br>
	 * If it is not, an exception is built using the given String as a name
	 * of the String-expected parameter for its message.
	 * 
	 * @param object The given object.
	 * @param expected Part of the exception message which indicates what exactly
	 * 					was supposed to be a String, but was not.
	 */
	private static void checkIfString(Object object, String expected) {
		if (!(object instanceof String)) {
			throw new IllegalArgumentException("Expected " + expected + " as a String.");
		}
	}
}
