/**
 * !(#) WhiteList.java
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
 * Class/Interface WhiteList.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class WhiteList<T> implements IFilterService<T> {

	private final ListMatcher<T> list = new ListMatcher<T>();

	/**
	 * Method addMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addMatcher(IMatcher<T> m) {
		list.addMatcher(m);
	}

	/**
	 * Method addMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param i
	 * @param m
	 */
	public void addMatcher(int i, IMatcher<T> m) {
		list.addMatcher(i, m);
	}

	/**
	 * Method removeMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeMatcher(IMatcher<T> m) {
		list.removeMatcher(m);
	}

	/**
	 * Method removeMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param i
	 */
	public void removeMatcher(int i) {
		list.removeMatcher(i);
	}

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clear() {
		list.clear();
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
		return list.matches(value);
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
