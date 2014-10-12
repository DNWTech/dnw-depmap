/**
 * !(#) Teller.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 11, 2014.
 */
package com.dnw.depmap.ast;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.dnw.depmap.Activator;

/**
 * Class/Interface Teller.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public class Teller {

	public static String fileInfo(ASTNode node) {
		CompilationUnit unit = (CompilationUnit)node.getRoot();
		IResource resource;
		try {
			resource = unit.getJavaElement().getCorrespondingResource();
		} catch (JavaModelException e) {
			Activator.console.println(e);
			return "";
		}
		IFile file = (IFile)resource.getAdapter(IFile.class);
		if (file == null)
			return "";
		String filename = file.getFullPath().toString();
		String linenum = String.valueOf(unit.getLineNumber(node.getStartPosition()));
		return filename + ":" + linenum;
	}

	public static void tellTypeDeclaration(TypeDeclaration node, ITypeBinding type) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(type != null ? type.getQualifiedName() : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}

	public static void tellMethodDeclaration(MethodDeclaration node, IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}

	public static void tellMethodInvocation(MethodInvocation node, IMethodBinding method) {
		StringBuffer sb = new StringBuffer();
		sb.append("  . ");
		sb.append(node.getName());
		sb.append(" bound to: ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}
}
