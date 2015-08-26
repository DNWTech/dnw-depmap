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

import java.util.List;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.ast.IVisitor;
import com.dnw.plugin.ast.VisitContext;

/**
 * Visitor to visit <code>MethodDeclaration</code> AST node, generating the corresponding method
 * declaration nodes and relations.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class MethodDeclarationVisitor implements IVisitor<MethodDeclaration> {

	/**
	 * The method will be called when a <code>MethodDeclaration</code> node is met.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param node the <code>MethodDeclaration</code> node.
	 * @param context the visiting context.
	 * @see com.dnw.plugin.ast.IVisitor#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      com.dnw.plugin.ast.VisitContext)
	 */
	@Override
	public void visit(MethodDeclaration node, VisitContext context) {
		Activator.console.println(" -- Visit MethodDeclaration: " + make(node));
		IMethodBinding method = node.resolveBinding();
		Activator.console.println(AstUtil.infoOf(context, node, method));
		// call DAO to generate the method node and its related relationships.
		Activator.neo().createMethod(method, context.file.getFullPath().toPortableString(),
				context.lineOf(node));

		@SuppressWarnings("unchecked") List<IExtendedModifier> modifiers = node.modifiers();
		for (IExtendedModifier m : modifiers) {
			if (m.isAnnotation()) {
				Annotation a = (Annotation)m;
				Activator.console.println(" -- Annotation Found: " + a.toString());
				// to generate the annotation node and its relationship.
				Activator.neo().createMethodAnnotation(method, a.resolveTypeBinding(),
						context.file.getFullPath().toPortableString(), context.lineOf(a));
			}
		}
	}

	/**
	 * Makes a string representation of the <code>MethodDeclaration</code> node.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node the <code>MethodDeclaration</code> node.
	 * @return the string representation.
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
