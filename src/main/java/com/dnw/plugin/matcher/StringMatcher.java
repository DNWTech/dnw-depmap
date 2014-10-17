/**
 * !(#) StringMatcher.java
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
 * Class/Interface StringMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class StringMatcher implements IMatcher<String> {

	private final String target;
	private final boolean ignoreCase;

	/**
	 * Constructor of StringMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param target
	 */
	public StringMatcher(String target) {
		this.target = target;
		this.ignoreCase = false;
	}

	/**
	 * Constructor of StringMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param target
	 * @param ignoreCase
	 */
	public StringMatcher(String target, boolean ignoreCase) {
		this.target = target;
		this.ignoreCase = ignoreCase;
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
		if (target == value)
			return true;
		else if (target == null)
			return false;
		else
			return ignoreCase ? target.equalsIgnoreCase(value) : target.equals(value);
	}
}
