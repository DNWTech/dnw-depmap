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
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.ast.IVisitor;
import com.dnw.plugin.ast.VisitContext;

/**
 * Class/Interface MethodDeclarationVisitor.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class MethodDeclarationVisitor implements IVisitor<MethodDeclaration> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param node
	 * @param context
	 * @see com.dnw.plugin.ast.IVisitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	@Override
	public void visit(MethodDeclaration node, VisitContext context) {
		Activator.console.println(" -- Visit MethodDeclaration: " + make(node));
		IMethodBinding method = node.resolveBinding();
		Activator.console.println(AstUtil.infoOf(context, node, method));
		Activator.neo().createMethod(method);
	}

	/**
	 * Method make.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node
	 * @return
	 */
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
