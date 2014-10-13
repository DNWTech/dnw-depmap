/**
 * !(#) BlackOrWhite.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 11, 2014.
 */
package com.dnw.depmap.neo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class/Interface BlackOrWhite.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public final class BlackOrWhite {

	/**
	 * Class/Interface Checker.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 */
	public static final class Checker {

		private final boolean asBlack;

		/**
		 * Constructor of Checker.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param asBlack
		 */
		private Checker(boolean asBlack) {
			this.asBlack = asBlack;
		}

		private final Map<String, Pattern> map = new HashMap<String, Pattern>();

		/**
		 * Method add.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param regex
		 */
		public final void add(String regex) {
			Pattern p = Pattern.compile(regex);
			map.put(p.pattern(), p);
		}

		/**
		 * Method add.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param p
		 */
		public final void add(Pattern p) {
			map.put(p.pattern(), p);
		}

		/**
		 * Method remove.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param regex
		 */
		public final void remove(String regex) {
			Pattern p = Pattern.compile(regex);
			map.remove(p.pattern());
		}

		/**
		 * Method remove.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param p
		 */
		public final void remove(Pattern p) {
			map.remove(p.pattern());
		}

		/**
		 * Method anyoneMatches.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param text
		 * @return
		 */
		private final boolean anyoneMatches(String text) {
			for (Pattern p : map.values()) {
				Matcher m = p.matcher(text);
				if (m.matches())
					return true;
			}
			return false;
		}

		/**
		 * Method blocked.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param text
		 * @return
		 */
		public final boolean blocked(String text) {
			if (asBlack) {
				return anyoneMatches(text);
			} else {
				return !anyoneMatches(text);
			}
		}

		/**
		 * Method allowed.
		 * 
		 * @author manbaum
		 * @since Oct 11, 2014
		 * @param text
		 * @return
		 */
		public final boolean allowed(String text) {
			if (asBlack) {
				return !anyoneMatches(text);
			} else {
				return anyoneMatches(text);
			}
		}
	}

	public static final Checker BLACK = new Checker(true);
	public static final Checker WHITE = new Checker(false);

	/**
	 * Method blocked.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param text
	 * @return
	 */
	public static final boolean blocked(String text) {
		if (WHITE.allowed(text))
			return false;
		else
			return BLACK.blocked(text);
	}

	/**
	 * Method allowed.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param text
	 * @return
	 */
	public static final boolean allowed(String text) {
		if (WHITE.allowed(text))
			return true;
		else
			return BLACK.allowed(text);
	}
}
