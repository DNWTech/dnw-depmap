/**
 * !(#) NodeTypeSet.java
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

/**
 * A node type set holds a set of AST node type value. Currently, we use bitmap as the internal
 * storage approach.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class NodeTypeSet {

	private long n0 = 0L;
	private long n1 = 0L;

	/**
	 * Checks whether the given AST node type value is valid.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type an integer value identifying the type of a concrete AST node.
	 * @return the original AST node type value.
	 * @throws IllegalArgumentException if the given AST node type value is invalid.
	 */
	protected int check(int type) {
		if (type < 0 || type > 127)
			throw new IllegalArgumentException("invalid.ASTNode.type: " + type);
		return type;
	}

	/**
	 * Calculates the bitmap mask of the given AST node type value.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param n an AST node type value.
	 * @return the mask of AST node type value.
	 */
	private long mask(int n) {
		return 1L << n;
	}

	/**
	 * Adds the given AST node type into this set.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type an integer value identifying the type of a concrete AST node.
	 */
	public void add(int type) {
		int n = check(type);
		if (n >= 64) {
			n1 |= mask(n - 64);
		} else {
			n0 |= mask(n);
		}
	}

	/**
	 * Removes the given AST node type from this set.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type an integer value identifying the type of a concrete AST node.
	 */
	public void remove(int type) {
		int n = check(type);
		if (n >= 64) {
			n1 &= ~mask(n - 64);
		} else {
			n0 &= ~mask(n);
		}
	}

	/**
	 * Clears this set, wipes off all previous stored AST node types.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public void clear() {
		n1 = n0 = 0L;
	}

	/**
	 * Returns whether this set contains the given AST node type.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param type an integer value identifying the type of a concrete AST node.
	 * @return <code>true</code> if this set contains the given type, or <code>false</code> if it
	 *         doesn't.
	 */
	public boolean contains(int type) {
		int n = check(type);
		return n >= 64 ? (n1 & mask(n - 64)) != 0L : (n0 & mask(n)) != 0L;
	}
}
