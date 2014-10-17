/**
 * !(#) CompositeList.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 17, 2014.
 */
package com.dnw.matcher;

/**
 * Class/Interface CompositeList.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class CompositeList<T> implements IListService<T> {

	private final WhiteList<T> white = new WhiteList<T>();
	private final BlackList<T> black = new BlackList<T>();
	private final boolean preferWhite;

	/**
	 * Constructor of CompositeList.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public CompositeList() {
		preferWhite = true;
	}

	/**
	 * Constructor of CompositeList.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param preferWhite
	 */
	public CompositeList(boolean preferWhite) {
		this.preferWhite = preferWhite;
	}

	/**
	 * Method addAllowMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addAllowMatcher(IMatcher<T> m) {
		white.addMatcher(m);
	}

	/**
	 * Method removeAllowMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeAllowMatcher(IMatcher<T> m) {
		white.removeMatcher(m);
	}

	/**
	 * Method clearAllows.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clearAllows() {
		white.clear();
	}

	/**
	 * Method addBlockMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addBlockMatcher(IMatcher<T> m) {
		black.addMatcher(m);
	}

	/**
	 * Method removeBlockMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeBlockMatcher(IMatcher<T> m) {
		black.removeMatcher(m);
	}

	/**
	 * Method clearBlocks.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clearBlocks() {
		black.clear();
	}

	/**
	 * Overrider method blocks.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.matcher.IListService#blocks(java.lang.Object)
	 */
	@Override
	public boolean blocks(T value) {
		if (preferWhite) {
			if (white.allows(value))
				return false;
			else
				return black.blocks(value);
		} else {
			if (black.blocks(value))
				return true;
			else
				return white.blocks(value);
		}
	}

	/**
	 * Overrider method allows.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.matcher.IListService#allows(java.lang.Object)
	 */
	@Override
	public boolean allows(T value) {
		if (preferWhite) {
			if (white.allows(value))
				return true;
			else
				return black.allows(value);
		} else {
			if (black.blocks(value))
				return false;
			else
				return white.allows(value);
		}
	}
}
