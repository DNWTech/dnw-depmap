package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

public interface Visitor<T extends ASTNode> {

	void visit(T node);
}
