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
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Class/Interface AstUtil.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public final class AstUtil {

	/**
	 * Method replaceAllGenericTypeParameters.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param name
	 * @return
	 */
	public final static String replaceAllGenericTypeParameters(String name) {
		return name.replaceAll("<[^>]+>", "<?>");
	}

	/**
	 * Method nameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @return
	 */
	public final static String nameOf(ITypeBinding type) {
		return type.getErasure().getQualifiedName();
	}

	/**
	 * Method displayNameOf.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @return
	 */
	public final static String captionOf(ITypeBinding type) {
		return type.getErasure().getName();
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
		sb.append(method.getDeclaringClass().getErasure().getQualifiedName());
		sb.append(Modifier.isStatic(method.getModifiers()) ? '/' : '#');
		sb.append(method.getName());
		sb.append('(');
		boolean first = true;
		for (ITypeBinding t : method.getParameterTypes()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append(t.getErasure().getQualifiedName());
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
	public final static String captionOf(IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append(method.getName());
		sb.append("()");
		return sb.toString();
	}

	/**
	 * Method fileInfo.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node
	 * @return
	 */

	/**
	 * Method tellTypeDeclaration.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node
	 * @param type
	 */
	public static String infoOf(VisitContext context, TypeDeclaration node, ITypeBinding type) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(type != null ? type.getQualifiedName() : "nothing");
		sb.append(" (");
		sb.append(context.fileInfo(node));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Method tellMethodDeclaration.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node
	 * @param method
	 */
	public static String infoOf(VisitContext context, MethodDeclaration node, IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(context.fileInfo(node));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Method tellMethodInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param node
	 * @param method
	 */
	public static String infoOf(VisitContext context, MethodInvocation node, IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(context.fileInfo(node));
		sb.append(")");
		return sb.toString();
	}
}
