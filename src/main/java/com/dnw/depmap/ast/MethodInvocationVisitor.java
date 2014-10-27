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
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.ast.IVisitor;
import com.dnw.plugin.ast.VisitContext;

/**
 * Visitor to visit <code>MethodInvocation</code> AST node, generating the corresponding method
 * invocation nodes and relations.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class MethodInvocationVisitor implements IVisitor<MethodInvocation> {

	/**
	 * The method will be called when a <code>MethodInvocation</code> node is met.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param node the <code>MethodInvocation</code> node.
	 * @param context the visiting context.
	 * @see com.dnw.plugin.ast.IVisitor#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      com.dnw.plugin.ast.VisitContext)
	 */
	@Override
	public void visit(MethodInvocation node, VisitContext context) {
		Activator.console.println(" -- Visit MethodInvocation: " + node.toString());
		// find the containing method of this method invocation.
		// TODO: what if a method invocation not happens in a method? i.e. during class initialization.
		ASTNode p = node.getParent();
		while (p != null) {
			if (p instanceof MethodDeclaration) {
				break;
			} else {
				p = p.getParent();
			}
		}
		MethodDeclaration decl = (MethodDeclaration)p;
		Activator.console.println("  . Method is called from: " + make(decl));
		if (p == null)
			return;

		IMethodBinding from = decl.resolveBinding();
		Activator.console.println(AstUtil.infoOf(context, decl, from));
		IMethodBinding to = node.resolveMethodBinding();
		Activator.console.println(AstUtil.infoOf(context, node, to));
		// call DAO to generate the method node and its related relationships.
		Activator.neo().createInvocation(from, to);
	}

	/**
	 * Makes the string representation of all arguments passed by this method invocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param list a list of <code>Expression</code>.
	 * @return a string list contains all arguments passed by this method invocation.
	 */
	private List<Object> args(List<?> list) {
		int n = list.size();
		List<Object> r = new ArrayList<Object>(n);
		for (int i = 0; i < n; i++) {
			Expression e = (Expression)list.get(i);
			r.add(e.toString());
		}
		return r;
	}

	/**
	 * Makes the string representation of this method invocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param node the <code>MethodInvocation</code> node.
	 * @return the string representation.
	 */
	private String make(MethodDeclaration node) {
		StringBuffer sb = new StringBuffer();
		if (node != null) {
			sb.append(node.getReturnType2());
			sb.append(' ');
			sb.append(node.getName());
			sb.append('(');
			for (Object v : node.parameters()) {
				SingleVariableDeclaration d = (SingleVariableDeclaration)v;
				sb.append(d.getType());
			}
			sb.append(')');
		}
		return sb.toString();
	}
}
