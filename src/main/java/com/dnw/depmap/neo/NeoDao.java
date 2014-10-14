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

import com.dnw.plugin.ast.AstUtil;

/**
 * Class/Interface NeoDao.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public class NeoDao {

	private final NeoWriter w;

	/**
	 * Constructor of NeoDao.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param accessor
	 */
	public NeoDao(NeoWriter writer) {
		this.w = writer;
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public void createType(ITypeBinding type) {
		if (type != null && BlackOrWhite.allowed(AstUtil.nameOf(type))) {
			w.createType(type);
			if (type.isInterface()) {
				for (ITypeBinding t : type.getInterfaces()) {
					if (BlackOrWhite.allowed(AstUtil.nameOf(t))) {
						w.createType(t);
						w.createExtends(type, t);
					}
				}
			} else {
				for (ITypeBinding t : type.getInterfaces()) {
					if (BlackOrWhite.allowed(AstUtil.nameOf(t))) {
						w.createType(t);
						w.createImplements(type, t);
					}
				}
				if (BlackOrWhite.allowed(AstUtil.nameOf(type.getSuperclass()))) {
					w.createExtends(type, type.getSuperclass());
				}
			}
		}
	}

	/**
	 * Method createMethod.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 */
	public void createMethod(IMethodBinding method) {
		ITypeBinding type = method.getDeclaringClass();
		String typeName = AstUtil.nameOf(type);
		if (method != null && BlackOrWhite.allowed(typeName)) {
			w.createMethod(method);
		}
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
	public void createInvocation(IMethodBinding from, IMethodBinding to, List<?> args) {
		if (from != null && to != null) {
			String typeNameF = AstUtil.nameOf(from.getDeclaringClass());
			boolean allowF = BlackOrWhite.allowed(typeNameF);
			String typeNameT = AstUtil.nameOf(to.getDeclaringClass());
			boolean allowT = BlackOrWhite.allowed(typeNameT);
			if (allowF && allowT)
				w.createInvocation(from, to, args);
		}
	}
}
