/**
 * !(#) V.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class L {

	final List<Object> list;

	L() {
		list = new ArrayList<Object>();
	}

	@SuppressWarnings("unchecked")
	L(List<?> list) {
		this.list = (List<Object>)list;
	}

	public static final L l() {
		return new L();
	}

	/**
	 * Method json.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return
	 */
	public final String json() {
		StringBuffer sb = new StringBuffer();
		Json.emit(sb, this);
		return sb.toString();
	}

	public final List<Object> list() {
		return list;
	}

	public final Object[] array() {
		return list.toArray();
	}

	public final L a(Object value) {
		list.add(Converter.convert(value));
		return this;
	}

	public final L aa(Object... values) {
		for (Object v : values) {
			list.add(Converter.convert(v));
		}
		return this;
	}

	public final L aa(Collection<?> src) {
		for (Object v : src) {
			list.add(Converter.convert(v));
		}
		return this;
	}

	public final L aa(L src) {
		list.addAll(src.list);
		return this;
	}

	public final L cp(int start, int len, Object[] src) {
		for (int i = start; i < len; i++) {
			list.add(Converter.convert(src[i]));
		}
		return this;
	}

	public final L cp(int start, int len, List<?> src) {
		for (int i = start; i < len; i++) {
			list.add(Converter.convert(src.get(i)));
		}
		return this;
	}

	public final L cp(int start, int len, L src) {
		for (int i = start; i < len; i++) {
			list.add(src.list.get(i));
		}
		return this;
	}

	public final L rm(int i) {
		list.remove(i);
		return this;
	}

	public final L clear() {
		list.clear();
		return this;
	}

	public final int length() {
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public final <T> T v(int i) {
		return (T)list.get(i);
	}

	@SuppressWarnings("unchecked")
	public final M vm(int i) {
		Object value = list.get(i);
		if (value == null)
			return null;
		else if (value instanceof Map) {
			return new M((Map<String, ?>)value);
		}
		throw new IllegalStateException("not.a.map");
	}

	public final L vl(int i) {
		Object value = list.get(i);
		if (value == null)
			return null;
		else if (value instanceof List) {
			return new L((List<?>)value);
		}
		throw new IllegalStateException("not.an.list");
	}
}
