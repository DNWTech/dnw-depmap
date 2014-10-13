/**
 * !(#) L.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 12, 2014.
 */
package com.dnw.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A class represents the JSON-like array. Internally it use a list to hold all values.
 * </p>
 * <p>
 * To create an array like:
 * 
 * <pre>
 *   [
 *     {'query': 'merge (:Object {name:{name}}) on create set refs={refs}'},
 *     [
 *       'some text',
 *       [2, 3, 5]
 *     ] 
 *   ] 
 * </pre>
 * </p>
 * <p>
 * Use the following code:
 * 
 * <pre>
 *   L l = L.l()
 *          .a(M.m().a(&quot;query&quot;, &quot;merge (:Object {name:{name}}) on create set refs={refs}&quot;))
 *          .a(L.l().a("some text", L.l().aa(2, 3, 5)));
 *   System.out.println(l.vl(1).vl(1).v(1)); // output: 3
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Oct 12, 2014
 */
public final class L {

	final List<Object> list;

	/**
	 * Creates an empty array. (non-public, public use refers {@link L#l()})
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 */
	L() {
		list = new ArrayList<Object>();
	}

	/**
	 * Creates an array adopt the given list.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param list the list to adopt.
	 */
	@SuppressWarnings("unchecked")
	L(List<?> list) {
		this.list = (List<Object>)list;
	}

	/**
	 * Returns a newly created empty array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @return an array.
	 */
	public static final L l() {
		return new L();
	}

	/**
	 * Exports the array as a JSON format string.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @return a JSON format string represents the array.
	 */
	public final String json() {
		StringBuffer sb = new StringBuffer();
		Json.emit(sb, this);
		return sb.toString();
	}

	/**
	 * Returns the inner list.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @return the inner list.
	 */
	public final List<Object> list() {
		return list;
	}

	/**
	 * Returns an array contains all values hold by the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @return an array.
	 */
	public final Object[] array() {
		return list.toArray();
	}

	/**
	 * Add a set of values to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param values a set of values.
	 * @return the array itself.
	 */
	public final L a(Object... values) {
		for (Object value : values)
			list.add(Json.convert(value));
		return this;
	}

	/**
	 * Add all values contained in the source array (the type of elements is unknown) to array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source array.
	 * @return the array itself.
	 * @throws IllegalArgumentException if the source is not an array.
	 */
	public final L aa(Object src) {
		if (src != null) {
			if (!src.getClass().isArray())
				throw new IllegalArgumentException("not.an.array");
			int length = Array.getLength(src);
			for (int i = 0; i < length; i++) {
				list.add(Json.convert(Array.get(src, i)));
			}
		}
		return this;
	}

	/**
	 * Add all values contained in the source array to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source array.
	 * @return the array itself.
	 */
	public final <T> L aa(T[] src) {
		if (src != null) {
			for (T value : src)
				list.add(Json.convert(value));
		}
		return this;
	}

	/**
	 * Add all values contained in the source <code>java.lang.Iterable</code> to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source iterable collection.
	 * @return the array itself.
	 */
	public final L aa(Iterable<?> src) {
		for (Object v : src) {
			list.add(Json.convert(v));
		}
		return this;
	}

	/**
	 * Add all values contained in the source array to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source array.
	 * @return the array itself.
	 */
	public final L aa(L src) {
		list.addAll(src.list);
		return this;
	}

	/**
	 * Copy a range of values contained in the source array (the type of elements is unknown) to the
	 * array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source array.
	 * @param start the start index to copy.
	 * @param len the number of elements to copy.
	 * @return the array itself.
	 * @throws IllegalArgumentException if the source is not an array.
	 */
	public final L cp(Object src, int start, int len) {
		if (src != null) {
			if (!src.getClass().isArray())
				throw new IllegalArgumentException("not.an.array");
			for (int i = start; i < len; i++) {
				list.add(Json.convert(Array.get(src, i)));
			}
		}
		return this;
	}

