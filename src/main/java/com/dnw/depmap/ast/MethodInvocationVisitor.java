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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

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
		Activator.console.println(" -- Visit MethodInvocation: "
				+ node.toString());
		ASTNode p = node.getParent();
		while (p != null) {
			if (p instanceof MethodDeclaration) {
				break;
			} else {
				p = p.getParent();
			}
		}
		MethodDeclaration decl = (MethodDeclaration) p;
		Activator.console.println("  . Method is called from: " + make(decl));
		if (p == null)
			return;

		IMethodBinding from = decl.resolveBinding();
		Teller.tellMethodDeclaration(decl, from);
		IMethodBinding to = node.resolveMethodBinding();
		Teller.tellMethodInvocation(node, to);
		Activator.w().createInvocation(from, to, args(node.arguments()));
	}

	private List<Object> args(List<?> list) {
		int n = list.size();
		List<Object> r = new ArrayList<Object>(n);
		for (int i = 0; i < n; i++) {
			Expression e = (Expression) list.get(i);
			r.add(e.toString());
		}
		return r;
	}

	private String make(MethodDeclaration node) {
		StringBuffer sb = new StringBuffer();
		if (node != null) {
			sb.append(node.getReturnType2());
			sb.append(' ');
			sb.append(node.getName());
			sb.append('(');
			for (Object v : node.parameters()) {
				SingleVariableDeclaration d = (SingleVariableDeclaration) v;
				sb.append(d.getType());
			}
			sb.append(')');
		}
		return sb.toString();
	}
}
