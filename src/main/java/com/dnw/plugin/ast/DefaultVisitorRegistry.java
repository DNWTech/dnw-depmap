/**
 * !(#) DefaultVisitorRegistry.java
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
 * Class/Interface DefaultVisitorRegistry.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public final class DefaultVisitorRegistry implements VisitorRegistry {

	private final Map<Class<?>, Visitor<?>> map = new HashMap<Class<?>, Visitor<?>>();

	/**
	 * Constructor of DefaultVisitorRegistry.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public DefaultVisitorRegistry() {
	}

	/**
	 * Overrider method add.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type
	 * @param visitor
	 * @return
	 * @see com.dnw.plugin.ast.VisitorRegistry#add(java.lang.Class, com.dnw.plugin.ast.Visitor)
	 */
	public <T extends ASTNode, V extends Visitor<T>> boolean add(Class<T> type, V visitor) {
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
	 * @see com.dnw.plugin.ast.VisitorRegistry#remove(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends Visitor<T>> V remove(Class<T> type) {
		return (V)map.remove(type);
	}

	/**
	 * Overrider method clear.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @see com.dnw.plugin.ast.VisitorRegistry#clear()
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
	 * @see com.dnw.plugin.ast.VisitorRegistry#lookup(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends Visitor<T>> V lookup(Class<T> type) {
		return (V)map.get(type);
	}
}
