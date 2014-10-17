/**
 * !(#) RegexSetMatcher.java
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class/Interface RegexSetMatcher.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public class RegexSetMatcher implements IMatcher<String> {

	private final Set<Pattern> patterns = new HashSet<Pattern>();

	/**
	 * Method addPattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param p
	 */
	public void addPattern(Pattern p) {
		patterns.add(p);
	}

	/**
	 * Method addPattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 */
	public void addPattern(String pattern) {
		patterns.add(Pattern.compile(pattern));
	}

	/**
	 * Method addPattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 * @param flags
	 */
	public void addPattern(String pattern, int flags) {
		patterns.add(Pattern.compile(pattern, flags));
	}

	/**
	 * Method removePattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param p
	 */
	public void removePattern(Pattern p) {
		patterns.remove(p);
	}

	/**
	 * Method removePattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 */
	public void removePattern(String pattern) {
		patterns.remove(Pattern.compile(pattern));
	}

	/**
	 * Method removePattern.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param pattern
	 * @param flags
	 */
	public void removePattern(String pattern, int flags) {
		patterns.remove(Pattern.compile(pattern, flags));
	}

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clear() {
		patterns.clear();
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
		for (Pattern p : patterns) {
			Matcher m = p.matcher(value);
			if (m.matches())
				return true;
		}
		return false;
	}
}
