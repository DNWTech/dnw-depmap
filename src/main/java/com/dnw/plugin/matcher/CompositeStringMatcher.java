/**
 * !(#) CompositeStringMatcher.java
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
 * Class/Interface CompositeStringMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class CompositeStringMatcher implements IMatcher<String> {

	private final Set<IMatcher<String>> set = new HashSet<IMatcher<String>>();

	/**
	 * Method addMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void addMatcher(IMatcher<String> m) {
		set.add(m);
	}

	/**
	 * Method removeMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param m
	 */
	public void removeMatcher(IMatcher<String> m) {
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
	 * @see com.dnw.plugin.matcher.IMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(String value) {
		for (IMatcher<String> m : set) {
			if (m.matches(value))
				return true;
		}
		return false;
	}
}
