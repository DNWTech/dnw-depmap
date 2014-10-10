/**
 * !(#) AstContext.java
 * Copyright (c) 2013 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum On Feb 2, 2013.
 */
package com.dnw.plugin.ast;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Class/Interface AstContext.
 * 
 * @author manbaum
 * @since Feb 2, 2013
 * 
 */
public final class AstContext {

	private final IFile file;
	private final ASTParser parser;
	private final ICompilationUnit unit;
	private final CompilationUnit root;
	private final IProgressMonitor monitor;

	public AstContext(IFile file, IProgressMonitor monitor) {
		this.file = file;
		this.monitor = monitor;

		parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		unit = JavaCore.createCompilationUnitFrom(file);
		parser.setSource(unit);
		root = (CompilationUnit) parser.createAST(monitor);
	}

	/**
	 * Getter of the field file.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * 
	 * @return value of the field file.
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * Getter of the field parser.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * 
	 * @return value of the field parser.
	 */
	public ASTParser getParser() {
		return parser;
	}

	/**
	 * Getter of the field unit.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * 
	 * @return value of the field unit.
	 */
	public ICompilationUnit getUnit() {
		return unit;
	}

	/**
	 * Getter of the field root.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * 
	 * @return value of the field root.
	 */
	public CompilationUnit getRoot() {
		return root;
	}

	/**
	 * Getter of the field monitor.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * 
	 * @return value of the field monitor.
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * Method getAST.
	 * 
	 * @author manbaum
	 * @since Feb 3, 2013
	 * 
	 * @return value of AST.
	 */
	public AST getAST() {
		return root.getAST();
	}
}
