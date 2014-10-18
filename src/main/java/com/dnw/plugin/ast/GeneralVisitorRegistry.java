/**
 * !(#) GeneralVisitorRegistry.java
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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface GeneralVisitorRegistry.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public final class GeneralVisitorRegistry implements IVisitorRegistry {

	private final Map<Class<?>, IVisitor<?>> map = new HashMap<Class<?>, IVisitor<?>>();

	/**
	 * Constructor of GeneralVisitorRegistry.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public GeneralVisitorRegistry() {
	}

	/**
	 * Overrider method add.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type
	 * @param visitor
	 * @return
	 * @see com.dnw.plugin.ast.IVisitorRegistry#add(java.lang.Class, com.dnw.plugin.ast.IVisitor)
	 */
	public <T extends ASTNode, V extends IVisitor<T>> boolean add(Class<T> type, V visitor) {
		if (map.containsKey(type))
			return false;
		else {
			map.put(type, visitor);
			return true;
		}
	}

	/**
	 * Overrider method remove.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type
	 * @return
	 * @see com.dnw.plugin.ast.IVisitorRegistry#remove(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends IVisitor<T>> V remove(Class<T> type) {
		return (V)map.remove(type);
	}

	/**
	 * Overrider method clear.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @see com.dnw.plugin.ast.IVisitorRegistry#clear()
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Overrider method lookup.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type
	 * @return
	 * @see com.dnw.plugin.ast.IVisitorRegistry#lookup(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends IVisitor<T>> V lookup(Class<T> type) {
		return (V)map.get(type);
	}
}
