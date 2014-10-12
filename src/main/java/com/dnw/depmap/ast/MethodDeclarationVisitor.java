/**
 * !(#) MethodDeclarationVisitor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 10, 2014.
 */
package com.dnw.depmap.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.Visitor;

/**
 * Class/Interface MethodDeclarationVisitor.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class MethodDeclarationVisitor implements Visitor<MethodDeclaration> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param node
	 * @see com.dnw.plugin.ast.Visitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	@Override
	public void visit(MethodDeclaration node) {
		Activator.console.println(" -- Visit MethodDeclaration: " + make(node));
		IMethodBinding method = node.resolveBinding();
		Teller.tellMethodDeclaration(node, method);
		Activator.w().createMethod(method);
	}

	private String make(MethodDeclaration node) {
		StringBuffer sb = new StringBuffer();
		sb.append(node.getReturnType2());
		sb.append(' ');
		sb.append(node.getName());
		sb.append('(');
		for (Object v : node.parameters()) {
			SingleVariableDeclaration d = (SingleVariableDeclaration)v;
			sb.append(d.getType());
		}
		sb.append(')');
		return sb.toString();
	}
}
