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

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.ast.IVisitor;
import com.dnw.plugin.ast.VisitContext;

/**
 * Class/Interface TypeDeclarationVisitor.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class TypeDeclarationVisitor implements IVisitor<TypeDeclaration> {

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
	public void visit(TypeDeclaration node, VisitContext context) {
		Activator.console.println(" -- Visit TypeDeclaration: " + node.getName());
		ITypeBinding type = node.resolveBinding();
		Activator.console.println(AstUtil.infoOf(context, node, type));
		Activator.neo().createType(type);
	}
}