	/**
	 * Copy a range of values contained in the source array to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source array.
	 * @param start the start index to copy.
	 * @param len the number of elements to copy.
	 * @return the array itself.
	 */
	public final <T> L cp(T[] src, int start, int len) {
		for (int i = start; i < len; i++) {
			list.add(Json.convert(src[i]));
		}
		return this;
	}

	/**
	 * Copy a range of values contained in the source <code>java.util.List</code> to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source list.
	 * @param start the start index to copy.
	 * @param len the number of elements to copy.
	 * @return the array itself.
	 */
	public final L cp(List<?> src, int start, int len) {
		for (int i = start; i < len; i++) {
			list.add(Json.convert(src.get(i)));
		}
		return this;
	}

	/**
	 * Copy a range of values contained in the source array to the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param src the source array.
	 * @param start the start index to copy.
	 * @param len the number of elements to copy.
	 * @return the array itself.
	 */
	public final L cp(L src, int start, int len) {
		for (int i = start; i < len; i++) {
			list.add(src.list.get(i));
		}
		return this;
	}

	/**
	 * Select a set of values contained in the source array (the type of elements is unknown) and
	 * copy to the array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source array.
	 * @param indexes the selected indexes to copy.
	 * @return the array itself.
	 * @throws IllegalArgumentException if the source is not an array.
	 */
	public final L sel(Object src, int... indexes) {
		if (src != null) {
			if (!src.getClass().isArray())
				throw new IllegalArgumentException("not.an.array");
			for (int i : indexes) {
				list.add(Json.convert(Array.get(src, i)));
			}
		}
		return this;
	}

	/**
	 * Select a set of values contained in the source array and copy to the array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source array.
	 * @param indexes the selected indexes to copy.
	 * @return the array itself.
	 */
	public final <T> L sel(T[] src, int... indexes) {
		for (int i : indexes) {
			list.add(Json.convert(src[i]));
		}
		return this;
	}

	/**
	 * Select a set of values contained in the source <code>java.util.List</code> and copy to the
	 * array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source list.
	 * @param indexes the selected indexes to copy.
	 * @return the array itself.
	 */
	public final L sel(List<?> src, int... indexes) {
		for (int i : indexes) {
			list.add(Json.convert(src.get(i)));
		}
		return this;
	}

	/**
	 * Select a set of values contained in the source array and copy to the array.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param src the source array.
	 * @param indexes the selected indexes to copy.
	 * @return the array itself.
	 */
	public final L sel(L src, int... indexes) {
		for (int i : indexes) {
			list.add(src.list.get(i));
		}
		return this;
	}

	/**
	 * Remove the values at the give indexes.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param indexes the given indexes.
	 * @return the array itself.
	 */
	public final L rm(int... indexes) {
		int length = indexes.length;
		if (length > 0) {
			Arrays.sort(indexes);
			for (int i = length - 1; i >= 0; i--) {
				list.remove(i);
			}
		}
		return this;
	}

	/**
	 * Clears all values in the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @return the array itself.
	 */
	public final L clear() {
		list.clear();
		return this;
	}

	/**
	 * Returns the length of the array.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @return the length of the array.
	 */
	public final int length() {
		return list.size();
	}

	/**
	 * Returns the value at the given index.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param i the given index.
	 * @return the value.
	 */
	@SuppressWarnings("unchecked")
	public final <T> T v(int i) {
		return (T)list.get(i);
	}

	/**
	 * Returns the object (i.e. a map) at the given index.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param i the given index.
	 * @return the result object.
	 * @throws IllegalStateException if the object at the given index is not an object (i.e. a map).
	 */
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

	/**
	 * Returns the array (i.e. a list) at the given index.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param i the given index.
	 * @return the result array.
	 * @throws IllegalStateException if the object at the given index is not an array (i.e. a list).
	 */
	public final L vl(int i) {
		Object value = list.get(i);
		if (value == null)
			return null;
		else if (value instanceof List) {
			return new L((List<?>)value);
		}
		throw new IllegalStateException("not.a.list");
	}
}
