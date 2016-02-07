package xyz.algogo.desktop.utils;

import xyz.algogo.core.AlgoLine;
import xyz.algogo.core.Instruction;
import xyz.algogo.core.Keyword;

public class AlgoLineUtils {
	
	/**
	 * Checks if the line is an IF and must be followed by an ELSE.
	 * 
	 * @param iff The line.
	 * 
	 * @return <b>true</b> If the IF must be followed by an ELSE.
	 * <b>false</b> Otherwise.
	 */
	
	public static final boolean ifFollowedByElse(final AlgoLine iff) {
		return iff.getInstruction() == Instruction.IF && Boolean.valueOf(iff.getArgs()[1]);
	}
	
	/**
	 * Gets the String representation for the specified keyword.
	 * 
	 * @param keyword The keyword.
	 * 
	 * @return The String representation.
	 */
	
	public static final String getLine(final Keyword keyword) {
		return "<html><b style=\"color:" + getLineColor(keyword) + "\">" + LanguageManager.getString("editor.line.keyword." + keyword.toString().toLowerCase()) + "</b></html>";
	}
	
	/**
	 * Gets the String representation for the specified instruction.
	 * 
	 * @param instruction The instruction.
	 * @param args The arguments.
	 * 
	 * @return The String representation.
	 */
	
	public static final String getLine(final Instruction instruction, final String... args) {
		final StringBuilder builder = new StringBuilder("<html><span style=color:\"" + getLineColor(instruction) + "\"><b>" + LanguageManager.getString("editor.line.instruction." + instruction.toString().replace("_", "").toLowerCase()) + "</b> ");
		switch(instruction) {
		case CREATE_VARIABLE:
			builder.append(Utils.escapeHTML(args[0]) + " <b>" + LanguageManager.getString("editor.line.instruction.createvariable.type") + "</b> " + (args[1].equals("0") ? LanguageManager.getString("editor.line.instruction.createvariable.type.string") : LanguageManager.getString("editor.line.instruction.createvariable.type.number")));
			break;
		case ASSIGN_VALUE_TO_VARIABLE:
			builder.append(Utils.escapeHTML(args[0] + " → " + args[1]));
			break;
		case SHOW_VARIABLE:
		case READ_VARIABLE:
		case SHOW_MESSAGE:
		case IF:
		case WHILE:
			builder.append(Utils.escapeHTML(args[0]));
			break;
		case FOR:
			builder.append(Utils.escapeHTML(args[0]) + " <b>" + LanguageManager.getString("editor.line.instruction.for.from") + "</b> " + args[1] + " <b>" + LanguageManager.getString("editor.line.instruction.for.to") + "</b> " + args[2]);
			break;
		case ELSE:
			break;
		default:
			builder.delete(0, builder.length() - 1);
			break;
		}
		return builder.append("</span></html>").toString();
	}
	
	/**
	 * Gets the color of the specified keyword.
	 * 
	 * @param keyword The keyword.
	 * 
	 * @return The color.
	 */
	
	private static final String getLineColor(final Keyword keyword) {
		return "#D35400";
	}
	
	/**
	 * Gets the color of the specified instruction.
	 * 
	 * @param instruction The instruction.
	 * 
	 * @return The color.
	 */
	
	private static final String getLineColor(final Instruction instruction) {
		switch(instruction) {
		case CREATE_VARIABLE:
		case ASSIGN_VALUE_TO_VARIABLE:
		case SHOW_VARIABLE:
		case READ_VARIABLE:
		case SHOW_MESSAGE:
			return "#22313F";
		default:
			return "#3498DB";
		}
	}

}