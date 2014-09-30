package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

public interface VisitorRegistry {

	<T extends ASTNode, V extends Visitor<T>> boolean add(Class<T> type,
			V visitor);

	<T extends ASTNode, V extends Visitor<T>> V remove(Class<T> type);

	void clear();

	<T extends ASTNode, V extends Visitor<T>> V lookup(Class<T> type);
}
