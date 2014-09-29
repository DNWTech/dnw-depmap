/**
 * !(#) CompilationUnitVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast.visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.bocnet.plugin.ast.AstVisitor;
import com.bocnet.plugin.ast.VisitContext;
import com.bocnet.plugin.ast.VisitorRegistry;

/**
 * Class/Interface CompilationUnitVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class CompilationUnitVisitor implements AstVisitor<CompilationUnit> {

	private final VisitorRegistry registry;

	public CompilationUnitVisitor(VisitorRegistry registry) {
		this.registry = registry;
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
	 * @see com.bocnet.plugin.ast.AstVisitor#visit(org.eclipse.jdt.core.dom.ASTNode)
	 */
	public boolean visit(CompilationUnit node, VisitContext context) {
		AstVisitor<CompilationUnit> visitor = registry
				.lookup(CompilationUnit.class);
		if (visitor != null) {
			boolean shouldDigInto = visitor.visit(node, context);
			if (shouldDigInto) {
				@SuppressWarnings("unchecked")
				List<AbstractTypeDeclaration> declarations = node.types();
				for (AbstractTypeDeclaration declaration : declarations) {
				}
			}
		}
		return false;
	}
}
