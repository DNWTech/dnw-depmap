/**
 * !(#) UnknownAstNodeException.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface UnknownAstNodeException.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class UnknownAstNodeException extends RuntimeException {

	private static final long serialVersionUID = 8461798111118292221L;
	private final ASTNode node;

	/**
	 * Getter of the field node.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @return value of the field node.
	 */
	public ASTNode getNode() {
		return node;
	}

	/**
	 * Constructor of UnknownAstNodeException.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 */
	public UnknownAstNodeException(ASTNode node) {
		this.node = node;
	}

	/**
	 * Constructor of UnknownAstNodeException.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param message
	 * @param cause
	 */
	public UnknownAstNodeException(ASTNode node, String message, Throwable cause) {
		super(message, cause);
		this.node = node;
	}

	/**
	 * Constructor of UnknownAstNodeException.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param message
	 */
	public UnknownAstNodeException(ASTNode node, String message) {
		super(message);
		this.node = node;
	}

	/**
	 * Constructor of UnknownAstNodeException.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param cause
	 */
	public UnknownAstNodeException(ASTNode node, Throwable cause) {
		super(cause);
		this.node = node;
	}
}
