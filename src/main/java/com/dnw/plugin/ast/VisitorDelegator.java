/**
 * !(#) VisitorDelegator.java
 * Copyright (c) 2014 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface VisitorDelegator.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public interface VisitorDelegator {

	boolean preVisit(ASTNode node);

	void postVisit(ASTNode node);

	<T extends ASTNode> boolean visit(Class<T> type, T node);

	<T extends ASTNode> void endVisit(Class<T> type, T node);
}
