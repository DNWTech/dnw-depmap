/**
 * !(#) NeoDao.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 14, 2014.
 */
package com.dnw.depmap.neo;

import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.dnw.matcher.IFilterService;
import com.dnw.plugin.ast.AstUtil;

/**
 * Class/Interface NeoDao.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public class NeoDao {

	private final NeoWriter w;
	private final IFilterService<String> filter;

	/**
	 * Constructor of NeoDao.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param accessor
	 */
	public NeoDao(NeoWriter writer, IFilterService<String> filter) {
		this.w = writer;
		this.filter = filter;
	}

	/**
	 * Method isBlocked.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param type
	 * @return
	 */
	private boolean isBlocked(ITypeBinding type) {
		return filter.blocks(AstUtil.nameOf(type));
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public boolean createBareType(ITypeBinding type) {
		if (type == null)
			return false;
		if (isBlocked(type))
			return false;
		if (!BindingCache.contains(type)) {
			String name = AstUtil.nameOf(type);
			BindingCache.put(type, name);
			w.createType(type);
		}
		return true;
	}

	/**
	 * Creates a type with full type hierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 */
	public boolean createType(ITypeBinding type) {
		if (!createBareType(type))
			return false;
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t))
					w.createExtends(type, t);
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t))
					w.createImplements(type, t);
			}
			ITypeBinding t = type.getSuperclass();
			if (createType(t))
				w.createExtends(type, t);
		}
		return true;
	}

	/**
	 * Method internalCreateMethod.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @return
	 */
	public boolean createBareMethod(IMethodBinding method) {
		if (method == null)
			return false;
		if (!createType(method.getDeclaringClass()))
			return false;
		if (!BindingCache.contains(method)) {
			BindingCache.put(method, AstUtil.nameOf(method));
			w.createMethod(method);
			w.createDeclare(method);
		}
		return true;
	}

	/**
	 * Creates a method with its override hierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 * @return
	 */
	public boolean createMethod(IMethodBinding method) {
		if (!createBareMethod(method))
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m))
						if (createMethod(m))
							w.createOverride(method, m);
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m))
						if (createMethod(m))
							w.createOverride(method, m);
				}
			}
			ITypeBinding t = type.getSuperclass();
			for (IMethodBinding m : t.getDeclaredMethods()) {
				if (method.overrides(m))
					if (createMethod(m))
						w.createOverride(method, m);
			}
		}
		return true;
	}

	/**
	 * Method createInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param from
	 * @param to
	 * @param args
	 */
	public boolean createMethodInvocation(IMethodBinding from, IMethodBinding to, List<?> args) {
		if (from == null && to == null)
			return false;
		if (!createMethod(from))
			return false;
		if (!createMethod(to))
			return false;
		w.createInvocation(from, to, args);
		return true;
	}
}
