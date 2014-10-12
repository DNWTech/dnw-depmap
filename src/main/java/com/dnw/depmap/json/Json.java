/**
 * !(#) Json.java
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
package com.dnw.depmap.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class/Interface Json.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public final class Json {

	private final static Pattern IDPATTERN = Pattern.compile("^[$_A-Za-z][0-9$_A-Za-z]*$");

	public final static String escape(final String value) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			final int n = value.codePointAt(i);
			final Character ch = value.charAt(i);
			if (n == 0)
				sb.append("\\0");
			else if (ch == '\n')
				sb.append("\\n");
			else if (ch == '\r')
				sb.append("\\r");
			else if (ch < 32) {
				sb.append("\\x");
				if (n < 16)
					sb.append('0');
				sb.append(Integer.toHexString(n));
			} else if (ch == '\\' || ch == '\'' || ch == '\"') {
				sb.append("\\");
				sb.append(ch);
			} else if (ch <= 126)
				sb.append(ch);
			else {
				sb.append("\\u");
				if (n < 16)
					sb.append("000");
				else if (n < 256)
					sb.append("00");
				else if (n < 4096)
					sb.append("0");
				sb.append(Integer.toHexString(n));
			}
		}
		return sb.toString();
	}

	public final static void emitString(final StringBuffer sb, final String value) {
		sb.append('\'');
		sb.append(Json.escape(value));
		sb.append('\'');
	}

	public final static void emitNumber(final StringBuffer sb, final Number value) {
		sb.append(String.valueOf(value));
	}

	public final static void emitOther(final StringBuffer sb, final Object value) {
		sb.append("\'[Object ");
		sb.append(value.getClass().getName());
		sb.append("]\'");
	}

	public final static void emitMap(final StringBuffer sb, final Map<?, ?> map) {
		sb.append('{');
		boolean first = true;
		for (final Map.Entry<?, ?> e : map.entrySet()) {
			if (first)
				first = false;
			else
				sb.append(',');
			sb.append('\'');
			Json.emitIdentifier(sb, (String)e.getKey());
			sb.append("\':");
			Json.emit(sb, e.getValue());
		}
		sb.append('}');
	}

	public final static void emitArray(final StringBuffer sb, final Object values) {
		sb.append('[');
		final int length = Array.getLength(values);
		for (int i = 0; i < length; i++) {
			if (i > 0)
				sb.append(',');
			Json.emit(sb, Array.get(values, i));
		}
		sb.append(']');
	}

	public final static void emitCollection(final StringBuffer sb, final Collection<?> values) {
		sb.append('[');
		boolean first = true;
		for (final Object v : values) {
			if (first)
				first = false;
			else
				sb.append(',');
			Json.emit(sb, v);
		}
		sb.append(']');
	}

	public final static void emitIdentifier(final StringBuffer sb, final String identifier) {
		final Matcher m = Json.IDPATTERN.matcher(identifier);
		if (!m.matches())
			throw new IllegalArgumentException("invalid.identifier");
		sb.append(identifier);
	}

	public final static void emit(final StringBuffer sb, final Object value) {
		if (value == null)
			sb.append("null");
		else if (value instanceof String)
			Json.emitString(sb, (String)value);
		else if (value instanceof Number)
			Json.emitNumber(sb, (Number)value);
		else if (value.getClass().isArray())
			Json.emitArray(sb, value);
		else if (value instanceof Map)
			Json.emitMap(sb, (Map<?, ?>)value);
		else if (value instanceof Collection)
			Json.emitCollection(sb, (Collection<?>)value);
		else
			Json.emitOther(sb, value);
	}
}
