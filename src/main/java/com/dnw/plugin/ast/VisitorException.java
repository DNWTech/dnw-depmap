/**
 * !(#) VisitorException.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 29, 2014.
 */
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Class/Interface VisitorException.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class VisitorException extends RuntimeException {

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
	public VisitorException(ASTNode node) {
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
	public VisitorException(ASTNode node, String message, Throwable cause) {
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
	public VisitorException(ASTNode node, String message) {
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
	public VisitorException(ASTNode node, Throwable cause) {
		super(cause);
		this.node = node;
	}
}
