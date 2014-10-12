/**
 * !(#) M.java
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public final class M {

	public static abstract class K {
		protected Object defaultMake(Object value) {
			return M.make(value);
		}

		public abstract Object make(Object value);
	}

	private final Map<String, Object> map = new HashMap<String, Object>();

	private M() {
	}

	public final Map<String, Object> map() {
		return map;
	}

	public final String json() {
		StringBuffer sb = new StringBuffer();
		JSON.emitMap(sb, map);
		return sb.toString();
	}

	public final M e(String key, Object... values) {
		int length = values.length;
		if (length == 0) {
			map.put(key, null);
		} else if (length == 1) {
			map.put(key, make(values[0]));
		} else {
			Object[] array = new Object[length];
			for (int i = 0; i < length; i++) {
				array[i] = make(values[i]);
			}
			map.put(key, array);
		}
		return this;
	}

	@SafeVarargs
	public final <T> M e(K k, String key, final T... values) {
		int length = values.length;
		if (length == 0) {
			map.put(key, null);
		} else if (length == 1) {
			map.put(key, k.make(values[0]));
		} else {
			Object[] array = new Object[length];
			for (int i = 0; i < length; i++) {
				array[i] = k.make(values[i]);
			}
			map.put(key, array);
		}
		return this;
	}

	public final M rm(String key) {
		map.remove(key);
		return this;
	}

	public final M cp(M src, String... keys) {
		if (keys.length > 0) {
			for (String key : keys) {
				map.put(key, src.map.get(key));
			}
		} else {
			for (Map.Entry<String, Object> e : src.map.entrySet()) {
				String key = e.getKey();
				map.put(key, e.getValue());
			}
		}
		return this;
	}

	public final M cp(Map<?, ?> src, Object... keys) {
		if (keys.length > 0) {
			for (Object o : keys) {
				String key = String.valueOf(o);
				map.put(key, make(src.get(o)));
			}
		} else {
			for (Map.Entry<?, ?> e : src.entrySet()) {
				String key = String.valueOf(e.getKey());
				map.put(key, make(e.getValue()));
			}
		}
		return this;
	}

	public final M cp(K k, Map<?, ?> src, Object... keys) {
		if (keys.length > 0) {
			for (Object o : keys) {
				String key = String.valueOf(o);
				map.put(key, k.make(src.get(o)));
			}
		} else {
			for (Map.Entry<?, ?> e : src.entrySet()) {
				String key = String.valueOf(e.getKey());
				map.put(key, k.make(e.getValue()));
			}
		}
		return this;
	}

	public final Object v(String key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	public final Map<String, ?> vm(String key) {
		return (Map<String, ?>) map.get(key);
	}

	public final Object[] va(String key) {
		return (Object[]) map.get(key);
	}

	public final boolean has(String key) {
		return map.containsKey(key);
	}

	public static final M m() {
		return new M();
	}

	private final static Object make(Object value) {
		if (value == null || value instanceof String || value instanceof Number) {
			return value;
		} else if (value instanceof M) {
			return ((M) value).map;
		} else if (value.getClass().isArray()) {
			int length = Array.getLength(value);
			Object[] array = (Object[]) Array.newInstance(Object.class, length);
			for (int i = 0; i < length; i++) {
				array[i] = make(Array.get(value, i));
			}
			return array;
		} else if (value instanceof Collection<?>) {
			Collection<?> src = (Collection<?>) value;
			int i = 0;
			Iterator<?> it = src.iterator();
			Object[] array = new Object[src.size()];
			while (it.hasNext()) {
				array[i++] = make(it.next());
			}
			return array;
		} else if (value instanceof Map<?, ?>) {
			Map<?, ?> src = (Map<?, ?>) value;
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map.Entry<?, ?> e : src.entrySet()) {
				map.put(String.valueOf(e.getKey()), make(e.getValue()));
			}
			return map;
		}
		throw new IllegalArgumentException("unknown.supported.type: "
				+ value.getClass());
	}

	public final static void main(String[] args) {
		HashSet<Object> set = new HashSet<Object>();
		set.add("set");
		set.add(M.m());
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("list");
		list.add(set);
		M m = M.m().e("hello", "world\b\f\t\r\n\\\'\"haha中文")
				.e("array", 5L, 6.0f, "7", M.m().e("eight", 8), list);
		System.out.println(m.json());
	}
}
