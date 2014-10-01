/**
 * !(#) MethodInvocationVisitor.java
 * Copyright (c) 2014 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.dnw.depmap.ast;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.ui.console.MessageConsole;

import com.dnw.plugin.ast.Visitor;
import com.dnw.plugin.util.ConsoleUtil;

/**
 * Class/Interface MethodInvocationVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class MethodInvocationVisitor implements Visitor<MethodInvocation> {

	private static final MessageConsole console = ConsoleUtil
			.getConsole(MethodInvocationVisitor.class.getName());

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
	public void visit(MethodInvocation node) {
		ConsoleUtil.println(console, node.toString());
	}
}
