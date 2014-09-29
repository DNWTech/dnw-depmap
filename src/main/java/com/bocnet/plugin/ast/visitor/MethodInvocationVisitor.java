/**
 * !(#) MethodInvocationVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast.visitor;

import org.eclipse.jdt.core.dom.MethodInvocation;

import com.bocnet.plugin.ast.AstVisitor;
import com.bocnet.plugin.ast.VisitContext;

/**
 * Class/Interface MethodInvocationVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class MethodInvocationVisitor implements AstVisitor<MethodInvocation> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 * @return
	 * 
	 * @see com.bocnet.plugin.ast.AstVisitor#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      boolean)
	 */
	public boolean visit(MethodInvocation node, VisitContext context) {
		return false;
	}
}
