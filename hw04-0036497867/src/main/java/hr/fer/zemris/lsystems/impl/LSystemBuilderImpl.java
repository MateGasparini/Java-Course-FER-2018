package hr.fer.zemris.lsystems.impl;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Specific <code>LSystemBuilder</code> implementation which provides
 * functionality for setting up and building a new <code>LSystem</code>.
 * 
 * @author Mate Gasparini
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Length of a turtle's unit step.
	 */
	private double unitLength;
	/**
	 * Scaler which, when needed, scales the turtle's step, so that
	 * the fractals remain the same size.
	 */
	private double unitLengthDegreeScaler;
	/**
	 * The turtle's starting point.
	 */
	private Vector2D origin;
	/**
	 * The angle specifying the turtle's starting direction.
	 */
	private double angle;
	/**
	 * Starting sequence of symbols (or just one symbol) from which
	 * the system develops.
	 */
	private String axiom;
	
	/**
	 * Dictionary that maps the specific symbols to corresponding
	 * Command implementations.
	 */
	private Dictionary commands;
	/**
	 * Dictionary that maps the specific symbols to corresponding
	 * replacing sequences.
	 */
	private Dictionary productions;
	
	/**
	 * Default constructor.
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0.0;
		axiom = "";
		
		commands = new Dictionary();
		productions = new Dictionary();
	}
	
	/**
	 * Returns a new LSystem implementation object, as specified by the
	 * generated configuration.
	 */
	@Override
	public LSystem build() {
		return new LSystem() {
			
			/**
			 * Returns a character sequence which is generated
			 * for a given level from the specified productions.
			 * 
			 * @param level The generating depth.
			 * @return String containing the generated sequence.
			 */
			@Override
			public String generate(int level) {
				if (level == 0) {
					return axiom;
				}
				
				StringBuilder builder = new StringBuilder(axiom);
				for (int currentLevel = 0; currentLevel < level; currentLevel ++) {
					for (int index = 0; index < builder.length(); index ++) {
						String production = (String) productions.get(builder.charAt(index));
						
						if (production != null) {
							builder.replace(index, index + 1, production);
							index += production.length() -1;
						}
					}
				}
				
				return builder.toString();
			}
			
			/**
			 * Generates the sequence and, for every symbol,
			 * executes the corresponding Command, which results
			 * in drawing the fractal on the screen.
			 * 
			 * @param level The generating depth.
			 * @param painter Painter that can draw a line on the screen.
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();
				
				context.pushState(
					new TurtleState(
						origin,
						new Vector2D(
							cos(toRadians(angle)), sin(toRadians(angle))
						),
						Color.BLACK,
						unitLength * pow(unitLengthDegreeScaler, level)
					)
				);
				
				char[] sequence = generate(level).toCharArray();
				for (char currentSymbol : sequence) {
					Command currentCommand = (Command) commands.get(currentSymbol);
					
					if (currentCommand != null) {
						currentCommand.execute(context, painter);
					}
				}
			}
		};
	}
	
	/**
	 * Sets the builder's configuration based on the given array
	 * of lines of text.
	 * 
	 * @param lines The lines of text.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			String[] lineHalves = line.split("\\s+", 2);
			
			if (lineHalves.length == 0) {
				continue;
			} else if (lineHalves.length == 1) {
				if (lineHalves[0].isEmpty()) {
					continue;
				} else {
					throw new LSystemBuilderException(
						"Expected 2 tokens or an empty line. Was: '" + line + "'."
					);
				}
			}
			
			String keyword = lineHalves[0];
			String parameters = lineHalves[1];
			
			try {
				if (keyword.equals("origin")) {
					parseOrigin(parameters);
				} else if (keyword.equals("angle")) {
					parseAngle(parameters);
				} else if (keyword.equals("unitLength")) {
					parseUnitLength(parameters);
				} else if (keyword.equals("unitLengthDegreeScaler")) {
					parseUnitLengthDegreeScaler(parameters);
				} else if (keyword.equals("command")) {
					parseCommand(parameters);
				} else if (keyword.equals("axiom")) {
					parseAxiom(parameters);
				} else if (keyword.equals("production")) {
					parseProduction(parameters);
				} else {
					throw new LSystemBuilderException(
						"Invalid keyword: '" + keyword + "'."
					);
				}
			} catch (NumberFormatException | ArithmeticException
					| IndexOutOfBoundsException ex) {
				throw new LSystemBuilderException(
					"Invalid parameters: '" + parameters + "'."
				);
			}
		}
		
		return this;
	}
	
	/**
	 * Puts a pair specified by the symbol (key) and the given action's
	 * corresponding Command (value) to the internal commands dictionary.
	 * 
	 * @param symbol The given symbol.
	 * @param action The given action.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		/* stringToCommand(action) can be null, because a null-check
		 * is performed before calling execute().
		 * 
		 * This allows the user to effectively erase an existing action
		 * for the given symbol (if ever needed). */
		commands.put(symbol, stringToCommand(action));
		return this;
	}
	
	/**
	 * Puts a pair specified by the symbol (key) and the given
	 * production (value) to the internal productions dictionary.
	 * 
	 * @param symbol The given symbol.
	 * @param production The given production.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		if (production == null) {
			throw new NullPointerException(
				"Symbol cannot have a production to null."
			);
		}
		
		productions.put(symbol, production);
		return this;
	}
	
	/**
	 * Sets the builder's angle to the given angle.
	 * 
	 * @param angle The given angle.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	/**
	 * Sets the builder's axiom to the given axiom.
	 * 
	 * @param axiom The given axiom.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Sets the builder's origin point to the given coordinates.
	 * 
	 * @param x The x-coordinate of the origin.
	 * @param y The y-coordinate of the origin.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * Sets the builder's unit length to the given value.
	 * 
	 * @param unitLength The given value.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	/**
	 * Sets the builder's unit length scaler to the given value.
	 * 
	 * @param unitLengthDegreeScaler The given value.
	 * @return The configured builder.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Converts the given action from a String to the corresponding
	 * <code>Command</code> object.
	 * 
	 * @param action The given action.
	 * @return The corresponding command.
	 */
	private Command stringToCommand(String action) {
		String[] parts = action.split("\\s+");
		
		if (parts.length == 0) {
			return null; // Empty action - no exception.
		}
		
		String commandName = parts[0];
		
		if (parts.length == 1) {
			if (commandName.equals("push")) {
				return new PushCommand();
			} else if (commandName.equals("pop")) {
				return new PopCommand();
			} else if (commandName.isEmpty()) {
				return null; // Empty action - no exception.
			}
		} else if (parts.length == 2) {
			String argument = parts[1];
			
			if (commandName.equals("color")) {
				return new ColorCommand(Color.decode("#".concat(argument)));
			}
			
			double argumentValue = Double.parseDouble(argument);
			
			if (commandName.equals("draw")) {
				return new DrawCommand(argumentValue);
			} else if (commandName.equals("skip")) {
				return new SkipCommand(argumentValue);
			} else if (commandName.equals("scale")) {
				return new ScaleCommand(argumentValue);
			} else if (commandName.equals("rotate")) {
				return new RotateCommand(argumentValue);
			}
		}
		
		throw new LSystemBuilderException(
			"Invalid command: '" + action + "'."
		);
	}
	
	/**
	 * Parses the string containing the origin parameters
	 * and updates the origin value.
	 * 
	 * @param parameters The specified parameters.
	 */
	private void parseOrigin(String parameters) {
		String[] parameterParts = parameters.split("\\s+");
		
		if (parameterParts.length == 2) {
			double x = Double.parseDouble(parameterParts[0]);
			double y = Double.parseDouble(parameterParts[1]);
			setOrigin(x, y);
		} else {
			throw new LSystemBuilderException(
				"Invalid origin parameters: '" + parameters + "'."
			);
		}
	}
	
	/**
	 * Parses the string containing the angle parameter
	 * and updates the angle value.
	 * 
	 * @param parameters The specified parameter.
	 */
	private void parseAngle(String parameters) {
		String[] parameterParts = parameters.split("\\s+");
		
		if (parameterParts.length == 1) {
			double angle = Double.parseDouble(parameterParts[0]);
			setAngle(angle);
		} else {
			throw new LSystemBuilderException(
				"Invalid angle parameter: '" + parameters + "'."
			);
		}
	}
	
	/**
	 * Parses the string containing the unit length parameter
	 * and updates the unit length value.
	 * 
	 * @param parameters The specified parameter.
	 */
	private void parseUnitLength(String parameters) {
		String[] parameterParts = parameters.split("\\s+");
		
		if (parameterParts.length == 1) {
			double unitLength = Double.parseDouble(parameterParts[0]);
			setUnitLength(unitLength);
		} else {
			throw new LSystemBuilderException(
				"Invalid unit length parameter: '" + parameters + "'."
			);
		}
	}
	
	/**
	 * Parses the string containing the unit length scaler parameter
	 * and updates the unit length scaler value.
	 * 
	 * @param parameters The specified parameter.
	 */
	private void parseUnitLengthDegreeScaler(String parameters) {
		String[] parts = parameters.split("\\s+");
		
		double scaler = 1.0;
		boolean parseable = true;
		
		if (parts.length == 1) {
			// Either "%lf/%lf", "%lf" or invalid.
			
			if (parts[0].contains("/")) {
				String[] quotient = parts[0].split("/");
				
				if (quotient.length == 2) {
					scaler = Double.parseDouble(quotient[0])
							/ Double.parseDouble(quotient[1]);
				} else {
					parseable = false;
				}
			} else {
				scaler = Double.parseDouble(parts[0]);
			}
		} else if (parts.length == 2) {
			// Either "%lf/ %lf", "%lf /%lf" or invalid.
			
			if (parts[0].endsWith("/")) {
				double numerator = Double.parseDouble(
						parts[0].substring(0, parts[0].length() - 1)
				);
				double denominator = Double.parseDouble(parts[1]);
				
				scaler = numerator / denominator;
			} else if (parts[1].startsWith("/") && parts[1].length() > 1) {
				double numerator = Double.parseDouble(parts[0]);
				double denominator = Double.parseDouble(
						parts[1].substring(1, parts[1].length())
				);
				
				scaler = numerator / denominator;
			} else {
				parseable = false;
			}
		} else if (parts.length == 3) {
			// Either "%lf / %lf" or invalid.
			
			if (parts[1].equals("/")) {
				scaler = Double.parseDouble(parts[0])
						/ Double.parseDouble(parts[2]);
			} else {
				parseable = false;
			}
		} else {
			parseable = false;
		}
		
		if (parseable) {
			setUnitLengthDegreeScaler(scaler);
		} else {
			throw new LSystemBuilderException(
				"Invalid scaler value: '" + parameters + "'."
			);
		}
	}
	
	/**
	 * Parses the string containing the command parameters
	 * and registers the new command.
	 * 
	 * @param parameters The specified parameters.
	 */
	private void parseCommand(String parameters) {
		String[] parameterParts = parameters.split("\\s", 2);
		
		if (parameterParts.length != 2 || parameterParts[0].length() != 1) {
			throw new LSystemBuilderException(
				"Invalid command parameters: '" + parameters + "'."
			);
		}
		
		char symbol = parameterParts[0].charAt(0);
		registerCommand(symbol, parameterParts[1]);
	}
	
	/**
	 * Parses the string containing the axiom parameter
	 * and updates the axiom value.
	 * 
	 * @param parameters The specified parameter.
	 */
	private void parseAxiom(String parameters) {
		String[] parameterParts = parameters.split("\\s+");
		
		if (parameterParts.length == 1) {
			setAxiom(parameterParts[0]);
		} else {
			throw new LSystemBuilderException(
				"Invalid axiom parameter: '" + parameters + "'."
			);
		}
	}
	
	/**
	 * Parses the string containing the production parameters
	 * and registers the new production.
	 * 
	 * @param parameters The specified parameters.
	 */
	private void parseProduction(String parameters) {
		String[] parameterParts = parameters.split("\\s+");
		
		if (parameterParts.length != 2 || parameterParts[0].length() != 1) {
			throw new LSystemBuilderException(
				"Invalid production parameters: '" + parameters + "'."
			);
		}
		
		char symbol = parameterParts[0].charAt(0);
		registerProduction(symbol, parameterParts[1]);
	}
}
