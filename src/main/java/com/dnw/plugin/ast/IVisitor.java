/**
 * !(#) IVisitor.java
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
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A visitor can visit the given type of AST node.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * @param <T> the given type.
 */
public interface IVisitor<T extends ASTNode> {

	/**
	 * This method will be called when the given type of AST node is met.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param node the given type of AST node.
	 * @param context the visiting context.
	 */
	void visit(T node, VisitContext context);
}
