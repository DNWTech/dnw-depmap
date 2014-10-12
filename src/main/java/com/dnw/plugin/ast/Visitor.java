/**
 * !(#) Visitor.java
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
 * Class/Interface Visitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * @param <T>
 */
public interface Visitor<T extends ASTNode> {

	/**
	 * Method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param node
	 */
	void visit(T node);
}
