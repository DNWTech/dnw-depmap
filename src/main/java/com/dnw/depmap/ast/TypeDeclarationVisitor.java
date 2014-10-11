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
import com.dnw.plugin.ast.Visitor;

/**
 * Class/Interface TypeDeclarationVisitor.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 * 
 */
public class TypeDeclarationVisitor implements Visitor<TypeDeclaration> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * 
	 * @param node
	 * 
	 * @see com.dnw.plugin.ast.Visitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	@Override
	public void visit(TypeDeclaration node) {
		Activator.console.println(" -- Visit TypeDeclaration: "
				+ node.getName());
		ITypeBinding type = node.resolveBinding();
		Teller.tellTypeDeclaration(node, type);
		Activator.w().createType(type);
	}
}
