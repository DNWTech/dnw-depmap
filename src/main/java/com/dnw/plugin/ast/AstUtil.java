/**
 * !(#) AstUtil.java
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
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;

/**
 * Class/Interface AstUtil.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public final class AstUtil {

	/**
	 * Method nameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @return
	 */
	public final static String nameOf(ITypeBinding type) {
		return type.getQualifiedName();
	}

	/**
	 * Method displayNameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @return
	 */
	public final static String displayNameOf(ITypeBinding type) {
		return type.getName();
	}

	/**
	 * Method nameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 * @return
	 */
	public final static String nameOf(IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append(method.getDeclaringClass().getQualifiedName());
		sb.append(Modifier.isStatic(method.getModifiers()) ? '/' : '#');
		sb.append(method.isConstructor() ? "<ctor>" : method.getName());
		sb.append('(');
		boolean first = true;
		for (ITypeBinding t : method.getParameterTypes()) {
			if (first)
				first = false;
			else
				sb.append(',');
			sb.append(t.getQualifiedName());
		}
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Method displayNameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 * @return
	 */
	public final static String displayNameOf(IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		// sb.append(method.getDeclaringClass().getName());
		// sb.append(Modifier.isStatic(method.getModifiers()) ? '/' : '#');
		sb.append(method.isConstructor() ? "<ctor>" : method.getName());
		sb.append("()");
		return sb.toString();
	}
}
