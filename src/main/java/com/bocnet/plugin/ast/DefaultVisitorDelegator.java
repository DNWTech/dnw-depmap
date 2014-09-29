package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

public final class DefaultVisitorDelegator implements VisitorDelegator {

	private final NodeTypeSet stopSet;
	private final VisitorRegistry registry;

	public DefaultVisitorDelegator(NodeTypeSet stopSet, VisitorRegistry registry) {
		this.stopSet = stopSet;
		this.registry = registry;
	}

	public boolean preVisit(ASTNode node) {
		return !stopSet.contains(node.getNodeType());
	}

	public void postVisit(ASTNode node) {
	}

	public <T extends ASTNode> boolean visit(Class<T> type, T node) {
		Visitor<T> visitor = registry.lookup(type);
		visitor.visit(node);
		return true;
	}

	public <T extends ASTNode> void endVisit(Class<T> type, T node) {
	}
}
