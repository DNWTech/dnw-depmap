/**
 * !(#) AbstractSetBasedMatcher.java
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
package com.dnw.plugin.matcher;

import java.util.HashSet;
import java.util.Set;

/**
 * Class/Interface AbstractSetBasedMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public abstract class AbstractSetBasedMatcher<S, T> implements IMatcher<S> {

	protected final Set<T> set = new HashSet<T>();

	/**
	 * Method decorate.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 */
	protected abstract T decorates(S value);

	/**
	 * Method addCandidate.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param candidate
	 */
	public void addCandidate(S candidate) {
		set.add(decorates(candidate));
	}

	/**
	 * Method removeCandidate.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param candidate
	 */
	public void removeCandidate(S candidate) {
		set.remove(decorates(candidate));
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
	 * @see com.dnw.plugin.matcher.IMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(S value) {
		return set.contains(decorates(value));
	}
}
