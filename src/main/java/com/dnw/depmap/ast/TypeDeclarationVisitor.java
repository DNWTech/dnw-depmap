/**
 * !(#) TypeDeclarationVisitor.java
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
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.ast.IVisitor;
import com.dnw.plugin.ast.VisitContext;

/**
 * Visitor to visit <code>TypeDeclaration</code> AST node, generating the corresponding method
 * declaration nodes and relations.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class TypeDeclarationVisitor implements IVisitor<TypeDeclaration> {

	/**
	 * The method will be called when a <code>TypeDeclaration</code> node is met.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param node the <code>TypeDeclaration</code> node.
	 * @param context the visiting context.
	 * @see com.dnw.plugin.ast.IVisitor#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      com.dnw.plugin.ast.VisitContext)
	 */
	@Override
	public void visit(TypeDeclaration node, VisitContext context) {
		Activator.getDefault().console.println(" -- Visit TypeDeclaration: " + node.getName());
		ITypeBinding type = node.resolveBinding();
		Activator.getDefault().console.println(AstUtil.infoOf(context, node, type));
		// call DAO to generate the method node and its related relationships.
		Activator.neo().createType(type, context.file.getFullPath().toPortableString(),
				context.lineOf(node));

		@SuppressWarnings("unchecked") List<IExtendedModifier> modifiers = node.modifiers();
		for (IExtendedModifier m : modifiers) {
			if (m.isAnnotation()) {
				Annotation a = (Annotation)m;
				Activator.getDefault().console.println(" -- Annotation Found: " + a.toString());
				// to generate the annotation node and its relationship.
				Activator.neo().createTypeAnnotation(type, a.resolveTypeBinding(),
						context.file.getFullPath().toPortableString(), context.lineOf(a));
			}
		}
	}
}
