package be.tbs.compilers.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import be.tbs.compilers.exceptions.error.CompileError;
import be.tbs.compilers.exceptions.warning.CompileWarning;
import be.tbs.compilers.stackmachine.AddressSpace;
import be.tbs.compilers.symboltable.SymbolTable;

/**
 * Abstract Syntax Tree!
 * @author Gino
 */
public class AST {
	
	private CommonTree fTree;
	private static boolean fOptimized = false;
	private static SymbolTable fSymbolTable;
	private static List<CompileError> fErrors;
	private static Set<CompileWarning> fWarnings;
	private static AddressSpace fStackMachineSpace;
	
	/**
	 * Constructs an AST using the provided CommonTree.
	 * @param tree CommonTree (usually generated by the parser)
	 */
	public AST(CommonTree tree) {
		fTree = tree;
		if (fSymbolTable == null) fSymbolTable = new SymbolTable();
		if (fErrors == null) fErrors = new ArrayList<CompileError>();
		if (fWarnings == null) fWarnings = new LinkedHashSet<CompileWarning>();
		if (fStackMachineSpace == null) fStackMachineSpace = new AddressSpace();
	}
	
	/**
	 * Gets the line number for this node.
	 * @return A line number.
	 */
	public int getLineNumber() {
		return fTree.getLine();
	}
	
	/**
	 * Gets the character number.
	 * @return A character index on the current line.
	 */
	public int getCharNumber() {
		return fTree.getCharPositionInLine();
	}
	
	/**
	 * Gets the specified child.
	 * @param i Index of the requested child.
	 * @return The child with index i.
	 */
	public AST getChild(int i) {
		Tree child = fTree.getChild(i);
		if (child==null)
			return null;
		else
			return new AST((CommonTree) child);
	}
	
	/**
	 * Gets all children of this AST Node.
	 * @return List of all children of this node.
	 */
	@SuppressWarnings("unchecked")
	public List<AST> getChildren() {
		List<AST> list = new ArrayList<AST>();
		List treeList = fTree.getChildren();
		if (treeList==null)
			return null;
		else {
			for (Object child : treeList) {
				list.add(new AST((CommonTree)child));
			}
		}
		return list;
	}
	
	/**
	 * Gets the number of children belonging to this node.
	 * @return The amount of children.
	 */
	public int getChildCount() {
		return fTree.getChildCount();
	}
	
	/**
	 * Gets the ANTLR CommonTree representation of this AST.
	 * @return This node as a CommonTree.
	 */
	public CommonTree getTree() {
		return fTree;
	}
	
	/**
	 * Gets the type number of this node.
	 * @return Type number defined in the Parser.
	 */
	public int getType() {
		return fTree.getType();
	}
	
	/**
	 * Returns the symbol table for this tree.
	 * @return SymbolTable
	 */
	public SymbolTable getSymbolTable() {
		return fSymbolTable;
	}
	
	public AddressSpace getStackSpace() {
		return fStackMachineSpace;
	}
	
	public String toString() {
		return fTree.toString();
	}
	
	/**
	 * Add a Compile Error to the tree.
	 * @param e - A Compile Error
	 */
	public void addError(CompileError e) {
		fErrors.add(e);
	}
	
	/**
	 * Add a Compile Warning to the tree.
	 * @param w - a compile warning
	 */
	public void addWarning(CompileWarning w) {
	    fWarnings.add(w);
	}
	
	   /**
     * Add a Compile Warning to the tree.
     * @param w - a compile warning
     */
    public void addWarning(CompileWarning w, boolean addPosition) {
        if (addPosition)
            w = new CompileWarning(this.getLocation() + w.getMessage());
        fWarnings.add(w);
    }
	
	/**
	 * Gets all errors encountered during compilation of this compilation unit.
	 * @return A list of CompileError
	 */
	public List<CompileError> getErrors() {
		return fErrors;
	}
	
	/**
	 * Gets all warnings encountered during compilation of this compilation unit.
	 * @return A list of CompileWarning
	 */
	public Set<CompileWarning> getWarnings() {
	    return fWarnings;
	}
	
	/**
	 * Checks if errors were encountered during compilation.
	 * @return True if there are errors, false if not.
	 */
	public boolean hasErrors() {
		return !fErrors.isEmpty();
	}
	
	/**
	 * Checks if warnings were encountered during compilation.
	 * @return True if there are warnings, false if not.
	 */
	public boolean hasWarnings() {
	    return !fWarnings.isEmpty();
	}
	
	public String getLocation() {
	    return "[" + getLineNumber() + ":" + getCharNumber() + "] ";
	}
	
	public void setOptimized(boolean o) {
		fOptimized = o;
	}

	public boolean getOptimized() {
		return fOptimized;
	}
	

}
