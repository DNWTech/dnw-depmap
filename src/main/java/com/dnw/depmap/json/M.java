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
package com.dnw.depmap.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A tool class to create JSON-like objects quickly and easily.
 * </p>
 * <p>
 * To create an object like this:
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
 *          .a(&quot;params&quot;, M.m().a(&quot;name&quot;, &quot;new-object&quot;).a(&quot;refs&quot;, 2, 3, 5));
 *   System.out.println(m.vm(&quot;params&quot;).va(&quot;refs&quot;)[1]); // output: 3
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public final class M {

	final Map<String, Object> map;

	/**
	 * Constructor of M.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 */
	M() {
		map = new HashMap<String, Object>();
	}

	/**
	 * Constructor of M.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	M(Map<String, ?> map) {
		this.map = (Map<String, Object>)map;
	}

	/**
	 * Method m.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return
	 */
	public static final M m() {
		return new M();
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

	/**
	 * Method map.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return
	 */
	public final Map<String, Object> map() {
		return map;
	}

	/**
	 * Method a.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param key
	 * @param value
	 * @return
	 */
	public final M a(String key, Object value) {
		map.put(key, Converter.convert(value));
		return this;
	}

	/**
	 * Method cp.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param src
	 * @param keys
	 * @return
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
	 * Method cp.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param src
	 * @param keys
	 * @return
	 */
	public final M cp(Map<?, ?> src, Object... keys) {
		if (keys.length > 0) {
			for (Object k : keys) {
				String key = String.valueOf(k);
				map.put(key, Converter.convert(src.get(k)));
			}
		} else {
			for (Map.Entry<?, ?> e : src.entrySet()) {
				String key = String.valueOf(e.getKey());
				map.put(key, Converter.convert(e.getValue()));
			}
		}
		return this;
	}

	/**
	 * Method rm.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key
	 * @return
	 */
	public final M rm(String key) {
		map.remove(key);
		return this;
	}

	public final M clear() {
		map.clear();
		return this;
	}

	/**
	 * Method has.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key
	 * @return
	 */
	public final boolean has(String key) {
		return map.containsKey(key);
	}

	/**
	 * Method v.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key
	 * @return
	 */
	public final Object v(String key) {
		return map.get(key);
	}

	/**
	 * Method vm.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key
	 * @return
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
	 * Method va.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param key
	 * @return
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
