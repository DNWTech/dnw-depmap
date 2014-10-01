/**
 * !(#) MockCompilationUnitVisitor.java
 * Copyright (c) 2014 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 30, 2014.
 */
package com.dnw.plugin.ast.mock;

import org.eclipse.jdt.core.dom.CompilationUnit;

import com.dnw.plugin.ast.Visitor;

/**
 * Class/Interface MockCompilationUnitVisitor.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 * 
 */
public class MockCompilationUnitVisitor implements Visitor<CompilationUnit> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param node
	 * 
	 * @see com.dnw.plugin.ast.Visitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	public void visit(CompilationUnit node) {
	}
}
