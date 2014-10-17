/**
 * !(#) StringSetMatcher.java
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

/**
 * Class/Interface StringSetMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class StringSetMatcher extends AbstractSetBasedMatcher<String, String> {

	private final boolean ignoreCase;

	/**
	 * Constructor of StringSetMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public StringSetMatcher() {
		ignoreCase = false;
	}

	/**
	 * Constructor of StringSetMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param ignoreCase
	 */
	public StringSetMatcher(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Overrider method decorates.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 * @see com.dnw.plugin.matcher.AbstractSetBasedMatcher#decorates(java.lang.Object)
	 */
	@Override
	protected String decorates(String value) {
		if (value == null)
			return value;
		else
			return ignoreCase ? value.toLowerCase() : value;
	}
}
