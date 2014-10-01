/**
 * !(#) DefaultVisitorRegistry.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
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
 * 
 */
public final class DefaultVisitorRegistry implements VisitorRegistry {

	private final Map<Class<?>, Visitor<?>> map = new HashMap<Class<?>, Visitor<?>>();

	public DefaultVisitorRegistry() {
	}

	public <T extends ASTNode, V extends Visitor<T>> boolean add(Class<T> type,
			V visitor) {
		if (map.containsKey(type))
			return false;
		else {
			map.put(type, visitor);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends Visitor<T>> V remove(Class<T> type) {
		return (V) map.remove(type);
	}

	public void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode, V extends Visitor<T>> V lookup(Class<T> type) {
		return (V) map.get(type);
	}
}
