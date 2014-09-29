/**
 * !(#) VisitContext.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface VisitContext.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public interface VisitContext {

	boolean isInterestedIn(int type);

	boolean shouldStopAt(int type);

	void add(ASTNode node);
}
