/**
 * !(#) TypeDeclarationVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast.visitor;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.bocnet.plugin.ast.AstVisitor;
import com.bocnet.plugin.ast.VisitContext;

/**
 * Class/Interface TypeDeclarationVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class TypeDeclarationVisitor implements AstVisitor<TypeDeclaration> {

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 * @param shouldDigInto
	 * @return
	 * 
	 * @see com.bocnet.plugin.ast.AstVisitor#visit(org.eclipse.jdt.core.dom.ASTNode,
	 *      boolean)
	 */
	public boolean visit(TypeDeclaration node, VisitContext context) {
		// TODO Auto-generated method stub
		return false;
	}
}
