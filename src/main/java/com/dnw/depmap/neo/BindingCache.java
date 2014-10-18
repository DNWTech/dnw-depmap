/**
 * !(#) BindingCache.java
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
package com.dnw.depmap.neo;

import org.eclipse.jdt.core.dom.IBinding;

import com.dnw.plugin.util.WeakCache;

/**
 * Class/Interface BindingCache.
 * 
 * @author manbaum
 * @since Oct 18, 2014
 */
public final class BindingCache {

	public static final WeakCache<IBinding, String> cache = new WeakCache<IBinding, String>();

	/**
	 * Method put.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @param value
	 */
	public static final void put(IBinding key, String value) {
		cache.put(key, value);
	}

	/**
	 * Method remove.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 */
	public static final void remove(IBinding key) {
		cache.remove(key);
	}

	/**
	 * Method contains.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @return
	 */
	public static final boolean contains(IBinding key) {
		return cache.contains(key);
	}

	/**
	 * Method get.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param key
	 * @return
	 */
	public static final String get(IBinding key) {
		return cache.get(key);
	}

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 */
	public static final void clear() {
		cache.clear();
	}
}
