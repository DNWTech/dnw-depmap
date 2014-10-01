/**
 * !(#) VisitorRegistry.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 30, 2014.
 */
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface VisitorRegistry.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public interface VisitorRegistry {

	/**
	 * Method add.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param type
	 * @param visitor
	 * @return
	 */
	<T extends ASTNode, V extends Visitor<T>> boolean add(Class<T> type,
			V visitor);

	/**
	 * Method remove.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param type
	 * @return
	 */
	<T extends ASTNode, V extends Visitor<T>> V remove(Class<T> type);

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	void clear();

	/**
	 * Method lookup.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param type
	 * @return
	 */
	<T extends ASTNode, V extends Visitor<T>> V lookup(Class<T> type);
}
