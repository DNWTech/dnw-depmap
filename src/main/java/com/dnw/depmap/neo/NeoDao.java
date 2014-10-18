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

import org.eclipse.jdt.core.dom.IBinding;
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
	 * Method isBlocked.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @return
	 */
	private boolean isBlocked(IMethodBinding method) {
		return filter.blocks(AstUtil.nameOf(method));
	}

	/**
	 * Method isCached.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 * @return
	 */
	private boolean isCached(IBinding binding) {
		return BindingCache.contains(binding);
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
		ITypeBinding declaration = type.getTypeDeclaration();
		if (isCached(declaration))
			return true;
		String name = AstUtil.nameOf(declaration);
		BindingCache.put(declaration, name);
		w.createType(declaration);
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
		if (isBlocked(method))
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (!createBareType(type))
			return false;
		IMethodBinding declaration = method.getMethodDeclaration();
		if (isCached(declaration))
			return true;
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createMethod(declaration);
		w.createDeclare(declaration);
		return true;
	}

	/**
	 * Method createBareInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param from
	 * @param to
	 * @param args
	 * @return
	 */
	public boolean createBareInvocation(IMethodBinding from, IMethodBinding to, List<?> args) {
		if (!createBareMethod(from))
			return false;
		if (!createBareMethod(to))
			return false;
		IMethodBinding df = from.getMethodDeclaration();
		IMethodBinding dt = to.getMethodDeclaration();
		w.createInvocation(df, dt, args);
		return true;
	}

	/**
	 * Creates a type with full type hierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 */
	public boolean createType(ITypeBinding type) {
		if (type == null)
			return false;
		if (isBlocked(type))
			return false;
		ITypeBinding declaration = type.getTypeDeclaration();
		if (isCached(declaration))
			return true;
		String name = AstUtil.nameOf(declaration);
		BindingCache.put(declaration, name);
		w.createType(declaration);
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t)) {
					ITypeBinding d = t.getTypeDeclaration();
					w.createExtends(declaration, d);
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t)) {
					ITypeBinding d = t.getTypeDeclaration();
					w.createExtends(declaration, d);
				}
			}
			ITypeBinding t = type.getSuperclass();
			if (createType(t)) {
				ITypeBinding d = t.getTypeDeclaration();
				w.createExtends(declaration, d);
			}
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
		if (method == null)
			return false;
		if (isBlocked(method))
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (!createType(type))
			return false;
		IMethodBinding declaration = method.getMethodDeclaration();
		if (isCached(declaration))
			return true;
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createMethod(declaration);
		w.createDeclare(declaration);
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m)) {
						IMethodBinding d = m.getMethodDeclaration();
						if (createMethod(m))
							w.createOverride(declaration, d);
					}
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m)) {
						IMethodBinding d = m.getMethodDeclaration();
						if (createMethod(m))
							w.createOverride(declaration, d);
					}
				}
			}
			ITypeBinding t = type.getSuperclass();
			for (IMethodBinding m : t.getDeclaredMethods()) {
				if (method.overrides(m)) {
					IMethodBinding d = m.getMethodDeclaration();
					if (createMethod(m))
						w.createOverride(declaration, d);
				}
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
	public boolean createInvocation(IMethodBinding from, IMethodBinding to, List<?> args) {
		if (!createMethod(from))
			return false;
		if (!createMethod(to))
			return false;
		IMethodBinding df = from.getMethodDeclaration();
		IMethodBinding dt = to.getMethodDeclaration();
		w.createInvocation(df, dt, args);
		return true;
	}
}
