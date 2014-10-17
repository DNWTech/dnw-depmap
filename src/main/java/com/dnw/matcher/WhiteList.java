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

import java.util.ArrayList;
import java.util.List;

/**
 * Class/Interface WhiteList.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class WhiteList<T> implements IListService<T> {

	private final List<IMatcher<T>> list = new ArrayList<IMatcher<T>>();

	/**
	 * Method addMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addMatcher(IMatcher<T> m) {
		list.add(m);
	}

	/**
	 * Method removeMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeMatcher(IMatcher<T> m) {
		list.remove(m);
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
		for (int i = list.size() - 1; i > 0; i--)
			if (list.get(i).matches(value))
				return false;
		return true;
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
		for (int i = list.size() - 1; i > 0; i--)
			if (list.get(i).matches(value))
				return true;
		return false;
	}
}
