/**
 * !(#) MethodInvocationVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.depmap.ast;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.ui.console.MessageConsole;

import com.bocnet.plugin.ast.Visitor;
import com.bocnet.plugin.util.ConsoleUtil;

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
	 * @see com.bocnet.plugin.ast.VisitorDelegator#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      boolean)
	 */
	public void visit(MethodInvocation node) {
		ConsoleUtil.println(console, node.toString());
	}
}
