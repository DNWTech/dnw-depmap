/**
 * !(#) SetMatcher.java
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

import java.util.HashSet;
import java.util.Set;

/**
 * Class/Interface SetMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class SetMatcher<T> implements IMatcher<T> {

	private final Set<IMatcher<T>> set = new HashSet<IMatcher<T>>();

	/**
	 * Method addMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addMatcher(IMatcher<T> m) {
		set.add(m);
	}

	/**
	 * Method removeMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeMatcher(IMatcher<T> m) {
		set.remove(m);
	}

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clear() {
		set.clear();
	}

	/**
	 * Overrider method matches.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.matcher.IMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T value) {
		for (IMatcher<T> m : set) {
			if (m.matches(value))
				return true;
		}
		return false;
	}
}
