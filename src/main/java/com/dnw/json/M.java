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
package com.dnw.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A class represents the JSON-like object. Internally it use a map to hold all key-value entries.
 * </p>
 * <p>
 * To create an object like:
 * 
 * <pre>
 *   {
 *     'query': 'merge (:Object {name:{name}}) on create set refs={refs}',
 *     'params': {
 *       'name': 'new-object',
 *       'refs': [2, 3, 5]
 *     } 
 *   } 
 * </pre>
 * </p>
 * <p>
 * Use the following code:
 * 
 * <pre>
 *   M m = M.m()
 *          .a(&quot;query&quot;, &quot;merge (:Object {name:{name}}) on create set refs={refs}&quot;)
 *          .a(&quot;params&quot;, M.m().a(&quot;name&quot;, &quot;new-object&quot;).a(&quot;refs&quot;, L.l().a(2, 3, 5)));
 *   System.out.println(m.vm(&quot;params&quot;).vl(&quot;refs&quot;).v(1)); // output: 3
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public final class M {

	final Map<String, Object> map;

	/**
	 * Creates an empty object. (non-public, public use refers {@link M#m()}.)
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 */
	M() {
		map = new HashMap<String, Object>();
	}

	/**
	 * Creates an object adopt the given map.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param map the map to adopt.
	 */
	@SuppressWarnings("unchecked")
	M(Map<String, ?> map) {
		this.map = (Map<String, Object>)map;
	}

	/**
	 * Returns a newly created empty object.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return an object.
	 */
	public static final M m() {
		return new M();
	}

	/**
	 * Exports the object as a JSON format string.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return a JSON format string represents the object.
	 */
	public final String json() {
		StringBuffer sb = new StringBuffer();
		J.emit(sb, this);
		return sb.toString();
	}

	/**
	 * Returns the inner map.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return the inner map.
	 */
	public final Map<String, Object> map() {
		return map;
	}

	/**
	 * Adds a key-value entry to object.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param key the key.
	 * @param value the value.
	 * @return the object itself.
	 */
	public final M a(String key, Object value) {
		map.put(key, J.convert(value));
		return this;
	}

	/**
	 * Copy key-value entries from the given map.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param src the source map.
	 * @param keys keys the keys to copy from. if missing, all entries in source will be copied.
	 * @return the object itself.
	 */
	public final M cp(Map<?, ?> src, Object... keys) {
		if (keys.length > 0) {
			for (Object k : keys) {
				String key = String.valueOf(k);
				map.put(key, J.convert(src.get(k)));
			}
		} else {
			for (Map.Entry<?, ?> e : src.entrySet()) {
				String key = String.valueOf(e.getKey());
				map.put(key, J.convert(e.getValue()));
			}
		}
		return this;
	}

	/**
	 * Copy key-value entries from another object.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param src the source object.
	 * @param keys the keys to copy from. if missing, all entries in source will be copied.
	 * @return the object itself.
	 */
	public final M cp(M src, String... keys) {
		if (keys.length > 0) {
			for (String key : keys) {
				map.put(key, src.map.get(key));
			}
		} else {
			map.putAll(src.map);
		}
		return this;
	}

	/**
	 * Remove the given keys from the object.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param keys the keys to remove.
	 * @return the object itself.
	 */
	public final M rm(String... keys) {
		for (String key : keys)
			map.remove(key);
		return this;
	}

	/**
	 * Clear all key-value entries in the object.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @return the object itself.
	 */
	public final M clear() {
		map.clear();
		return this;
	}

	/**
	 * Checks if the object contains the given key.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key the key to check
	 * @return <code>true</code> if contains, else <code>false</code>.
	 */
	public final boolean has(String key) {
		return map.containsKey(key);
	}

	/**
	 * Returns the value associated with the given key.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key the key.
	 * @return the value.
	 */
	public final Object v(String key) {
		return map.get(key);
	}

	/**
	 * Returns the object (i.e. a map) associated with the given key.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key the key.
	 * @return the result object.
	 * @throws IllegalStateException if the object associated with the key is not an object (i.e. a
	 *             map).
	 */
	@SuppressWarnings("unchecked")
	public final M vm(String key) {
		Object value = map.get(key);
		if (value == null)
			return null;
		else if (value instanceof Map) {
			return new M((Map<String, ?>)value);
		}
		throw new IllegalStateException("not.a.map");
	}

	/**
	 * Returns the array (i.e. a list) associated with the given key.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key the key.
	 * @return the result array.
	 * @throws IllegalStateException if the object associated with the key is not an array (i.e. a
	 *             list).
	 */
	public final L vl(String key) {
		Object value = map.get(key);
		if (value == null)
			return null;
		else if (value instanceof List) {
			return new L((List<?>)value);
		}
		throw new IllegalStateException("not.an.list");
	}
}
