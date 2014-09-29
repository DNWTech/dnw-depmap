package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

public interface VisitorRegistry {

	<T extends ASTNode> boolean add(Class<T> type, Visitor<T> visitor);

	<T extends ASTNode> Visitor<T> remove(Class<T> type);

	void clear();

	<T extends ASTNode> Visitor<T> lookup(Class<T> type);
}
