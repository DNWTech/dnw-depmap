/**
 * !(#) RegexMatcher.java
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

import java.util.regex.Pattern;

/**
 * Class/Interface RegexMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class RegexMatcher implements IMatcher<String> {

	private final Pattern pattern;

	/**
	 * Constructor of RegexMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 */
	public RegexMatcher(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Constructor of RegexMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 */
	public RegexMatcher(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	/**
	 * Constructor of RegexMatcher.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 * @param flags
	 */
	public RegexMatcher(String pattern, int flags) {
		this.pattern = Pattern.compile(pattern, flags);
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
	public boolean matches(String value) {
		return pattern == null ? false : pattern.matcher(value).matches();
	}
}
