/**
 * !(#) AstVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface AstVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public interface AstVisitor<N extends ASTNode> {

	boolean visit(N node, VisitContext context);
}
