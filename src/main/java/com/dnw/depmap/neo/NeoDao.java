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

import com.dnw.matcher.IListService;
import com.dnw.plugin.ast.AstUtil;

/**
 * Class/Interface NeoDao.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public class NeoDao {

	private final NeoWriter w;
	private final IListService<String> filter;

	/**
	 * Constructor of NeoDao.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param accessor
	 */
	public NeoDao(NeoWriter writer, IListService<String> filter) {
		this.w = writer;
		this.filter = filter;
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public boolean createType(ITypeBinding type) {
		if (type == null)
			return false;
		String name = AstUtil.nameOf(type);
		if (filter.blocks(name))
			return false;
		w.createType(type);
		return true;
	}

	/**
	 * Method createHierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param type
	 * @return
	 */
	public boolean createHierarchy(ITypeBinding type) {
		if (!createType(type))
			return false;
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				w.createType(t);
				w.createExtends(type, t);
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				w.createType(t);
				w.createImplements(type, t);
			}
			ITypeBinding t = type.getSuperclass();
			if (t != null) {
				w.createType(t);
				w.createExtends(type, t);
			}
		}
		return true;
	}

	/**
	 * Method createTypeHierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 */
	public boolean createFullHierarchy(ITypeBinding type) {
		if (!createType(type))
			return false;
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createFullHierarchy(t))
					w.createExtends(type, t);
				else {
					w.createType(t);
					w.createExtends(type, t);
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createFullHierarchy(t))
					w.createImplements(type, t);
				else {
					w.createType(t);
					w.createImplements(type, t);
				}
			}
			ITypeBinding t = type.getSuperclass();
			if (t != null) {
				if (createFullHierarchy(t))
					w.createExtends(type, t);
				else {
					w.createType(t);
					w.createExtends(type, t);
				}
			}
		}
		return true;
	}

	/**
	 * Method createMethod.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 */
	public boolean createMethodDeclaration(IMethodBinding method) {
		if (method == null)
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (!createType(type))
			return false;
		w.createMethod(method);
		w.createDeclare(method);
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
		if (!createMethodDeclaration(from))
			return false;
		if (!createMethodDeclaration(to))
			return false;
		w.createInvocation(from, to, args);
		return true;
	}
}
