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

public final class BlackOrWhite {

	public static final class Checker {

		private final boolean asBlack;

		private Checker(boolean asBlack) {
			this.asBlack = asBlack;
		}

		private final Map<String, Pattern> map = new HashMap<String, Pattern>();

		public final void add(String regex) {
			Pattern p = Pattern.compile(regex);
			map.put(p.pattern(), p);
		}

		public final void add(Pattern p) {
			map.put(p.pattern(), p);
		}

		public final void remove(String regex) {
			Pattern p = Pattern.compile(regex);
			map.remove(p.pattern());
		}

		public final void remove(Pattern p) {
			map.remove(p.pattern());
		}

		private final boolean anyoneMatches(String text) {
			for (Pattern p : map.values()) {
				Matcher m = p.matcher(text);
				if (m.matches())
					return true;
			}
			return false;
		}

		public final boolean blocked(String text) {
			if (asBlack) {
				return anyoneMatches(text);
			} else {
				return !anyoneMatches(text);
			}
		}

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

	public static final boolean blocked(String text) {
		if (WHITE.allowed(text))
			return false;
		else
			return BLACK.blocked(text);
	}

	public static final boolean allowed(String text) {
		if (WHITE.allowed(text))
			return true;
		else
			return BLACK.allowed(text);
	}
}
