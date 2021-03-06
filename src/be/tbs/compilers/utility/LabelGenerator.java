package be.tbs.compilers.utility;


/**
 * Generates base labels to use in p machine code.
 * @author Gino Wuytjens & Philip De Smedt
 */
public class LabelGenerator {
	
	public enum LabelTypes {
		IF, WHILE, COND, COMP_STMNT;
	}
	
	private static int if_labels = 0;
	private static int while_labels = 0;
	private static int cond_labels = 0;
	private static int comp_stmnt_labels = 0;
	
	/**
	 * Generates a label of the requested type.
	 * @param t The type of label required.
	 * @return A new label.
	 */
	public static String generate(LabelTypes t) {
		String lbl;
		switch (t) {
		case IF:
			lbl = "IF_" + if_labels + "_";
			if_labels++;
			break;
		case WHILE:
			lbl = "WHILE_" + while_labels + "_";
			while_labels++;
			break;
		case COND:
			lbl = "COND_" + cond_labels + "_";
			cond_labels++;
			break;
		default:
			lbl = "COMPSTMNT_" + comp_stmnt_labels + "_";
			comp_stmnt_labels++;
			break;
		}
		
		return lbl;
	}
	
	
	
	

}
