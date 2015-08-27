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

	/**
	 * According to document of <tt>ASTParser.setResolveBindings()</tt>:<br/>
	 * <blockquote>It is very important to not retain any of these objectes longer than absolutely
	 * necessary.</blockquote>
	 * 
	 * @author manbaum
	 * @since Aug 26, 2015
	 */
	public static final WeakCache<String, String> cache = new WeakCache<String, String>();

	/**
	 * Method put.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 * @param value
	 */
	public static final void put(IBinding binding, String value) {
		cache.put(binding.getKey(), value);
	}

	/**
	 * Method remove.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 */
	public static final void remove(IBinding binding) {
		cache.remove(binding.getKey());
	}

	/**
	 * Method contains.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 * @return
	 */
	public static final boolean contains(IBinding binding) {
		return cache.contains(binding.getKey());
	}

	/**
	 * Method get.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 * @return
	 */
	public static final String get(IBinding binding) {
		return cache.get(binding.getKey());
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
