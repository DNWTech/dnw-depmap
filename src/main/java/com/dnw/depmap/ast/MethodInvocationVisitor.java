/**
 * !(#) MethodInvocationVisitor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 29, 2014.
 */
package com.dnw.depmap.ast;

import org.eclipse.jdt.core.dom.MethodInvocation;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.Visitor;

/**
 * Class/Interface MethodInvocationVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class MethodInvocationVisitor implements Visitor<MethodInvocation> {

	/**
	 * Constructor of MethodInvocationVisitor.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public MethodInvocationVisitor() {
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 * @return
	 * 
	 * @see com.dnw.plugin.ast.VisitorDelegator#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      boolean)
	 */
	@Override
	public void visit(MethodInvocation node) {
		Activator.console.println("Visit MethodInvocation: " + node.toString());
	}
}
