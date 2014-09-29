/**
 * !(#) VisitorRegistry.java
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
 * Class/Interface VisitorRegistry.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class VisitorRegistry {

	private final Map<Class<? extends ASTNode>, AstVisitor<? extends ASTNode>> map = new HashMap<Class<? extends ASTNode>, AstVisitor<? extends ASTNode>>();

	public VisitorRegistry() {
	}

	public <T extends ASTNode> boolean add(Class<T> clazz, AstVisitor<T> visitor) {
		if (map.containsKey(clazz))
			return false;
		else {
			map.put(clazz, visitor);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode> AstVisitor<T> remove(Class<T> clazz) {
		return (AstVisitor<T>) map.remove(clazz);
	}

	public void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
	public <T extends ASTNode> AstVisitor<T> lookup(Class<T> nodeType) {
		return (AstVisitor<T>) map.get(nodeType);
	}
}
