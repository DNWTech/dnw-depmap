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
public class CompositeList<T> implements IFilterService<T> {

	private final ListMatcher<T> white = new ListMatcher<T>();
	private final ListMatcher<T> black = new ListMatcher<T>();

	// true 白名单优先，是反逻辑；false 黑名单优先，是正逻辑。
	// 正逻辑时白名单有才让过，白名单没有，则不让过。此时的黑名单优先，相当于临时把白名单里有的抠掉。
	// 反逻辑时黑名单有就不让过，黑名单没有，则让过。此时的白名单优先，相当于临时把黑名单里有的抠掉。
	// 正反逻辑最大区别在于，黑白名单都没有时是让过还是不让过：正逻辑是不让过，反逻辑是让过。
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
	 * Overrider method allows.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.matcher.IFilterService#allows(java.lang.Object)
	 */
	@Override
	public boolean allows(T value) {
		if (preferWhite) {
			if (white.matches(value))
				// 只要白名单有，不管黑白名单有没有，让过。
				return true;
			else
				// 黑白名单都没有，让过。仅黑名单有，不让过。
				return !black.matches(value);
		} else {
			if (black.matches(value))
				// 只要黑名单有，不管白名单有没有，不让过。
				return false;
			else
				// 仅白名单有，让过。黑白名单都没有，不让过。
				return white.matches(value);
		}
	}

	/**
	 * Overrider method blocks.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.matcher.IFilterService#blocks(java.lang.Object)
	 */
	@Override
	public boolean blocks(T value) {
		return !allows(value);
	}
}
