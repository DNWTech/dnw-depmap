/**
 * !(#) WeakCache.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 18, 2014.
 */
package com.dnw.plugin.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Class/Interface WeakCache.
 * 
 * @author manbaum
 * @since Oct 18, 2014
 */
public final class WeakCache<K, V> {

	private final Map<K, WeakReference<V>> map = new HashMap<K, WeakReference<V>>();

	/**
	 * Method put.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("null.key.not.allowed");
		if (value == null)
			throw new NullPointerException("null.value.not.allowed");
		map.put(key, new WeakReference<V>(value));
	}

	/**
	 * Method remove.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 */
	public void remove(K key) {
		map.remove(key);
	}

	/**
	 * Method contains.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @return
	 */
	public boolean contains(K key) {
		return get(key) != null;
	}

	/**
	 * Method get.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @return
	 */
	public V get(K key) {
		WeakReference<V> ref = map.get(key);
		return ref != null ? ref.get() : null;
	}
}
