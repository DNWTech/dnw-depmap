/**
 * !(#) DefaultVisitorRegistry.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

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

	private final Map<Class<? extends ASTNode>, Visitor<? extends ASTNode>> map = new HashMap<Class<? extends ASTNode>, Visitor<? extends ASTNode>>();

	public DefaultVisitorRegistry() {
	}

	public <T extends ASTNode> boolean add(Class<T> type, Visitor<T> visitor) {
		if (map.containsKey(type))
			return false;
		else {
			map.put(type, visitor);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode> Visitor<T> remove(Class<T> type) {
		return (Visitor<T>) map.remove(type);
	}

	public void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode> Visitor<T> lookup(Class<T> type) {
		return (Visitor<T>) map.get(type);
	}
}
