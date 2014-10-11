/**
 * !(#) JSON.java
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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class/Interface JSON.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 * 
 */
public class JSON {

	private final static Pattern IDPATTERN = Pattern
			.compile("^[$_A-Za-z][0-9$_A-Za-z]*$");

	public final static String escape(String value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			int n = value.codePointAt(i);
			Character ch = value.charAt(i);
			if (n == 0) {
				sb.append("\\0");
			} else if (ch == '\n') {
				sb.append("\\n");
			} else if (ch == '\r') {
				sb.append("\\r");
			} else if (ch < 32) {
				sb.append("\\x");
				if (n < 16) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(n));
			} else if (ch == '\\' || ch == '\'' || ch == '\"') {
				sb.append("\\");
				sb.append(ch);
			} else if (ch <= 126) {
				sb.append(ch);
			} else {
				sb.append("\\u");
				if (n < 16) {
					sb.append("000");
				} else if (n < 256) {
					sb.append("00");
				} else if (n < 4096) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(n));
			}
		}
		return sb.toString();
	}

	public final static void emitString(StringBuffer sb, String value) {
		sb.append('\'');
		sb.append(escape(value));
		sb.append('\'');
	}

	public final static void emitNumber(StringBuffer sb, Number value) {
		sb.append(String.valueOf(value));
	}

	public final static void emitOther(StringBuffer sb, Object value) {
		sb.append("\'[Object ");
		sb.append(value.getClass().getName());
		sb.append("]\'");
	}

	public final static void emitMap(StringBuffer sb, Map<?, ?> map) {
		sb.append('{');
		boolean first = true;
		for (Map.Entry<?, ?> e : map.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append('\'');
			emitIdentifier(sb, (String) e.getKey());
			sb.append("\':");
			emit(sb, e.getValue());
		}
		sb.append('}');
	}

	public final static void emitArray(StringBuffer sb, Object values) {
		sb.append('[');
		int length = Array.getLength(values);
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				sb.append(',');
			}
			emit(sb, Array.get(values, i));
		}
		sb.append(']');
	}

	public final static void emitCollection(StringBuffer sb,
			Collection<?> values) {
		sb.append('[');
		boolean first = true;
		for (Object v : values) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			emit(sb, v);
		}
		sb.append(']');
	}

	public final static void emitIdentifier(StringBuffer sb, String identifier) {
		Matcher m = IDPATTERN.matcher(identifier);
		if (!m.matches())
			throw new IllegalArgumentException("invalid.identifier");
		sb.append(identifier);
	}

	public final static void emit(StringBuffer sb, Object value) {
		if (value == null) {
			sb.append("null");
		} else if (value instanceof String) {
			emitString(sb, (String) value);
		} else if (value instanceof Number) {
			emitNumber(sb, (Number) value);
		} else if (value.getClass().isArray()) {
			emitArray(sb, value);
		} else if (value instanceof Map) {
			emitMap(sb, (Map<?, ?>) value);
		} else if (value instanceof Collection) {
			emitCollection(sb, (Collection<?>) value);
		} else {
			emitOther(sb, value);
		}
	}
}
