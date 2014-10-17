/**
 * !(#) MockCompilationUnitVisitor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 30, 2014.
 */
package com.dnw.plugin.ast.mock;

import org.eclipse.jdt.core.dom.CompilationUnit;

import com.dnw.plugin.ast.VisitContext;
import com.dnw.plugin.ast.Visitor;

/**
 * Class/Interface MockCompilationUnitVisitor.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class MockCompilationUnitVisitor implements Visitor<CompilationUnit> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param node
	 * @see com.dnw.plugin.ast.Visitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	public void visit(CompilationUnit node, VisitContext context) {
	}
}
